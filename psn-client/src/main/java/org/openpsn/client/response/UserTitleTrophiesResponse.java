package org.openpsn.client.response;

import lombok.NonNull;
import org.openpsn.client.model.UserTrophy;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Response from getting trophy details for a title as it relates to a user.
 *
 * @param trophySetVersion    is the version of the trophy set (ex 1.50).
 * @param hasTrophyGroups     is a flag that is true if the title has trophy groups enabled.
 * @param lastUpdatedDateTime is the last time the user has performed an action on this title.
 * @param trophies            is a list of trophies for the title with user details.
 * @param rarestTrophies      is a list of trophies for the title that are the rarest.
 * @param totalItemCount      is for pagination, returning the total number of items.
 * @param nextOffset          is for pagination, which is the next offset that should be used.
 * @param previousOffset      is for pagination, which is the offset for the previous page.
 */
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
