package org.openpsn.client.model;

import lombok.NonNull;

import java.time.ZonedDateTime;

/**
 * Describes a trophy from the perspective of a user.
 *
 * @param trophyId           is the unique id of the trophy.
 * @param trophyHidden       is the flag indicating if the trophy is a "secret" trophy.
 * @param earned             is true if the user has earned the trophy.
 * @param progress           is a PS5 feature which is how much completed progress the trophy is.
 * @param progressRate       is a PS5 feature which is the percentage complete the trophy is.
 * @param progressedDateTime is a PS5 feature which shows the last time progress was made.
 * @param earnedDateTime     is the date the trophy was earned.
 * @param trophyType         is the type of trophy.
 * @param trophyRare         is the rarity of the trophy.
 * @param trophyEarnedRate   is the percent of all PSN users that have earned the trophy.
 */
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
