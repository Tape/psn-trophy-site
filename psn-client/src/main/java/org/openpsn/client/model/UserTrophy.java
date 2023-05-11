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
    @NonNull TrophyType trophyType,
    @NonNull TrophyRarity trophyRare,
    @NonNull String trophyEarnedRate
) {
}
