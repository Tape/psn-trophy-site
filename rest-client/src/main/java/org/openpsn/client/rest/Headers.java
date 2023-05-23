package org.openpsn.client.rest;

import org.openpsn.client.rest.util.MultiValueMap;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class Headers extends MultiValueMap<String, String> {
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

    /**
     * Creates an immutable header object from a HttpHeaders instance.
     */
    public static Headers of(HttpHeaders other) {
        final var headers = new Headers();
        headers.putAll(other.map());
        return headers;
    }

    /**
     * Creates a new headers instance with no values populated.
     */
    public Headers() {
        super(String.CASE_INSENSITIVE_ORDER);
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
        set(AUTHORIZATION, "Basic " + encodedCredentials);
        return this;
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
        set(CONTENT_TYPE, contentType.getValue());
        return this;
    }
}
