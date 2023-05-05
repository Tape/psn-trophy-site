package org.openpsn.client.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.openpsn.client.rest.exception.ContentTypeException;
import org.openpsn.client.rest.exception.StatusCodeException;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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

    public <T> CompletableFuture<T> requestObjectAsync(
        @NonNull RequestEntity<?> entity,
        @NonNull Class<T> responseType
    ) {
        final var request = toHttpRequest(entity);
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

    private HttpRequest.BodyPublisher toBodyPublisher(Object payload) {
        if (payload == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        return HttpRequest.BodyPublishers.ofInputStream(() -> {
            try {
                final var outputStream = new PipedOutputStream();
                final var inputStream = new PipedInputStream(outputStream);
                objectMapper.writeValue(outputStream, payload);
                return inputStream;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    private HttpRequest toHttpRequest(@NonNull RequestEntity<?> entity) {
        final var builder = HttpRequest.newBuilder(entity.getUri());

        final var methodString = entity.getMethod().name();
        final var bodyPublisher = toBodyPublisher(entity.getPayload());
        builder.method(methodString, bodyPublisher);

        for (final var headerEntry : entity.getHeaders().entrySet()) {
            final var headerName = headerEntry.getKey();
            for (final var headerValue : headerEntry.getValue()) {
                builder.header(headerName, headerValue);
            }
        }

        return builder.build();
    }
}
