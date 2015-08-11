/*
 * Copyright(c) Inocybe Technologies and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.multifunctional.impl.rev141210;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.multifunctional.impl.MultifunctionalProvider;
//import org.opendaylight.multifunctional.impl.MultifunctionalProvider;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalService;
import org.opendaylight.yangtools.concepts.ListenerRegistration;

public class MultifunctionalModule
        extends
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.multifunctional.impl.rev141210.AbstractMultifunctionalModule {
    public MultifunctionalModule(
            org.opendaylight.controller.config.api.ModuleIdentifier identifier,
            org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public MultifunctionalModule(
            org.opendaylight.controller.config.api.ModuleIdentifier identifier,
            org.opendaylight.controller.config.api.DependencyResolver dependencyResolver,
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.multifunctional.impl.rev141210.MultifunctionalModule oldModule,
            java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        MultifunctionalProvider provider = new MultifunctionalProvider();

        provider.setNotificationProvider(getNotificationServiceDependency());

        // Register runtimeBean for printing statistics via JMX
        final MultifunctionalRuntimeRegistration runtimeReg = getRootRuntimeBeanRegistratorWrapper()
                .register(provider);

        DataBroker dataBrokerService = getBrokerDependency();
        provider.setDataProvider(dataBrokerService);

        final BindingAwareBroker.RpcRegistration<MultifunctionalService> rpcRegistration = getRpcRegistryDependency()
                .addRpcImplementation(MultifunctionalService.class, provider);

        final ListenerRegistration<DataChangeListener> dataChangeListenerRegistration = dataBrokerService
                .registerDataChangeListener(LogicalDatastoreType.CONFIGURATION,
                        MultifunctionalProvider.MULTIFUNCTIONAL_IID, provider,
                        DataChangeScope.SUBTREE);

        final class AutoCloseableToaster implements AutoCloseable {
            @Override
            public void close() throws Exception {
                dataChangeListenerRegistration.close(); // closes the listener
                                                        // registrations
                                                        // (removes it)
                runtimeReg.close();
            }
        }

        return provider;
    }

}
