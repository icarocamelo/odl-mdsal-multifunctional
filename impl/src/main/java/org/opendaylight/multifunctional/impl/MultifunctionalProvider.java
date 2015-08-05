/*
 * Copyright (c) 2015 Inocybe Technologies and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.multifunctional.impl;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.TransactionStatus;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.DisplayString;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.Multifunctional.MultifunctionalStatus;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class MultifunctionalProvider implements BindingAwareProvider,
        AutoCloseable {

    public MultifunctionalProvider(){
        executor = Executors.newFixedThreadPool(1);
    }

    private final ExecutorService executor;

    // The following holds the Future for the current make toast task.
    // This is used to cancel the current toast.
    private final AtomicReference<Future<?>> currentMultifunctional = new AtomicReference<>();

    private static final Logger LOG = LoggerFactory
            .getLogger(MultifunctionalProvider.class);

    // making this public because this unique ID is required later on in other
    // classes.
    public static final InstanceIdentifier<Multifunctional> MULTIFUNCTIONAL_IID = InstanceIdentifier
            .builder(Multifunctional.class).build();

    private static final DisplayString MULTIFUNCTIONAL_MANUFACTURER = new DisplayString(
            "HP");
    private static final DisplayString MULTIFUNCTIONAL_MODEL_NUMBER = new DisplayString(
            "Model Deskjet 2000");

    private DataBroker dataProvider;

    private Multifunctional buildMultifunctional( MultifunctionalStatus status ) {

        // note - we are simulating a device whose manufacture and model are
        // fixed (embedded) into the hardware.
        // This is why the manufacture and model number are hardcoded.
        return new MultifunctionalBuilder().setMultifunctionalManufacturer( MULTIFUNCTIONAL_MANUFACTURER )
                                   .setMultifunctionalModelNumber( MULTIFUNCTIONAL_MODEL_NUMBER )
                                   .setMultifunctionalStatus( status )
                                   .build();
    }

    public void setDataProvider( final DataBroker salDataProvider ) {
         this.dataProvider = salDataProvider;
         setMultifunctionalStatusUp( null );
    }


    public Future<RpcResult<Void>> cancel() {

        Future<?> current = currentMultifunctional.getAndSet( null );
        if( current != null ) {
            current.cancel( true );
        }

        // Always return success from the cancel toast call.
        return  null; //Futures.immediateFuture( Rpcs.<Void> getRpcResult( true,
                                        //Collections.<RpcError>emptyList() ) );
    }
    /**
     * Implemented from the AutoCloseable interface.
     */
    @Override
    public void close() throws ExecutionException, InterruptedException {
     // When we close this service we need to shutdown our executor!
        executor.shutdown();

        if (dataProvider != null) {
            WriteTransaction t = dataProvider.newWriteOnlyTransaction();
            t.delete(LogicalDatastoreType.OPERATIONAL,MULTIFUNCTIONAL_IID);
            ListenableFuture<RpcResult<TransactionStatus>> future = t.commit();
            Futures.addCallback( future, new FutureCallback<RpcResult<TransactionStatus>>() {
                @Override
                public void onSuccess( RpcResult<TransactionStatus> result ) {
                    LOG.debug( "Delete Multifunctional commit result: " + result );
                }

                @Override
                public void onFailure( Throwable t ) {
                    LOG.error( "Delete of Multifunctional failed", t );
                }
            } );
        }
    }

    private void setMultifunctionalStatusUp( final Function<Boolean,Void> resultCallback ) {

        WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
        tx.put( LogicalDatastoreType.OPERATIONAL,MULTIFUNCTIONAL_IID, buildMultifunctional( MultifunctionalStatus.Up ) );

        ListenableFuture<RpcResult<TransactionStatus>> commitFuture = tx.commit();

        Futures.addCallback( commitFuture, new FutureCallback<RpcResult<TransactionStatus>>() {
            @Override
            public void onSuccess( RpcResult<TransactionStatus> result ) {
                if( result.getResult() != TransactionStatus.COMMITED ) {
                    LOG.error( "Failed to update toaster status: " + result.getErrors() );
                }

                notifyCallback( result.getResult() == TransactionStatus.COMMITED );
            }

            @Override
            public void onFailure( Throwable t ) {
                // We shouldn't get an OptimisticLockFailedException (or any ex) as no
                // other component should be updating the operational state.
                LOG.error( "Failed to update toaster status", t );

                notifyCallback( false );
            }

            void notifyCallback( boolean result ) {
                if( resultCallback != null ) {
                    resultCallback.apply( result );
                }
            }
        } );
    }






    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("MultifunctionalProvider Session Initiated");
    }


}
