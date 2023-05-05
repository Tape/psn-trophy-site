package org.openpsn.client.rest;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.model.MediaType;
import org.openpsn.IntegrationTest;
import org.openpsn.client.rest.exception.ContentTypeException;
import org.openpsn.client.rest.exception.StatusCodeException;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
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

        final var entity = RequestEntity.get(URI.create(mockServerHost + "/test"));

        final var response = restClient.requestObjectAsync(entity, TestPayload.class).join();
        assertThat(response.key).isEqualTo("key");
        assertThat(response.value).isEqualTo("value");
    }

    @Test
    public void requestObjectAsync_should_throwOnNon200() {
        final var entity = RequestEntity.get(URI.create(mockServerHost + "/test"));

        assertThatRuntimeException()
            .isThrownBy(() -> restClient.requestObjectAsync(entity, TestPayload.class).join())
            .withRootCauseInstanceOf(StatusCodeException.class);
    }

    @Test
    public void requestObjectAsync_should_throwOnBadContentType() {
        mockServerClient.when(request("/test").withMethod("GET"))
            .respond(
                response()
                    .withContentType(MediaType.TEXT_PLAIN)
                    .withBody("Hello, world!")
            );

        final var entity = RequestEntity.get(URI.create(mockServerHost + "/test"));

        assertThatRuntimeException()
            .isThrownBy(() -> restClient.requestObjectAsync(entity, TestPayload.class).join())
            .withRootCauseInstanceOf(ContentTypeException.class);
    }

    @Test
    public void requestObjectAsync_should_throwOnDeserializationException() {
        mockServerClient.when(request("/test").withMethod("GET"))
            .respond(response().withContentType(MediaType.APPLICATION_JSON));

        final var entity = RequestEntity.get(URI.create(mockServerHost + "/test"));

        assertThatRuntimeException()
            .isThrownBy(() -> restClient.requestObjectAsync(entity, TestPayload.class).join())
            .withRootCauseInstanceOf(MismatchedInputException.class);
    }

    private record TestPayload(
        String key,
        String value
    ) {
    }
}
