package org.openpsn.api.controller;

import io.jooby.annotation.POST;
import io.jooby.annotation.Path;
import lombok.RequiredArgsConstructor;
import org.openpsn.api.model.AuthTokens;
import org.openpsn.api.service.AuthService;

import javax.inject.Inject;
import javax.inject.Singleton;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class AuthController {
    private final AuthService authService;

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
