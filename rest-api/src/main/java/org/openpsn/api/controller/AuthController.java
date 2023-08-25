package org.openpsn.api.controller;

import io.jooby.annotation.POST;
import io.jooby.annotation.Path;
import org.openpsn.api.model.AuthTokens;
import org.openpsn.api.service.AuthService;
import org.pac4j.jwt.config.signature.SignatureConfiguration;

public class AuthController {
    private final AuthService authService;

    public AuthController(SignatureConfiguration signatureConfiguration) {
        this.authService = new AuthService(signatureConfiguration);
    }

    @POST
    @Path("/token")
    public AuthTokens exchangeForAuthToken(TokenRequest body) {
        return authService.authenticate(body.username, body.password);
    }

    public record TokenRequest(
        String username,
        String password
    ) {
    }
}
