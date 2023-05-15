package org.openpsn.client.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    /**
     * Type for the POST equivalent of GET query parameters in the body.
     */
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    /**
     * Type for a regular JSON encoded body.
     */
    APPLICATION_JSON("application/json");

    private final String value;

    /**
     * Utility function that can be used to determine if a content type matches the enum content type.
     *
     * @param contentType is the content type to test.
     */
    public boolean matches(String contentType) {
        if (contentType == null || contentType.isBlank()) {
            return false;
        }

        // Trim the encoding if provided
        final var semicolonIndex = contentType.indexOf(';');
        final var contentTypeBase = semicolonIndex == -1
            ? contentType
            : contentType.substring(0, semicolonIndex).trim();

        return contentTypeBase.equals(value);
    }
}
