package org.openpsn.client.response;

import lombok.NonNull;
import org.openpsn.client.model.UserTrophy;

import java.time.ZonedDateTime;
import java.util.List;

public record UserTitleTrophiesResponse(
    @NonNull String trophySetVersion,
    boolean hasTrophyGroups,
    @NonNull ZonedDateTime lastUpdatedDateTime,
    @NonNull List<UserTrophy> trophies,
    @NonNull List<UserTrophy> rarestTrophies,
    int totalItemCount,
    Integer nextOffset,
    Integer previousOffset
) {
}
