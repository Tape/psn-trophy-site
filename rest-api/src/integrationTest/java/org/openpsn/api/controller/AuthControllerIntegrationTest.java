package org.openpsn.api.controller;

import io.jooby.StatusCode;
import io.jooby.test.MockContext;
import io.jooby.test.MockRouter;
import org.junit.jupiter.api.Test;
import org.openpsn.api.IntegrationTest;
import org.openpsn.api.model.AuthTokens;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class AuthControllerIntegrationTest {
    @Test
    public void exchangeForAuthToken_should_generateTokens(MockRouter router) {
        final var payload = new AuthController.TokenRequest("test", "pass");
        final var context = new MockContext().setBodyObject(payload);

        router.post("/token", context, response -> {
            assertThat(response.getStatusCode()).isEqualTo(StatusCode.OK);
            assertThat((AuthTokens) response.value())
                .extracting(AuthTokens::accessToken, AuthTokens::refreshToken)
                .doesNotContainNull();
        });
    }
}
