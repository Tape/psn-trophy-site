package org.openpsn.api.module;

import dagger.Module;
import dagger.Provides;
import io.jooby.Jooby;
import org.pac4j.core.client.DirectClient;
import org.pac4j.http.client.direct.DirectBearerAuthClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;

import javax.inject.Singleton;

@Module
public class SecurityModule {
    @Provides
    @Singleton
    public DirectClient directClient(SignatureConfiguration signatureConfiguration) {
        final var authenticator = new JwtAuthenticator(signatureConfiguration);
        return new DirectBearerAuthClient(authenticator);
    }

    @Provides
    @Singleton
    public SignatureConfiguration signatureConfiguration(Jooby application) {
        final var salt = application.getConfig().getString("jwt.salt");
        return new SecretSignatureConfiguration(salt);
    }
}
