package org.openpsn.client.model;

import lombok.NonNull;

public record Trophy(
    int trophyId,
    boolean trophyHidden,
    @NonNull String trophyType,
    @NonNull String trophyName,
    @NonNull String trophyDetail,
    @NonNull String trophyIconUrl,
    @NonNull String trophyGroupId,
    String trophyProgressTargetValue,
    String trophyRewardName,
    String trophyRewardImageUrl
) {
}
