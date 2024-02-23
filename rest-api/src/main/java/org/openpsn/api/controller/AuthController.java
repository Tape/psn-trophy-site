package org.openpsn.api.controller;

import io.jooby.annotation.POST;
import io.jooby.annotation.Path;
import lombok.RequiredArgsConstructor;
import org.openpsn.api.model.AuthTokens;
import org.openpsn.api.model.Registration;
import org.openpsn.api.service.AuthService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Path("/auth")
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class AuthController {
    private final AuthService authService;

    @POST
    @Path("/register")
    public Registration register(AuthenticationRequest body) {
        return authService.register(body.username, body.password);
    }

    @POST
    @Path("/token")
    public AuthTokens exchangeForAuthToken(AuthenticationRequest body) {
        return authService.authenticate(body.username, body.password);
    }

    public record AuthenticationRequest(
        String username,
        String password
    ) {
    }
}
