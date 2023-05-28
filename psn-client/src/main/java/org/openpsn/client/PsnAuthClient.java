package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.response.TokenResponse;
import org.openpsn.client.rest.ContentType;
import org.openpsn.client.rest.Headers;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.openpsn.client.rest.util.EncodingUtils.urlDecodeMap;
import static org.openpsn.client.rest.util.EncodingUtils.urlEncodeMap;

public class PsnAuthClient extends AbstractApiClient {
    /**
     * Creates an authentication client for use exchanging user credentials for tokens.
     *
     * @param restClient is the shared REST client.
     */
    PsnAuthClient(@NonNull RestClient restClient) {
        super(restClient);
    }

    /**
     * Fetches an access code from the authentication service which is used to exchange for access tokens.
     *
     * @param npSso is the npsso value.
     */
    public CompletableFuture<String> getAccessCode(@NonNull String npSso) {
        final var query = urlEncodeMap(Map.of(
            "access_type", "offline",
            "client_id", "09515159-7237-4370-9b40-3806e67c0891",
            "response_type", "code",
            "scope", "psn:mobile.v2.core psn:clientapp",
            "redirect_uri", "com.scee.psxandroid.scecompcall://redirect"
        ));

        final var entity = RequestEntity.get(URI.create(urlBase() + "/authorize?" + query))
            .headers(headers -> headers.set("Cookie", "npsso=" + npSso));

        return restClient.requestAsync(entity)
            .thenApply(response -> {
                final var location = response.headers()
                    .getFirst(Headers.LOCATION)
                    .map(URI::create)
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'Location' header"));

                final var queryParams = urlDecodeMap(location.getQuery());
                final var code = queryParams.get("code");
                if (code == null || code.isEmpty()) {
                    throw new NullPointerException("Missing code");
                }

                return Objects.requireNonNull(code.get(0));
            });
    }

    /**
     * Fetches access tokens for the given access code. This also includes user details via the id token, the refresh
     * token, expiry and scope information.
     *
     * @param code is the access code.
     */
    public CompletableFuture<TokenResponse> getAccessToken(@NonNull String code) {
        final var body = urlEncodeMap(Map.of(
            "code", code,
            "redirect_uri", "com.scee.psxandroid.scecompcall://redirect",
            "grant_type", "authorization_code",
            "token_format", "jwt"
        ));

        final var entity = RequestEntity.post(URI.create(urlBase() + "/token"), body)
            .headers(headers -> {
                headers.basicAuth("09515159-7237-4370-9b40-3806e67c0891", "ucPjka5tntB2KqsP");
                headers.contentType(ContentType.FORM_URLENCODED);
            });

        return restClient.requestObjectAsync(entity, TokenResponse.class);
    }

    @Override
    protected String urlBase() {
        return System.getProperty(
            "openpsn.client.authUrlBase",
            "https://ca.account.sony.com/api/authz/v3/oauth");
    }
}
