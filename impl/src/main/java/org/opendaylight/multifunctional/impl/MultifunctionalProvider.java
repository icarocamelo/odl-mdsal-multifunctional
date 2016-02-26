/*
 * Copyright (c) 2016 Inocybe Technologies and others. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.multifunctional.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.multifunctional.impl.rev141210.modules.module.configuration.Multifunctional;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.multifunctional.impl.rev141210.modules.module.configuration.MultifunctionalBuilder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultifunctionalProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(MultifunctionalProvider.class);
    public static final InstanceIdentifier<?> MULTIFUNCTIONAL_IID = InstanceIdentifier.create(Multifunctional.class);
    private DataBroker dataBroker;

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("MultifunctionalProvider Session Initiated");

        dataBroker =  session.getSALService(DataBroker.class);
//        syncCupWithDataStore(LogicalDatastoreType.OPERATIONAL, MULTIFUNCTIONAL_IID, buildOperational());
//        syncCupWithDataStore(LogicalDatastoreType.CONFIGURATION, MULTIFUNCTIONAL_IID, buildConfig());
    }

    private DataObject buildOperational() {
        return new MultifunctionalBuilder().build();
    }

    private DataObject buildConfig() {
        return new MultifunctionalBuilder().build();
    }

    @Override
    public void close() throws Exception {
        LOG.info("MultifunctionalProvider Closed");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void syncCupWithDataStore(final LogicalDatastoreType store,
                                      InstanceIdentifier iid,
                                      final DataObject object) {
        WriteTransaction transaction = dataBroker.newWriteOnlyTransaction();
        transaction.put(store, iid, object);
        // Perform the tx.submit asynchronously
        Futures.addCallback(transaction.submit(), new FutureCallback<Void>() {
            @Override
            public void onSuccess(final Void result) {
                LOG.info("SyncStore {} with object {} succeeded", store, object);
            }
            @Override
            public void onFailure(final Throwable throwable)  {
                LOG.error("SyncStore {} with object {} failed", store, object);
            }
        });
    }

//    private Cup buildOperationalCup() {
//        return new MultifunctionalBuilder()
//                .setCupManufacturer(CupConstants.CUP_MANUFACTURER)
//                .setCupModelNumber(CupConstants.CUP_MODEL_NUMBER)
//                .setAmmountOfCupsInStock(cupState.getAmountOfCupsInStock().get())
//                .setAmmountOfCupsMade(cupState.getCupsMade().get())
//                .setCupStatus(CupStatus.values()[cupState.getCupStatus().getStatus()])
//                .build();
//    }
}
