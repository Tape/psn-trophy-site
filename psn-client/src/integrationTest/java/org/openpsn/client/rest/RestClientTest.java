package org.openpsn.client.rest;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.model.MediaType;
import org.openpsn.IntegrationTest;
import org.openpsn.client.rest.exception.ContentTypeException;
import org.openpsn.client.rest.exception.StatusCodeException;

import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class RestClientTest extends IntegrationTest {
    private RestClient restClient;

    @BeforeEach
    public void setUp() {
        restClient = new RestClient();
    }

    @AfterEach
    public void tearDown() {
        mockServerClient.reset();
    }

    @Test
    public void requestObjectAsync_should_handleValidResponse() {
        mockServerClient.when(request("/test"))
            .respond(
                response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody("{ \"key\": \"key\", \"value\": \"value\" }")
            );

        final var request = HttpRequest.newBuilder(URI.create(mockServerHost + "/test")).build();

        final var response = restClient.requestObjectAsync(request, TestPayload.class).join();
        assertEquals("key", response.key);
        assertEquals("value", response.value);
    }

    @Test
    public void requestObjectAsync_should_throwOnNon200() {
        final var request = HttpRequest.newBuilder(URI.create(mockServerHost + "/test")).build();

        final var exception = assertThrows(CompletionException.class, () ->
            restClient.requestObjectAsync(request, TestPayload.class).join());
        assertInstanceOf(StatusCodeException.class, exception.getCause());
    }

    @Test
    public void requestObjectAsync_should_throwOnBadContentType() {
        mockServerClient.when(request("/test").withMethod("GET"))
            .respond(
                response()
                    .withContentType(MediaType.TEXT_PLAIN)
                    .withBody("Hello, world!")
            );

        final var request = HttpRequest.newBuilder(URI.create(mockServerHost + "/test")).build();

        final var exception = assertThrows(CompletionException.class, () ->
            restClient.requestObjectAsync(request, TestPayload.class).join());
        assertInstanceOf(ContentTypeException.class, exception.getCause());
    }

    @Test
    public void requestObjectAsync_should_throwOnDeserializationException() {
        mockServerClient.when(request("/test").withMethod("GET"))
            .respond(response().withContentType(MediaType.APPLICATION_JSON));

        final var request = HttpRequest.newBuilder(URI.create(mockServerHost + "/test")).build();

        final var exception = assertThrows(CompletionException.class, () ->
            restClient.requestObjectAsync(request, TestPayload.class).join());
        assertInstanceOf(UncheckedIOException.class, exception.getCause());
        assertInstanceOf(MismatchedInputException.class, exception.getCause().getCause());
    }

    private record TestPayload(
        String key,
        String value
    ) {
    }
}
