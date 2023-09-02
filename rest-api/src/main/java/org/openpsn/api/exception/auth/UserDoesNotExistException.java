package org.openpsn.api.exception.auth;

import io.jooby.StatusCode;
import io.jooby.exception.StatusCodeException;

public class UserDoesNotExistException extends StatusCodeException {
    public UserDoesNotExistException(String username) {
        super(StatusCode.NOT_FOUND, String.format("User %s does not exist", username));
    }
}
