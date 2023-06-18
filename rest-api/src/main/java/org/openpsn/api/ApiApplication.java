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

import java.util.Arrays;

public class ApiApplication extends Jooby {
    public ApiApplication() {
        // (De)serialization
        install(new JacksonModule());

        // Database
        install(new HikariModule());
        install(new FlywayModule());

        // Please don't actually do this
        final var saltChars = new char[256];
        Arrays.fill(saltChars, 'a');
        final var salt = new String(saltChars);
        final var signatureConfiguration = new SecretSignatureConfiguration(salt);

        install(new Pac4jModule()
            .client("/api/*", conf -> {
                final var authenticator = new JwtAuthenticator(signatureConfiguration);
                return new DirectBearerAuthClient(authenticator);
            })
        );

        mvc(new AuthController(signatureConfiguration));

        get("/api/hello", ctx -> ctx.<CommonProfile>getUser().getId());
    }
}
