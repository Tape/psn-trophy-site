package org.openpsn.api.service;

import org.openpsn.api.model.AuthTokens;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;

import java.util.UUID;

public class AuthService {
    private final JwtGenerator jwtGenerator;

    public AuthService(SignatureConfiguration signatureConfiguration) {
        this.jwtGenerator = new JwtGenerator(signatureConfiguration);
    }

    public AuthTokens authenticate(String username, String password) {
        final var user = new CommonProfile();
        user.setId(username);

        final var refreshToken = UUID.randomUUID().toString();
        // TODO: actually interact with the DB. Hash password, refreshToken

        final var accessToken = jwtGenerator.generate(user);
        return new AuthTokens(accessToken, refreshToken);
    }
}
