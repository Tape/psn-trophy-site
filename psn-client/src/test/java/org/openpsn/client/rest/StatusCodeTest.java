package org.openpsn.client.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openpsn.client.rest.exception.StatusCodeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StatusCodeTest {
    @ParameterizedTest
    @ValueSource(ints = {99, 1000, -100})
    public void constructor_should_throwOnInvalidCodes(int code) {
        assertThatExceptionOfType(StatusCodeException.class)
            .isThrownBy(() -> StatusCode.of(code));
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 299})
    public void is2xxSuccessful_should_returnTrueIf2xx(int code) {
        assertThat(StatusCode.of(code).is2xxSuccessful()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 300})
    public void is2xxSuccessful_should_returnFalseIfNot2xx(int code) {
        assertThat(StatusCode.of(code).is2xxSuccessful()).isFalse();
    }
}
