package org.openpsn.client.rest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContentType {
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
