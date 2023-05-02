package org.openpsn.client.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.openpsn.client.rest.exception.ContentTypeException;
import org.openpsn.client.rest.exception.StatusCodeException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class RestClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RestClient() {
        this(new ObjectMapper());
    }

    public RestClient(@NonNull ObjectMapper objectMapper) {
        this(objectMapper, null);
    }

    public RestClient(@NonNull ObjectMapper objectMapper, Executor executor) {
        this.objectMapper = objectMapper;

        final var httpClientBuilder = HttpClient.newBuilder();
        if (executor != null) {
            httpClientBuilder.executor(executor);
        }

        this.httpClient = httpClientBuilder.build();
    }

    public <T> CompletableFuture<T> requestObjectAsync(HttpRequest request, Class<T> responseType) {
        final var responseFuture = httpClient.sendAsync(request, response -> {
            // Validate for a 2xx status code
            final var statusCode = StatusCode.of(response.statusCode());
            if (!statusCode.is2xxSuccessful()) {
                throw new StatusCodeException(statusCode.getCode());
            }

            // Extract the Content-Type header for validation
            final var contentType = response.headers().firstValue("Content-Type")
                .orElseThrow(() -> new ContentTypeException("<missing header>"));
            // We currently only support JSON parsing
            if (!ContentType.APPLICATION_JSON.matches(contentType)) {
                throw new ContentTypeException(contentType);
            }

            // Create a BodySubscriber that converts an InputStream into T
            return HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofInputStream(),
                inputStream -> {
                    try {
                        return objectMapper.readValue(inputStream, responseType);
                    } catch (IOException e) {
                        // TODO: Use something more specific here?
                        throw new UncheckedIOException(e);
                    }
                });
        });

        return responseFuture.thenApply(HttpResponse::body);
    }
}
