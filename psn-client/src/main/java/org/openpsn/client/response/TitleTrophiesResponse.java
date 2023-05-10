package org.openpsn.client.response;

import lombok.NonNull;
import org.openpsn.client.model.Trophy;

import java.util.List;

public record TitleTrophiesResponse(
    @NonNull String trophySetVersion,
    boolean hasTrophyGroups,
    @NonNull List<Trophy> trophies,
    int totalItemCount,
    Integer nextOffset,
    Integer previousOffset
) {
}
