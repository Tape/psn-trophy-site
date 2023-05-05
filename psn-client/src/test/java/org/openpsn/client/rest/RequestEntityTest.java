package org.openpsn.client.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestEntityTest {
    private URI expectedUri;

    @BeforeEach
    public void setUp() {
        expectedUri = URI.create("http://test.com");
    }

    @Test
    void get_should_createEntity() {
        final var entity = RequestEntity.get(expectedUri);
        assertEquals(expectedUri, entity.getUri());
        assertEquals(Method.GET, entity.getMethod());
        assertNull(entity.getPayload());
    }

    @Test
    void patch_should_createEntity() {
        final var entity = RequestEntity.patch(expectedUri, "test");
        assertEquals(expectedUri, entity.getUri());
        assertEquals(Method.PATCH, entity.getMethod());
        assertEquals("test", entity.getPayload());
    }

    @Test
    void post_should_createEntity() {
        final var entity = RequestEntity.post(expectedUri, "test");
        assertEquals(expectedUri, entity.getUri());
        assertEquals(Method.POST, entity.getMethod());
        assertEquals("test", entity.getPayload());
    }

    @Test
    void put_should_createEntity() {
        final var entity = RequestEntity.put(expectedUri, "test");
        assertEquals(expectedUri, entity.getUri());
        assertEquals(Method.PUT, entity.getMethod());
        assertEquals("test", entity.getPayload());
    }

    @Test
    void header_should_addHeader() {
        final var entity = RequestEntity.get(expectedUri)
            .header("Content-Type", "application/json");

        assertEquals(
            Map.of("Content-Type",
                List.of("application/json")),
            entity.getHeaders()
        );
    }

    @Test
    void header_should_addMultipleOfSameHeader() {
        final var entity = RequestEntity.get(expectedUri)
            .header("X-Test", "value 1")
            .header("X-Test", "value 2");

        assertEquals(
            Map.of("X-Test",
                List.of("value 1", "value 2")),
            entity.getHeaders()
        );
    }
}
