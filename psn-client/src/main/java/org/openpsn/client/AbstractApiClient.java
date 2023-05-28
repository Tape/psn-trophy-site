package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

abstract class AbstractApiClient {
    protected final RestClient restClient;
    protected final String accessToken;

    /**
     * Creates a new client with the given REST client that does not contain an authentication context.
     *
     * @param restClient is the REST client to use.
     */
    protected AbstractApiClient(@NonNull RestClient restClient) {
        this(restClient, null);
    }

    /**
     * Creates a new client with the given REST client and an optional (nullable) access token for use in authenticated
     * requests.
     *
     * @param restClient  is the REST client to use.
     * @param accessToken is the access token for the user.
     */
    protected AbstractApiClient(@NonNull RestClient restClient, String accessToken) {
        this.restClient = restClient;
        this.accessToken = accessToken;
    }

    /**
     * Provides a generic GET call to a given path. The host and leading path components are provided by the
     * implementation of urlBase().
     *
     * @param path         is the path to the resource.
     * @param responseType is the type that should be returned by the resource.
     */
    protected <T> CompletableFuture<T> get(@NonNull String path, Class<T> responseType) {
        final var entity = RequestEntity.get(URI.create(urlBase() + path));
        if (accessToken != null) {
            entity.headers(headers -> headers.set("Authorization", "Bearer " + accessToken));
        }

        return restClient.requestObjectAsync(entity, responseType);
    }

    /**
     * Generates the base url for the client.
     */
    protected abstract String urlBase();
}
