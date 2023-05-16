package org.openpsn.client.rest;

import java.util.Optional;

public interface Response {
    /**
     * Fetch the content type of the response body, which is empty if not specified.
     */
    Optional<String> contentType();

    /**
     * The response headers.
     */
    Headers headers();

    /**
     * Deserializes the body into a type as long as the content type is supported (currently only JSON).
     *
     * @param type is the type the body should be deserialized to.
     */
    <T> T readObject(Class<T> type);

    /**
     * The status code from the response.
     */
    StatusCode statusCode();
}
