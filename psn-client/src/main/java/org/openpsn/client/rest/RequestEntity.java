package org.openpsn.client.rest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestEntity<T> {
    private final URI uri;
    private final Method method;
    private final T payload;
    // TODO: Case insensitive
    private final Map<String, List<String>> headers = new HashMap<>();

    public static RequestEntity<Void> get(URI uri) {
        return new RequestEntity<>(uri, Method.GET, null);
    }

    public static <T> RequestEntity<T> patch(URI uri, T payload) {
        return new RequestEntity<>(uri, Method.PATCH, payload);
    }

    public static <T> RequestEntity<T> post(URI uri, T payload) {
        return new RequestEntity<>(uri, Method.POST, payload);
    }

    public static <T> RequestEntity<T> put(URI uri, T payload) {
        return new RequestEntity<>(uri, Method.PUT, payload);
    }

    public RequestEntity<T> header(@NonNull String name, @NonNull String value) {
        headers.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
        return this;
    }
}
