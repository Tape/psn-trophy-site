package org.openpsn.api.exception.auth;

import io.jooby.StatusCode;
import io.jooby.exception.StatusCodeException;

public class BadCredentialsException extends StatusCodeException {
    public BadCredentialsException() {
        super(StatusCode.FORBIDDEN, "Provided credentials provided are invalid");
    }
}
