/*
 * Copyright (c) 2015 Inocybe and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.office.api.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.opendaylight.controller.office.api.OfficeService;
import org.opendaylight.controller.office.api.PrintType;
import org.opendaylight.controller.sal.common.util.RpcErrors;
import org.opendaylight.controller.sal.common.util.Rpcs;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalListener;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalOutOfStock;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalRestocked;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalService;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInput;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.PrintInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.config.office.consumer.impl.rev140131.OfficeServiceRuntimeMXBean;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorSeverity;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class OfficeServiceImpl implements OfficeService, OfficeServiceRuntimeMXBean, MultifunctionalListener , AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger( OfficeServiceImpl.class );

    private final MultifunctionalService printService;

    private final ListeningExecutorService executor =
            MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    private volatile boolean multifunctionalOutOfPaper;

    public OfficeServiceImpl(MultifunctionalService service) {
        this.printService = service;
    }

    @Override
    public Future<RpcResult<Void>> createPrint(PrintType printType,
          long numberOfPages, int printDoneness)  {

        // Call createDocument and use JdkFutureAdapters to convert the Future to a ListenableFuture,
        // The Multifunctional impl already returns a ListenableFuture so the conversion is
        // actually a no-op.

        ListenableFuture<RpcResult<Void>> makeFuturePrints = JdkFutureAdapters.listenInPoolThread(
                print( numberOfPages, printDoneness ), executor );

        ListenableFuture<RpcResult<Void>> makeFutureDocuments = createDocument( printType );

        // Combine the 2 ListenableFutures into 1 containing a list of RpcResults.

        ListenableFuture<List<RpcResult<Void>>> combinedFutures =
                Futures.allAsList( ImmutableList.of( makeFuturePrints, makeFutureDocuments ) );

        // Then transform the RpcResults into 1.

        return Futures.transform( combinedFutures,
            new AsyncFunction<List<RpcResult<Void>>,RpcResult<Void>>() {
                @Override
                public ListenableFuture<RpcResult<Void>> apply( List<RpcResult<Void>> results )
                                                                                 throws Exception {
                    boolean atLeastOneSucceeded = false;
                    Builder<RpcError> errorList = ImmutableList.builder();
                    for( RpcResult<Void> result: results ) {
                        if( result.isSuccessful() ) {
                            atLeastOneSucceeded = true;
                        }

                        if( result.getErrors() != null ) {
                            errorList.addAll( result.getErrors() );
                        }
                    }

                    return Futures.immediateFuture(
                            RpcResultBuilder.<Void> status(atLeastOneSucceeded)
                            .withRpcErrors( errorList.build()).build());
                }
        } );
    }

    private ListenableFuture<RpcResult<Void>> createDocument( PrintType printType ) {

        return executor.submit( new Callable<RpcResult<Void>>() {

            @Override
            public RpcResult<Void> call() throws Exception {

             // we don't actually do anything here, just return a successful result.
                return RpcResultBuilder.<Void> success().build();
            }
        } );
    }

    @SuppressWarnings("deprecation")
    private Future<RpcResult<Void>> print(long numbersOfPages,
                                               long doneness ) {
        // Access the MultifunctionalService to make the toast.

        if( multifunctionalOutOfPaper )
        {
            log.info( "We're out of paper but we can send fax" );
            return Futures.immediateFuture( Rpcs.<Void> getRpcResult( true,
                       Arrays.asList( RpcErrors.getRpcError( "", "partial-operation", null,
                                          ErrorSeverity.WARNING,
                                          "Multifunctional is out of bread but we can send fax",
                                          ErrorType.APPLICATION, null ) ) ) );
        }

        PrintInput printInput = new PrintInputBuilder()
            .setNumberOfPages(numbersOfPages)
            .setPrintDoneness(doneness)
            .build();

        log.info("Printing page(s)...");
        return printService.print(printInput );
    }

    @Override
    public Boolean printAndSendFax() {
        try {
            long printDoneness = 5;
            long numberOfPages = 2;

            // This call has to block since we must return a result to the JMX client.
            RpcResult<Void> result = print( numberOfPages, printDoneness ).get();
            if( result.isSuccessful() ) {
                log.info( "printAndSendFax succeeded" );
            } else {
                log.warn( "printAndSendFax failed: " + result.getErrors() );
            }

            return result.isSuccessful();

        } catch( InterruptedException | ExecutionException e ) {
            log.warn( "An error occurred while maing breakfast: " + e );
        }

        return Boolean.FALSE;
    }

    @Override
    public void close() throws Exception {
        executor.shutdown();
    }

    @Override
    public void onMultifunctionalOutOfStock(
            MultifunctionalOutOfStock notification) {
        log.info( "MultifunctionalOutOfStock notification" );
        multifunctionalOutOfPaper = true;
    }

    @Override
    public void onMultifunctionalRestocked(MultifunctionalRestocked notification) {
        log.info( "MultifunctionalRestocked notification - amountOfPage: " + notification.getAmountOfPage() );
        multifunctionalOutOfPaper = false;
    }
}
