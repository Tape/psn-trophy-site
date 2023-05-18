package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

abstract class AbstractApiClient {
    protected final RestClient restClient;
    protected final String accessToken;

    protected AbstractApiClient(@NonNull RestClient restClient) {
        this(restClient, null);
    }

    protected AbstractApiClient(@NonNull RestClient restClient, String accessToken) {
        this.restClient = restClient;
        this.accessToken = accessToken;
    }

    protected <T> CompletableFuture<T> get(@NonNull String path, Class<T> responseType) {
        final var entity = RequestEntity.get(URI.create(urlBase() + path));
        if (accessToken != null) {
            entity.headers(headers -> headers.set("Authorization", "Bearer " + accessToken));
        }

        return restClient.requestObjectAsync(entity, responseType);
    }

    protected abstract String urlBase();
}
