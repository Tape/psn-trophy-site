package org.openpsn.client.response;

import lombok.NonNull;
import org.openpsn.client.model.TrophyTitle;

import java.util.List;

public record TrophyTitleResponse(
    @NonNull List<TrophyTitle> trophyTitles,
    int totalItemCount,
    int nextOffset,
    int previousOffset
) {
}
