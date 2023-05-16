package org.openpsn.client.rest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestEntity<T> {
    private final URI uri;
    private final Method method;
    private final T payload;
    private final Headers headers = new Headers();


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
     * allows the user to provide a consumer to manipulate the request entity headers.
     *
     * @param consumer is the consumer function.
     */
    public RequestEntity<T> headers(Consumer<Headers> consumer) {
        consumer.accept(headers);
        return this;
    }
}
