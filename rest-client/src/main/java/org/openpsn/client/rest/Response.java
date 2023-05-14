package org.openpsn.client.rest;

import java.net.http.HttpHeaders;
import java.util.Optional;

public interface Response {
    Optional<String> contentType();

    HttpHeaders headers();

    <T> T readObject(Class<T> type);

    StatusCode statusCode();
}
