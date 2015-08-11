package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.config.office.consumer.impl.rev140131;

import org.opendaylight.controller.office.api.impl.OfficeServiceImpl;
import org.opendaylight.yang.gen.v1.http.inocybe.com.ns.multifunctional.rev150804.MultifunctionalService;
import org.opendaylight.yangtools.concepts.Registration;
import org.opendaylight.yangtools.yang.binding.NotificationListener;

public class OfficeServiceModule
        extends
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.config.office.consumer.impl.rev140131.AbstractOfficeServiceModule {

    public OfficeServiceModule(
            org.opendaylight.controller.config.api.ModuleIdentifier identifier,
            org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public OfficeServiceModule(
            org.opendaylight.controller.config.api.ModuleIdentifier identifier,
            org.opendaylight.controller.config.api.DependencyResolver dependencyResolver,
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.config.office.consumer.impl.rev140131.OfficeServiceModule oldModule,
            java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        MultifunctionalService svc = getRpcRegistryDependency().getRpcService(
                MultifunctionalService.class);

        OfficeServiceImpl service = new OfficeServiceImpl(svc);

        final Registration multifunctionalListenerReg =
                getNotificationServiceDependency().registerNotificationListener( service );

        final OfficeServiceRuntimeRegistration runtimeReg = getRootRuntimeBeanRegistratorWrapper()
                .register(service);

        final class AutoCloseableOfficeService implements AutoCloseable {
            @Override
            public void close() throws Exception {
                multifunctionalListenerReg.close();
                runtimeReg.close();
            }
        }

        return service;
    }
}