package org.openpsn.client.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import org.openpsn.client.rest.exception.ContentTypeException;
import org.openpsn.client.rest.exception.StatusCodeException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class RestClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Creates an instance of the REST client that manages its own deserialization and thread management.
     */
    public RestClient() {
        this(new ObjectMapper().registerModule(new JavaTimeModule()));
    }

    /**
     * Creates an instance of the REST client that accepts an ObjectMapper for deserialization and handles its own
     * thread management.
     *
     * @param objectMapper is the ObjectMapper it should use for deserialization.
     */
    public RestClient(@NonNull ObjectMapper objectMapper) {
        this(objectMapper, null);
    }

    /**
     * Creates an instance of the REST client that allows the client to fully customize behavior.
     *
     * @param objectMapper is the ObjectMapper it should use for deserialization.
     * @param executor     is the executor that should be used for thread management.
     */
    public RestClient(@NonNull ObjectMapper objectMapper, Executor executor) {
        this.objectMapper = objectMapper;

        final var httpClientBuilder = HttpClient.newBuilder();
        if (executor != null) {
            httpClientBuilder.executor(executor);
        }

        this.httpClient = httpClientBuilder.build();
    }

    /**
     * Performs a request and returns the raw Response object so the consumer can choose how to handle response data
     * based on how they expect the upstream service to respond.
     *
     * @param entity is the entity specifying how the request should be handled.
     * @return the raw response object.
     */
    public CompletableFuture<Response> requestAsync(@NonNull RequestEntity<?> entity) {
        final var request = toHttpRequest(entity);
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
            .thenApply(ResponseImpl::new);
    }

    /**
     * Performs a request and returns the body deserialized to a specified type as long as it returns a successful 2xx
     * status code.
     *
     * @param entity       is the entity specifying how the request should be handled.
     * @param responseType is the type the body should be deserialized to.
     */
    public <T> CompletableFuture<T> requestObjectAsync(
        @NonNull RequestEntity<?> entity,
        @NonNull Class<T> responseType
    ) {
        return requestAsync(entity)
            .thenApply(response -> {
                // Validate for a 2xx status code
                if (!response.statusCode().is2xxSuccessful()) {
                    throw new StatusCodeException(response.statusCode().getCode());
                }

                return response.readObject(responseType);
            });
    }

    private HttpRequest.BodyPublisher toBodyPublisher(@NonNull RequestEntity<?> entity) {
        final var payload = entity.getPayload();
        if (payload == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        // String and byte[] types use their own respective publishers
        if (payload instanceof String str) {
            return HttpRequest.BodyPublishers.ofString(str);
        } else if (payload instanceof byte[] bytes) {
            return HttpRequest.BodyPublishers.ofByteArray(bytes);
        }

        // Attempt to serialize to JSON, and add the header if successful
        final byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(payload);
            entity.contentType(ContentType.APPLICATION_JSON);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return HttpRequest.BodyPublishers.ofByteArray(bytes);
    }

    private HttpRequest toHttpRequest(@NonNull RequestEntity<?> entity) {
        final var builder = HttpRequest.newBuilder(entity.getUri());

        final var methodString = entity.getMethod().name();
        final var bodyPublisher = toBodyPublisher(entity);
        builder.method(methodString, bodyPublisher);

        for (final var headerEntry : entity.getHeaders().entrySet()) {
            final var headerName = headerEntry.getKey();
            for (final var headerValue : headerEntry.getValue()) {
                builder.header(headerName, headerValue);
            }
        }

        return builder.build();
    }

    private class ResponseImpl implements Response {
        private final HttpResponse<byte[]> response;

        private ResponseImpl(@NonNull HttpResponse<byte[]> response) {
            this.response = response;
        }

        @Override
        public Optional<String> contentType() {
            return response.headers().firstValue("Content-Type");
        }

        @Override
        public HttpHeaders headers() {
            return response.headers();
        }

        @Override
        public <T> T readObject(Class<T> type) {
            // Extract the Content-Type header for validation as we only support JSON
            final var contentType = contentType()
                .orElseThrow(() -> new ContentTypeException("<missing header>"));

            if (!ContentType.APPLICATION_JSON.matches(contentType)) {
                throw new ContentTypeException(contentType);
            }

            try {
                return objectMapper.readValue(response.body(), type);
            } catch (IOException e) {
                // TODO: Use something more specific here?
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public StatusCode statusCode() {
            return StatusCode.of(response.statusCode());
        }
    }
}
