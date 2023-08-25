package org.openpsn.api;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.jooby.Jooby;
import io.jooby.flyway.FlywayModule;
import io.jooby.hikari.HikariModule;
import io.jooby.jackson.JacksonModule;
import io.jooby.pac4j.Pac4jModule;
import lombok.RequiredArgsConstructor;
import org.openpsn.api.controller.AuthController;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.DirectBearerAuthClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;

import javax.inject.Singleton;

public class ApiApplication extends Jooby {
    public ApiApplication() {
        final var dagger = DaggerApiApplication_ControllerComponent.builder()
            .apiApplicationModule(new ApiApplicationModule(this))
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

        get("/api/hello", ctx -> ctx.<CommonProfile>getUser().getId());
    }

    @Module
    @RequiredArgsConstructor
    public static class ApiApplicationModule {
        private final Jooby app;

        @Provides
        @Singleton
        public DirectClient directClient() {
            final var authenticator = new JwtAuthenticator(signatureConfiguration());
            return new DirectBearerAuthClient(authenticator);
        }

        @Provides
        @Singleton
        public SignatureConfiguration signatureConfiguration() {
            final var salt = app.getConfig().getString("jwt.salt");
            return new SecretSignatureConfiguration(salt);
        }
    }

    @Component(modules = {ApiApplicationModule.class})
    @Singleton
    interface ControllerComponent {
        DirectClient apiAuthClient();

        AuthController authController();
    }
}
