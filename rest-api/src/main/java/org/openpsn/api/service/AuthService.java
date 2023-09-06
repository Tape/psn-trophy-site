package org.openpsn.api.service;

import org.openpsn.api.dao.UserDao;
import org.openpsn.api.exception.auth.BadCredentialsException;
import org.openpsn.api.model.AuthTokens;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;

import javax.inject.Inject;
import java.util.UUID;

public class AuthService {
    private final JwtGenerator jwtGenerator;
    private final UserDao userDao;

    @Inject
    public AuthService(SignatureConfiguration signatureConfiguration, UserDao userDao) {
        this.jwtGenerator = new JwtGenerator(signatureConfiguration);
        this.userDao = userDao;
    }

    public AuthTokens authenticate(String psnName, String password) throws BadCredentialsException {
        final var user = userDao.authenticate(psnName, password)
            .orElseThrow(BadCredentialsException::new);

        final var profile = new CommonProfile();
        profile.setId(user.id());

        final var accessToken = jwtGenerator.generate(profile);
        final var refreshToken = UUID.randomUUID().toString();

        return new AuthTokens(accessToken, refreshToken);
    }
}
