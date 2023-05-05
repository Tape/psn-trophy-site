package org.openpsn.client.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ContentTypeTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "application/json",
        "application/json; charset=utf-8",
    })
    void matches_should_returnTrueWhenSimilar(String contentType) {
        Assertions.assertThat(ContentType.APPLICATION_JSON.matches(contentType)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "application/jsonp",
        "application/jsonp; charset=utf-8",
        "text/html"
    })
    void matches_should_returnFalseWhenNotSimilar(String contentType) {
        Assertions.assertThat(ContentType.APPLICATION_JSON.matches(contentType)).isFalse();
    }
}
