package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.response.TrophyTitleResponse;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.concurrent.CompletableFuture;

public class PsnTrophyClient extends AbstractApiClient {
    private static final String DEFAULT_URL_BASE = "https://m.np.playstation.com/api/trophy/v1";

    private final HttpHeaders authHeaders;

    PsnTrophyClient(@NonNull RestClient restClient, HttpHeaders authHeaders) {
        super(restClient);
        this.authHeaders = authHeaders;
    }

    public CompletableFuture<TrophyTitleResponse> getTrophyTitles() {
        return getTrophyTitles("me");
    }

    public CompletableFuture<TrophyTitleResponse> getTrophyTitles(@NonNull String userId) {
        final var uri = URI.create(urlBase() + String.format("/users/%s/trophyTitles", userId));
        return restClient.requestObjectAsync(RequestEntity.get(uri), TrophyTitleResponse.class);
    }

    @Override
    protected String urlBase() {
        return System.getProperty("openpsn.client.trophyUrlBase", DEFAULT_URL_BASE);
    }
}
