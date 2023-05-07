package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

abstract class AbstractApiClient {
    private final String accessToken;
    private final RestClient restClient;

    protected AbstractApiClient(@NonNull RestClient restClient, @NonNull String accessToken) {
        this.accessToken = accessToken;
        this.restClient = restClient;
    }

    protected <T> CompletableFuture<T> get(@NonNull String path, Class<T> responseType) {
        final var entity = RequestEntity.get(URI.create(urlBase() + path))
            .header("Authorization", "Bearer " + accessToken);

        return restClient.requestObjectAsync(entity, responseType);
    }

    protected abstract String urlBase();
}
