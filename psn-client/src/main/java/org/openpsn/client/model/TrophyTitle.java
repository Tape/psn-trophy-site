package org.openpsn.client.model;

import lombok.NonNull;

import java.time.ZonedDateTime;

/**
 * Describes a title (game) with respect to the user that they were queried for. Each game may have several titles based
 * on region or platform.
 *
 * @param npServiceName       is the service name, generally trophy or trophy2.
 * @param npCommunicationId   is the communication id for the title, which effectively acts as its unique id.
 * @param trophySetVersion    is the version of the trophy set (ex 1.50).
 * @param trophyTitleName     is the display name of the title.
 * @param trophyTitleDetail   is the detailed description for the title.
 * @param trophyTitleIconUrl  is a url that points to the icon for the title.
 * @param trophyTitlePlatform is the platform the title is playable on.
 * @param hasTrophyGroups     is a flag that is true if the title has trophy groups enabled.
 * @param definedTrophies     is a breakdown of the trophies available for the title by trophy type.
 * @param progress            is the trophy progress completed by the player thus far.
 * @param earnedTrophies      is a breakdown of the trophies earned by the user for the title by trophy type.
 * @param hiddenFlag          is a flag that is true if the user has opted to hide it from their profile.
 * @param lastUpdatedDateTime is the last time the user has performed an action on this title.
 */
public record TrophyTitle(
    @NonNull String npServiceName,
    @NonNull String npCommunicationId,
    @NonNull String trophySetVersion,
    @NonNull String trophyTitleName,
    String trophyTitleDetail,
    @NonNull String trophyTitleIconUrl,
    @NonNull TitlePlatform trophyTitlePlatform,
    boolean hasTrophyGroups,
    @NonNull TrophyCounts definedTrophies,
    int progress,
    @NonNull TrophyCounts earnedTrophies,
    boolean hiddenFlag,
    @NonNull ZonedDateTime lastUpdatedDateTime
) {
}
