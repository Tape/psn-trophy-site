package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;

abstract class AbstractApiClient {
    protected final RestClient restClient;

    protected AbstractApiClient(@NonNull RestClient restClient) {
        this.restClient = restClient;
    }

    protected HttpRequest.Builder requestBuilder(@NonNull URI uri) {
        return requestBuilder(uri, null);
    }

    protected HttpRequest.Builder requestBuilder(@NonNull URI uri, HttpHeaders headers) {
        // FIXME: I honestly hate this approach, better refactored into a utility class
        final var builder = HttpRequest.newBuilder(uri);
        if (headers == null) {
            return builder;
        }

        for (final var entry : headers.map().entrySet()) {
            for (final var value : entry.getValue()) {
                builder.header(entry.getKey(), value);
            }
        }
        return builder;
    }

    protected abstract String urlBase();
}
