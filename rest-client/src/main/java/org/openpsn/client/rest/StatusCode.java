package org.openpsn.client.rest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openpsn.client.rest.exception.StatusCodeException;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusCode {
    private final int code;

    /**
     * Creates an instance of a StatusCode from a raw status code value.
     *
     * @param code is the HTTP status code.
     * @throws StatusCodeException if the code is not 3 digits
     */
    public static StatusCode of(int code) {
        if (code < 100 || code > 999) {
            throw new StatusCodeException(code);
        }
        return new StatusCode(code);
    }

    /**
     * Utility function that can be used to determine if the status code represents a successful request.
     */
    public boolean is2xxSuccessful() {
        return code >= 200 && code < 300;
    }
}
