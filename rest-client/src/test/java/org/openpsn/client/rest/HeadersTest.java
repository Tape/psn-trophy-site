package org.openpsn.client.rest;

import org.junit.jupiter.api.Test;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class HeadersTest {

    @Test
    void of_should_useUnderlyingMap() {
        final Map<String, List<String>> headers = new HashMap<>();
        headers.put("test", List.of("value1", "value2"));
        final var httpHeaders = HttpHeaders.of(headers, (s, s2) -> true);

        assertThat(Headers.of(httpHeaders).get("test"))
            .containsExactly("value1", "value2");
    }

    @Test
    void add_should_addVarargsHeaderValues() {
        final var headers = new Headers()
            .add("test", "value1", "value2");

        assertThat(headers.get("test"))
            .containsExactly("value1", "value2");
    }

    @Test
    void add_should_addCollectionHeaderValues() {
        final var headers = new Headers()
            .add("test", List.of("value1", "value2"));

        assertThat(headers.get("test"))
            .containsExactly("value1", "value2");
    }

    @Test
    void basicAuth_should_addAuthorizationHeader() {
        final var headers = new Headers()
            .basicAuth("test", "test");

        assertThat(headers.get("Authorization"))
            .containsExactly("Basic dGVzdDp0ZXN0");
    }

    @Test
    void contentType_should_setContentTypeHeader() {
        final var headers = new Headers()
            .contentType(ContentType.APPLICATION_JSON);

        assertThat(headers.get(Headers.CONTENT_TYPE))
            .containsExactly(ContentType.APPLICATION_JSON.getValue());
    }

    @Test
    void contentType_should_getContentTypeHeader() {
        final var headers = new Headers()
            .contentType(ContentType.APPLICATION_JSON);

        assertThat(headers.contentType())
            .contains(ContentType.APPLICATION_JSON.getValue());
    }

    @Test
    void entrySet_should_returnHeaderEntries() {
        final var headers = new Headers()
            .add("test", List.of("value1", "value2"));

        assertThat(headers.entrySet())
            .containsExactly(entry("test", List.of("value1", "value2")));
    }

    @Test
    void get_should_returnAllValues() {
        final var headers = new Headers()
            .add("test", List.of("value1", "value2"));

        assertThat(headers.get("test"))
            .containsExactly("value1", "value2");
    }

    @Test
    void getFirst_should_returnFirstValue() {
        final var headers = new Headers()
            .add("test", List.of("value1", "value2"));

        assertThat(headers.getFirst("test"))
            .contains("value1");
    }

    @Test
    void set_should_replaceExistingValuesUsingVarargs() {
        final var headers = new Headers()
            .add("test", "value1")
            .set("test", "value2", "value3");

        assertThat(headers.get("test"))
            .containsExactly("value2", "value3");
    }

    @Test
    void set_should_replaceExistingValuesUsingCollection() {
        final var headers = new Headers()
            .add("test", List.of("value1"))
            .set("test", List.of("value2", "value3"));

        assertThat(headers.get("test"))
            .containsExactly("value2", "value3");
    }
}
