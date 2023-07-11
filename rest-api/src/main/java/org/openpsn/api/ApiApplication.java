package org.openpsn.api;

import io.jooby.Jooby;
import io.jooby.flyway.FlywayModule;
import io.jooby.hikari.HikariModule;
import io.jooby.jackson.JacksonModule;
import io.jooby.pac4j.Pac4jModule;
import org.openpsn.api.controller.AuthController;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.DirectBearerAuthClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;

public class ApiApplication extends Jooby {
    public ApiApplication() {
        // (De)serialization
        install(new JacksonModule());

        // Database
        install(new HikariModule());
        install(new FlywayModule());

        // API endpoints for authentication
        mvc(new AuthController(secretSignatureConfiguration()));

        // Protect API endpoints behind a bearer JWT
        install(new Pac4jModule()
            .client("/api/*", conf -> {
                final var authenticator = new JwtAuthenticator(secretSignatureConfiguration());
                return new DirectBearerAuthClient(authenticator);
            })
        );

        get("/api/hello", ctx -> ctx.<CommonProfile>getUser().getId());
    }

    private SecretSignatureConfiguration secretSignatureConfiguration() {
        final var salt = getConfig().getString("jwt.salt");
        return new SecretSignatureConfiguration(salt);
    }
}
