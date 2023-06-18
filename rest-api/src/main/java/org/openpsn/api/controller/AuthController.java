package org.openpsn.api.controller;

import io.jooby.annotation.POST;
import io.jooby.annotation.Path;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;

import java.util.UUID;

public class AuthController {
    private final JwtGenerator jwtGenerator;

    public AuthController(SignatureConfiguration signatureConfiguration) {
        this.jwtGenerator = new JwtGenerator(signatureConfiguration);
    }

    @POST
    @Path("/token")
    public TokenResponse exchangeForAuthToken(TokenRequest body) {
        final var user = new CommonProfile();
        user.setId(body.username);

        final var refreshToken = UUID.randomUUID().toString();
        // TODO: actually interact with the DB. Hash password, refreshToken

        final var accessToken = jwtGenerator.generate(user);
        return new TokenResponse(accessToken, refreshToken);
    }

    public record TokenRequest(
        String username,
        String password
    ) {
    }

    public record TokenResponse(
        String accessToken,
        String refreshToken
    ) {
    }
}
