package org.openpsn.api.exception.auth;

import io.jooby.StatusCode;
import io.jooby.exception.StatusCodeException;

public class RegistrationFailedException extends StatusCodeException {
    public RegistrationFailedException() {
        super(StatusCode.BAD_REQUEST, "Registration failed");
    }
}
