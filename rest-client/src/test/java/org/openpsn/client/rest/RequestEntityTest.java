package org.openpsn.client.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class RequestEntityTest {
    private URI expectedUri;

    @BeforeEach
    public void setUp() {
        expectedUri = URI.create("http://test.com");
    }

    @Test
    void get_should_createEntity() {
        final var entity = RequestEntity.get(expectedUri);
        assertThat(entity.getUri()).isEqualTo(expectedUri);
        assertThat(entity.getMethod()).isEqualTo(Method.GET);
        assertThat(entity.getPayload()).isNull();
    }

    @Test
    void patch_should_createEntity() {
        final var entity = RequestEntity.patch(expectedUri, "test");
        assertThat(entity.getUri()).isEqualTo(expectedUri);
        assertThat(entity.getMethod()).isEqualTo(Method.PATCH);
        assertThat(entity.getPayload()).isEqualTo("test");
    }

    @Test
    void post_should_createEntity() {
        final var entity = RequestEntity.post(expectedUri, "test");
        assertThat(entity.getUri()).isEqualTo(expectedUri);
        assertThat(entity.getMethod()).isEqualTo(Method.POST);
        assertThat(entity.getPayload()).isEqualTo("test");
    }

    @Test
    void put_should_createEntity() {
        final var entity = RequestEntity.put(expectedUri, "test");
        assertThat(entity.getUri()).isEqualTo(expectedUri);
        assertThat(entity.getMethod()).isEqualTo(Method.PUT);
        assertThat(entity.getPayload()).isEqualTo("test");
    }

    @Test
    void headers_should_enableHeaderModifications() {
        final var entity = RequestEntity.get(expectedUri)
            .headers(headers -> headers.contentType(ContentType.APPLICATION_JSON));

        assertThat(entity.getHeaders().entrySet())
            .contains(entry(Headers.CONTENT_TYPE, List.of(ContentType.APPLICATION_JSON.getValue())));
    }
}
