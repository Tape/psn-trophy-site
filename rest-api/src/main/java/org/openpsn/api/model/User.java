package org.openpsn.api.model;

import java.time.LocalDateTime;

public record User(
    String id,
    String psnId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
