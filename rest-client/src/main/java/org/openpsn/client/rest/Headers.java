package org.openpsn.client.rest;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.*;

@EqualsAndHashCode
public class Headers {
    /**
     * Header name for authorization.
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * Header name for the content type of the payload.
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * Header name for the target location URL, typically used in redirects.
     */
    public static final String LOCATION = "Location";

    private final Map<String, List<String>> headers;

    /**
     * Creates an immutable header object from a HttpHeaders instance.
     */
    public static Headers of(HttpHeaders headers) {
        return new Headers(headers.map());
    }

    /**
     * Creates a new headers instance with no values populated.
     */
    public Headers() {
        this(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
    }

    private Headers(@NonNull Map<String, List<String>> headers) {
        this.headers = headers;
    }

    /**
     * Add headers using the varargs syntax.
     *
     * @param name   is the name of the header.
     * @param values is the values that should be set.
     */
    public Headers add(String name, String... values) {
        return add(name, Arrays.asList(values));
    }

    /**
     * Add headers using the collection syntax.
     *
     * @param name   is the name of the header.
     * @param values is the values that should be set.
     */
    public Headers add(String name, Collection<String> values) {
        headers.merge(name, new ArrayList<>(values), (existingValues, newValues) -> {
            existingValues.addAll(newValues);
            return existingValues;
        });

        return this;
    }

    /**
     * Add an Authorization: Basic header with the provided credentials.
     *
     * @param username is the username for the credentials.
     * @param password is the password for the credentials.
     */
    public Headers basicAuth(String username, String password) {
        final var credentials = (username + ":" + password).getBytes(StandardCharsets.UTF_8);
        final var encodedCredentials = Base64.getEncoder().encodeToString(credentials);
        return set(AUTHORIZATION, "Basic " + encodedCredentials);
    }

    /**
     * Fetch the first value of the Content-Type header.
     */
    public Optional<String> contentType() {
        return getFirst(CONTENT_TYPE);
    }

    /**
     * Set the Content-type header.
     *
     * @param contentType is a predefined content type value.
     */
    public Headers contentType(ContentType contentType) {
        return set(CONTENT_TYPE, contentType.getValue());
    }

    /**
     * Returns a set containing all the header name values entries.
     */
    public Set<Map.Entry<String, List<String>>> entrySet() {
        return headers.entrySet();
    }

    /**
     * Fetch an immutable list all the values for a header.
     *
     * @param name is the name of the header.
     */
    public List<String> get(String name) {
        final var values = headers.getOrDefault(name, Collections.emptyList());
        return Collections.unmodifiableList(values);
    }

    /**
     * Fetch the first value for a header.
     *
     * @param name is the name of the header.
     */
    public Optional<String> getFirst(String name) {
        return get(name).stream().findFirst();
    }

    /**
     * Sets the value of a header using the varargs syntax. The previously set values are overwritten.
     *
     * @param name   is the name of the header.
     * @param values is the values that should be set.
     */
    public Headers set(String name, String... values) {
        return set(name, Arrays.asList(values));
    }

    /**
     * Sets the value of a header using the collection syntax. The previously set values are overwritten.
     *
     * @param name   is the name of the header.
     * @param values is the values that should be set.
     */
    public Headers set(String name, Collection<String> values) {
        headers.remove(name);
        return add(name, values);
    }
}
