/*
 * Copyright(c) Inocybe Technologies and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.multifunctional.impl;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.OptimisticLockFailedException;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.DisplayString;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional.MultifunctionalStatus;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalBuilder;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalOutOfStockBuilder;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalRestocked;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalRestockedBuilder;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalService;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.RestockMultifunctionalInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.multifunctional.impl.rev141210.MultifunctionalRuntimeMXBean;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

public class MultifunctionalProvider implements MultifunctionalService,
        DataChangeListener, BindingAwareProvider, MultifunctionalRuntimeMXBean,
        AutoCloseable {

    // making this public because this unique ID is required later on in other
    // classes.
    public static final InstanceIdentifier<Multifunctional> MULTIFUNCTIONAL_IID = InstanceIdentifier
            .builder(Multifunctional.class).build();

    private static final DisplayString MULTIFUNCTIONAL_MANUFACTURER = new DisplayString(
            "HP");
    private static final DisplayString MULTIFUNCTIONAL_MODEL_NUMBER = new DisplayString(
            "DESKJET 4022");

    private static final Logger LOG = LoggerFactory
            .getLogger(MultifunctionalProvider.class);
    private final ExecutorService executor;

    // The following holds the Future for the current print task.
    // This is used to cancel the current print.
    private final AtomicReference<Future<?>> currentMultifunctionalTask = new AtomicReference<>();

    private DataBroker dataProvider;

    private final AtomicLong amountOfPagesAvailable = new AtomicLong(100);
    private AtomicLong darknessFactor = new AtomicLong(1000);

    private final AtomicLong pagesPrinted = new AtomicLong(0);

    private NotificationProviderService notificationProvider;

    public MultifunctionalProvider() {
        executor = Executors.newFixedThreadPool(1);
    }

    public void setNotificationProvider(NotificationProviderService salService) {
        this.notificationProvider = salService;
    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("MultifunctionalProvider Session Initiated");
    }

    @Override
    public Future<RpcResult<Void>> cancel() {
        Future<?> current = currentMultifunctionalTask.getAndSet(null);
        if (current != null) {
            current.cancel(true);
        }

        // Always return success from the cancel print call.
        return Futures.immediateFuture(RpcResultBuilder.<Void> success()
                .build());
    }

    @Override
    public Future<RpcResult<Void>> print(PrintInput input) {
        final SettableFuture<RpcResult<Void>> futureResult = SettableFuture
                .create();

        checkStatusAndPrint(input, futureResult, 3);

        return futureResult;
    }

    /**
     * Read the MultifunctionalStatus and, if currently Down, try to write the
     * status to Up. If that succeeds, then we essentially have an exclusive
     * lock and can proceed to print.
     *
     * @param input
     * @param futureResult
     * @param tries
     */
    private void checkStatusAndPrint(final PrintInput input,
            final SettableFuture<RpcResult<Void>> futureResult, final int tries) {

        // Read the MultifunctionalStatus and, if currently Down, try to write
        // the status to Up.
        // If that succeeds, then we essentially have an exclusive lock and can
        // proceed
        // to print.
        /**
         * We create a ReadWriteTransaction by using the databroker. Then, we
         * read the status of the cup with getCupStatus() using the databroker
         * again. Once we have the status, we analyze it and then databroker
         * submit function is called to effectively change the cup status.
         *
         * This all affects the MD-SAL tree, more specifically the part of the
         * tree that contain the cup (the nodes).
         */
        final ReadWriteTransaction tx = dataProvider.newReadWriteTransaction();
        ListenableFuture<Optional<Multifunctional>> readFuture = tx.read(
                LogicalDatastoreType.OPERATIONAL, MULTIFUNCTIONAL_IID);

        final ListenableFuture<Void> commitFuture = Futures.transform(
                readFuture,
                new AsyncFunction<Optional<Multifunctional>, Void>() {

                    @Override
                    public ListenableFuture<Void> apply(
                            final Optional<Multifunctional> multifunctionalData)
                            throws Exception {

                        MultifunctionalStatus multifunctionalStatus = MultifunctionalStatus.Down;
                        if (multifunctionalData.isPresent()) {
                            multifunctionalStatus = multifunctionalData.get()
                                    .getMultifunctionalStatus();
                        }

                        LOG.debug("Read multifunctional status: {}",
                                multifunctionalStatus);

                        if (multifunctionalStatus == MultifunctionalStatus.Down) {

                            if (outOfPages()) {
                                LOG.debug("No more paper");

                                return Futures
                                        .immediateFailedCheckedFuture(new TransactionCommitFailedException(
                                                "", makeNoMorePageError()));
                            }

                            LOG.debug("Setting multifunctional status to Up");

                            // We're not currently printing - try to update the
                            // status to Up
                            // to indicate we're going to print. This acts as a
                            // lock to prevent
                            // concurrent toasting.
                            tx.put(LogicalDatastoreType.OPERATIONAL,
                                    MULTIFUNCTIONAL_IID,
                                    buildMultifunctional(MultifunctionalStatus.Up));
                            return tx.submit();
                        }

                        LOG.debug("Oops - already printing!");

                        // Return an error since we are already printing. This
                        // will get
                        // propagated to the commitFuture below which will
                        // interpret the null
                        // TransactionStatus in the RpcResult as an error
                        // condition.
                        return Futures
                                .immediateFailedCheckedFuture(new TransactionCommitFailedException(
                                        "", makeMultifunctionalInUseError()));
                    }

                    /**
                     *
                     * @return The RPC error message in case the RPC service is
                     *         not available.
                     */
                    private RpcError makeNoMorePageError() {
                        return RpcResultBuilder.newError(ErrorType.APPLICATION,
                                "resource-denied", "No more paper",
                                "out-of-stock", null, null);
                    }

                    /**
                     *
                     * @return true if there are no more multifunctionals, false
                     *         otherwise.
                     */
                    private boolean outOfPages() {
                        return amountOfPagesAvailable.get() == 0;
                    }

                    private RpcError makeMultifunctionalInUseError() {
                        return RpcResultBuilder.newWarning(
                                ErrorType.APPLICATION, "in-use",
                                "Multifunctional is busy (in-use)", null, null,
                                null);
                    }

                    private Multifunctional buildMultifunctional(
                            MultifunctionalStatus status) {
                        return new MultifunctionalBuilder()
                                .setMultifunctionalManufacturer(
                                        MULTIFUNCTIONAL_MANUFACTURER)
                                .setMultifunctionalModelNumber(
                                        MULTIFUNCTIONAL_MODEL_NUMBER)
                                .setMultifunctionalStatus(status).build();
                    }
                });

        Futures.addCallback(commitFuture, new FutureCallback<Void>() {
            @Override
            public void onSuccess(final Void result) {
                // OK to print
                currentMultifunctionalTask.set(executor.submit(new PrintTask(
                        input, futureResult)));
            }

            @Override
            public void onFailure(final Throwable ex) {
                if (ex instanceof OptimisticLockFailedException) {

                    // Another thread is likely trying to print simultaneously
                    // and updated the
                    // status before us. Try reading the status again - if
                    // another printing is
                    // now in progress, we should get MultifunctionalStatus.Down
                    // and fail.

                    if ((tries - 1) > 0) {
                        LOG.debug("Got OptimisticLockFailedException - trying again");

                        checkStatusAndPrint(input, futureResult, tries - 1);
                    } else {
                        futureResult.set(RpcResultBuilder
                                .<Void> failed()
                                .withError(ErrorType.APPLICATION,
                                        ex.getMessage()).build());
                    }

                } else {

                    LOG.debug("Failed to commit multifunctional status", ex);

                    // Probably already printing.
                    futureResult.set(RpcResultBuilder
                            .<Void> failed()
                            .withRpcErrors(
                                    ((TransactionCommitFailedException) ex)
                                            .getErrorList()).build());
                }
            }
        });
    }

    /**
     * Set the dataBroker
     *
     * @param salDataProvider
     */
    public void setDataProvider(final DataBroker salDataProvider) {
        this.dataProvider = salDataProvider;
        // setCupStatusCold( null );
    }

    /**
     * Implemented from the AutoCloseable interface.
     */
    @Override
    public void close() throws ExecutionException, InterruptedException {
        // When we close this service we need to shutdown our executor!
        executor.shutdown();
        LOG.info("MultifunctionalProvider Closed");
    }

    @Override
    public void onDataChanged(
            AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
        DataObject dataObject = change.getUpdatedSubtree();
        if (dataObject instanceof Multifunctional) {
            Multifunctional multifunctional = (Multifunctional) dataObject;
            Long darkness = multifunctional.getDarknessFactor();
            if (darkness != null) {
                darknessFactor.set(darkness);
            }
        }

    }

    private class PrintTask implements Callable<Void> {

        final PrintInput printRequest;
        final SettableFuture<RpcResult<Void>> futureResult;

        public PrintTask(final PrintInput printRequest,
                final SettableFuture<RpcResult<Void>> futureResult) {
            this.printRequest = printRequest;
            this.futureResult = futureResult;
        }

        @Override
        public Void call() {
            try {
                // make toast just sleeps for n seconds.
                long darknessFactor = MultifunctionalProvider.this.darknessFactor
                        .get();
                Thread.sleep(darknessFactor * printRequest.getPrintDoneness());
            } catch (InterruptedException e) {
                LOG.info("Interrupted while making the toast");
            }

            pagesPrinted.incrementAndGet();

            amountOfPagesAvailable.getAndDecrement();

            if (outOfPaper()) {
                LOG.info("Multifunctional is out of bread!");

                notificationProvider
                        .publish(new MultifunctionalOutOfStockBuilder().build());
            }
            return null;
        }

        private boolean outOfPaper() {
            return amountOfPagesAvailable.get() == 0;
        }
    }

    @Override
    public Long getPagesPrinted() {
        return pagesPrinted.get();
    }

    @Override
    public void clearPagesPrinted() {
        LOG.info("clearPagesPrinted");
        pagesPrinted.set(0);
    }

    @Override
    public Future<RpcResult<Void>> restockMultifunctional(
            RestockMultifunctionalInput input) {

        LOG.info("restockMultifunctional: " + input);

        amountOfPagesAvailable.set(input.getAmountOfPagesToStock());

        if (amountOfPagesAvailable.get() > 0) {
            MultifunctionalRestocked reStockedNotification = new MultifunctionalRestockedBuilder()
                    .setAmountOfPage(input.getAmountOfPagesToStock()).build();
            notificationProvider.publish(reStockedNotification);
        }

        return Futures.immediateFuture(RpcResultBuilder.<Void> success()
                .build());
    }
}
