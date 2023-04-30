package org.openpsn.client.rest.exception;

public class StatusCodeException extends RuntimeException {
    public StatusCodeException(int statusCode) {
        super("Unexpected status code: " + statusCode);
    }
}
