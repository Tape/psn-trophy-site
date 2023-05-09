package org.openpsn.client.rest.exception;

import lombok.NonNull;

public class ContentTypeException extends UnsupportedOperationException {
    public ContentTypeException(@NonNull String contentType) {
        super("Unexpected content type: " + contentType);
    }
}
