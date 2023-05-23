package org.openpsn.client.rest;

import org.junit.jupiter.api.Test;
import org.openpsn.client.rest.util.MultiValueMap;

import java.net.http.HttpHeaders;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HeadersTest {
    @Test
    void of_should_useUnderlyingHttpHeadersMap() {
        final var headers = new MultiValueMap<String, String>()
            .add("test", List.of("value1", "value2"));
        final var httpHeaders = HttpHeaders.of(headers, (s, s2) -> true);

        assertThat(Headers.of(httpHeaders).get("test"))
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
}
