package org.openpsn.client.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentTypeTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "application/json",
        "application/json; charset=utf-8",
    })
    void matches_should_returnTrueWhenSimilar(String contentType) {
        assertTrue(ContentType.APPLICATION_JSON.matches(contentType),
            contentType + "should match APPLICATION_JSON");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "application/jsonp",
        "application/jsonp; charset=utf-8",
        "text/html"
    })
    void matches_should_returnFalseWhenNotSimilar(String contentType) {
        assertFalse(ContentType.APPLICATION_JSON.matches(contentType),
            contentType + " should not match APPLICATION_JSON");
    }
}
