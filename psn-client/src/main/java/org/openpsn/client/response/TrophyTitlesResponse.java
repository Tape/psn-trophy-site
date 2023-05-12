package org.openpsn.client.response;

import lombok.NonNull;
import org.openpsn.client.model.TrophyTitle;

import java.util.List;

/**
 * Response from getting title information for a user.
 *
 * @param trophyTitles   is the titles the user has played.
 * @param totalItemCount is for pagination, returning the total number of items.
 * @param nextOffset     is for pagination, which is the next offset that should be used.
 * @param previousOffset is for pagination, which is the offset for the previous page.
 */
public record TrophyTitlesResponse(
    @NonNull List<TrophyTitle> trophyTitles,
    int totalItemCount,
    Integer nextOffset,
    Integer previousOffset
) {
}
