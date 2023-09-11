package org.openpsn.api;

import dagger.Component;
import io.jooby.Jooby;
import io.jooby.flyway.FlywayModule;
import io.jooby.hikari.HikariModule;
import io.jooby.jackson.JacksonModule;
import io.jooby.pac4j.Pac4jModule;
import org.openpsn.api.controller.AuthController;
import org.openpsn.api.controller.UserController;
import org.openpsn.api.module.ApplicationModule;
import org.openpsn.api.module.DataAccessModule;
import org.openpsn.api.module.SecurityModule;
import org.pac4j.core.client.DirectClient;

import javax.inject.Singleton;

public class ApiApplication extends Jooby {
    public ApiApplication() {
        final var dagger = DaggerApiApplication_RestComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .build();

        // (De)serialization
        install(new JacksonModule());

        // Database
        install(new HikariModule());
        install(new FlywayModule());

        // API endpoints for authentication
        mvc(dagger.authController());

        // Protect API endpoints behind a bearer JWT
        final var pac4j = new Pac4jModule()
            .client("/api/*", conf -> dagger.apiAuthClient());
        install(pac4j);

        mvc(dagger.userController());
    }

    @Component(modules = {
        ApplicationModule.class,
        DataAccessModule.class,
        SecurityModule.class,
    })
    @Singleton
    interface RestComponent {
        DirectClient apiAuthClient();

        AuthController authController();

        UserController userController();
    }
}
