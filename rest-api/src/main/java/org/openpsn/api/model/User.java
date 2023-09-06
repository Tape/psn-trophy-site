package org.openpsn.api.model;

import java.time.LocalDateTime;

public record User(
    String id,
    String psnName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
