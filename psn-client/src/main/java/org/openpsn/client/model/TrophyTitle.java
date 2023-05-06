package org.openpsn.client.model;

import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.Optional;

public record TrophyTitle(
    @NonNull String npServiceName,
    @NonNull String npCommunicationId,
    @NonNull String trophySetVersion,
    @NonNull String trophyTitleName,
    @NonNull Optional<String> trophyTitleDetail,
    @NonNull String trophyTitleIconUrl,
    @NonNull String trophyTitlePlatform,
    boolean hasTrophyGroups,
    @NonNull TrophyCounts definedTrophies,
    int progress,
    @NonNull TrophyCounts earnedTrophies,
    boolean hiddenFlag,
    @NonNull ZonedDateTime lastUpdatedDateTime
) {
}
