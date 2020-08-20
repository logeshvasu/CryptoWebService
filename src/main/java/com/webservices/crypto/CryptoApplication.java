package com.webservices.crypto;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.webservices.crypto.config.CryptoConfiguration;
import com.webservices.crypto.config.CryptoHealthCheck;
import com.webservices.crypto.resource.v1.CryptoResource;
import com.webservices.crypto.service.CryptoService;
import com.webservices.crypto.service.CryptoServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;


import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoApplication extends Application<CryptoConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoApplication
            .class);

    @Override
    public void initialize(Bootstrap<CryptoConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }

    @Override
    public void run(CryptoConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Registering REST resources");
        Injector injector = createInjector(configuration);
        environment.jersey().register(injector.getInstance(
                CryptoResource.class));

        final CryptoHealthCheck healthCheck = new CryptoHealthCheck();
        environment
                .healthChecks()
                .register("CryptoApplicationHealth", healthCheck);
    }

    public static void main(String[] args) throws Exception {
        new CryptoApplication().run("server", "config.yml");
    }

    private Injector createInjector(final CryptoConfiguration configuration) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CryptoConfiguration.class).toInstance(configuration);
                bind(CryptoService.class).to(CryptoServiceImpl.class);
            }
        });
    }

}
