package org.openpsn.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.model.MediaType;
import org.openpsn.IntegrationTest;
import org.openpsn.client.response.TokenResponse;
import org.openpsn.client.rest.RestClient;
import org.openpsn.test.junit.extension.SystemPropertiesExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;
import static org.mockserver.model.ParameterBody.params;
import static org.openpsn.test.util.ResourceUtils.getResourceBytes;

@ExtendWith(SystemPropertiesExtension.class)
class PsnAuthClientIntegrationTest extends IntegrationTest {
    private PsnAuthClient client;

    @BeforeEach
    public void setUp() {
        final var restClient = new RestClient();
        client = new PsnAuthClient(restClient);
        System.setProperty("openpsn.client.authUrlBase", mockServerHost);
    }

    @AfterEach
    public void tearDown() {
        mockServerClient.reset();
    }

    @Test
    public void getAccessToken_should_returnTokensIfSuccessful() throws IOException {
        // Access Code
        mockServerClient
            .when(
                request()
                    .withPath("/authorize")
                    .withHeader("Cookie", "npsso=npsso")
                    .withQueryStringParameters(
                        param("access_type", "offline"),
                        param("client_id", "09515159-7237-4370-9b40-3806e67c0891"),
                        param("response_type", "code"),
                        param("scope", "psn:mobile.v2.core psn:clientapp"),
                        param("redirect_uri", "com.scee.psxandroid.scecompcall://redirect")
                    )
            )
            .respond(
                response()
                    .withStatusCode(302)
                    .withHeader("Location", mockServerHost + "?code=abc123")
            );

        // Access Token
        mockServerClient
            .when(
                request()
                    .withMethod("POST")
                    .withPath("/token")
                    .withHeader("Authorization", "Basic MDk1MTUxNTktNzIzNy00MzcwLTliNDAtMzgwNmU2N2MwODkxOnVjUGprYTV0bnRCMktxc1A=")
                    .withContentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .withBody(params(
                        param("code", "abc123"),
                        param("redirect_uri", "com.scee.psxandroid.scecompcall://redirect"),
                        param("grant_type", "authorization_code"),
                        param("token_format", "jwt")
                    ))
            )
            .respond(
                response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(getResourceBytes("responses/auth/tokens.json"))
            );

        final var tokens = client.getAccessCode("npsso")
            .thenCompose(client::getAccessToken)
            .join();

        assertThat(tokens)
            .extracting(TokenResponse::accessToken, TokenResponse::tokenType)
            .containsExactly("access_token", "bearer");
    }
}
