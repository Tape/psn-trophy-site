package org.openpsn.client.response;

import lombok.NonNull;
import org.openpsn.client.model.Trophy;

import java.util.List;

/**
 * Response from getting trophy information for a title.
 *
 * @param trophySetVersion is the version of the trophy set (ex 1.50).
 * @param hasTrophyGroups  is a flag that is true if the title has trophy groups enabled.
 * @param trophies         is a list of trophies the title has.
 * @param totalItemCount   is for pagination, returning the total number of items.
 * @param nextOffset       is for pagination, which is the next offset that should be used.
 * @param previousOffset   is for pagination, which is the offset for the previous page.
 */
public record TitleTrophiesResponse(
    @NonNull String trophySetVersion,
    boolean hasTrophyGroups,
    @NonNull List<Trophy> trophies,
    int totalItemCount,
    Integer nextOffset,
    Integer previousOffset
) {
}
