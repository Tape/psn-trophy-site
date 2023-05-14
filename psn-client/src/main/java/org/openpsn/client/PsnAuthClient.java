package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.response.TokenResponse;
import org.openpsn.client.rest.ContentType;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class PsnAuthClient extends AbstractApiClient {
    PsnAuthClient(@NonNull RestClient restClient) {
        super(restClient);
    }

    public CompletableFuture<String> getAccessCode(@NonNull String npSso) {
        final var query = urlEncodeMap(Map.of(
            "access_type", "offline",
            "client_id", "09515159-7237-4370-9b40-3806e67c0891",
            "response_type", "code",
            "scope", "psn:mobile.v2.core psn:clientapp",
            "redirect_uri", "com.scee.psxandroid.scecompcall://redirect"
        ));

        final var entity = RequestEntity.get(URI.create(urlBase() + "/authorize?" + query))
            .header("Cookie", "npsso=" + npSso);

        return restClient.requestAsync(entity)
            .thenApply(response -> {
                final var location = response.headers()
                    .firstValue("Location")
                    .map(URI::create)
                    .orElseThrow(() -> new IllegalArgumentException("Missing location header"));

                final var queryParams = urlDecodeMap(location.getQuery());
                return Objects.requireNonNull(queryParams.get("code"));
            });
    }

    public CompletableFuture<TokenResponse> getAccessToken(@NonNull String code) {
        final var body = urlEncodeMap(Map.of(
            "code", code,
            "redirect_uri", "com.scee.psxandroid.scecompcall://redirect",
            "grant_type", "authorization_code",
            "token_format", "jwt"
        ));

        final var entity = RequestEntity.post(URI.create(urlBase() + "/token"), body)
            .contentType(ContentType.FORM_URLENCODED)
            .header("Authorization", "Basic MDk1MTUxNTktNzIzNy00MzcwLTliNDAtMzgwNmU2N2MwODkxOnVjUGprYTV0bnRCMktxc1A=");

        return restClient.requestObjectAsync(entity, TokenResponse.class);
    }

    @Override
    protected String urlBase() {
        return System.getProperty(
            "openpsn.client.authUrlBase",
            "https://ca.account.sony.com/api/authz/v3/oauth");
    }
}
