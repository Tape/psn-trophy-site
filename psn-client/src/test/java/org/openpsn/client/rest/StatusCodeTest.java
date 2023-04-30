package org.openpsn.client.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openpsn.client.rest.exception.StatusCodeException;

import static org.junit.jupiter.api.Assertions.*;

class StatusCodeTest {
    @ParameterizedTest
    @ValueSource(ints = {99, 1000, -100})
    public void constructor_should_throwOnInvalidCodes(int code) {
        assertThrows(StatusCodeException.class, () -> StatusCode.of(code),
            code + " is not a valid status code");
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 299})
    public void is2xxSuccessful_should_returnTrueIf2xx(int code) {
        assertTrue(StatusCode.of(code).is2xxSuccessful(),
            code + " is a successful status code");
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 300})
    public void is2xxSuccessful_should_returnFalseIfNot2xx(int code) {
        assertFalse(StatusCode.of(code).is2xxSuccessful(),
            code + " is not a successful status code");
    }
}
