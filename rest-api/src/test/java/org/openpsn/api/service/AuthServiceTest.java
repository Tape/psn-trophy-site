package org.openpsn.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openpsn.api.dao.UserDao;
import org.openpsn.api.exception.auth.BadCredentialsException;
import org.openpsn.api.model.AuthTokens;
import org.openpsn.api.model.User;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserDao userDao;

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        final var config = new SecretSignatureConfiguration(new byte[256]);
        authService = new AuthService(config, userDao);
    }

    @Test
    void authenticate_should_returnTokensIfSuccessful() {
        final var user = new User("123", "user", LocalDateTime.now(), LocalDateTime.now());
        doReturn(Optional.of(user)).when(userDao).authenticate("test", "pass");

        final var tokens = authService.authenticate("test", "pass");
        assertThat(tokens).isNotNull()
            .extracting(AuthTokens::accessToken, AuthTokens::refreshToken)
            .doesNotContainNull();
    }

    @Test
    void authenticate_should_throwExceptionIfUserEmpty() {
        assertThatExceptionOfType(BadCredentialsException.class)
            .isThrownBy(() -> authService.authenticate("test", "pass"));
    }
}
