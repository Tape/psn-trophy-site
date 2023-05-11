package org.openpsn.client.model;

import lombok.NonNull;

import java.time.ZonedDateTime;

public record UserTrophy(
    int trophyId,
    boolean trophyHidden,
    boolean earned,
    String progress,
    Integer progressRate,
    ZonedDateTime progressedDateTime,
    ZonedDateTime earnedDateTime,
    @NonNull String trophyType,
    int trophyRare,
    @NonNull String trophyEarnedRate
) {
}
