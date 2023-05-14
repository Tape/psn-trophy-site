package org.openpsn.client.rest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestEntity<T> {
    private final URI uri;
    private final Method method;
    private final T payload;
    private final Map<String, List<String>> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);


    /**
     * Creates an entity that will perform a GET request with an empty body.
     *
     * @param uri is the URI to send the request to.
     */
    public static RequestEntity<Void> get(@NonNull URI uri) {
        return new RequestEntity<>(uri, Method.GET, null);
    }

    /**
     * Creates an entity that will perform a PATCH request with a given payload.
     *
     * @param uri     is the URI to send the request to.
     * @param payload is the payload to transmit, with null values being treated as an empty body.
     * @param <T>     is the type of the payload.
     */
    public static <T> RequestEntity<T> patch(@NonNull URI uri, T payload) {
        return new RequestEntity<>(uri, Method.PATCH, payload);
    }

    /**
     * Creates an entity that will perform a POST request with a given payload.
     *
     * @param uri     is the URI to send the request to.
     * @param payload is the payload to transmit, with null values being treated as an empty body.
     * @param <T>     is the type of the payload.
     */
    public static <T> RequestEntity<T> post(@NonNull URI uri, T payload) {
        return new RequestEntity<>(uri, Method.POST, payload);
    }

    /**
     * Creates an entity that will perform a PUT request with a given payload.
     *
     * @param uri     is the URI to send the request to.
     * @param payload is the payload to transmit, with null values being treated as an empty body.
     * @param <T>     is the type of the payload.
     */
    public static <T> RequestEntity<T> put(@NonNull URI uri, T payload) {
        return new RequestEntity<>(uri, Method.PUT, payload);
    }

    /**
     * Adds a new header to the entity. Repeats of the same header will be added as another value.
     *
     * @param name  is the name of the header, such as Content-Type.
     * @param value is the value of the header, such as application/json.
     */
    public RequestEntity<T> header(@NonNull String name, @NonNull String value) {
        headers.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
        return this;
    }

    /**
     * Utility method that sets the Content-Type header using a standard set of available content types.
     */
    public RequestEntity<T> contentType(@NonNull ContentType contentType) {
        return setHeader("Content-Type", contentType.getValue());
    }

    /**
     * Adds a new header to the entity. Replaces the existing header value if it exists. This will also lock down the
     * header and make it immutable so that any future calls to append new values will fail.
     *
     * @param name  is the name of the header, such as Content-Type.
     * @param value is the value of the header, such as application/json.
     */
    public RequestEntity<T> setHeader(@NonNull String name, @NonNull String value) {
        headers.put(name, List.of(value));
        return this;
    }
}
