package org.openpsn.client.model;

import lombok.NonNull;

/**
 * Detailed information about a trophy as it relates to an individual title.
 *
 * @param trophyId                  is the unique id of the trophy.
 * @param trophyHidden              is the flag indicating if the trophy is a "secret" trophy.
 * @param trophyType                is the type of trophy.
 * @param trophyName                is the display name of the trophy.
 * @param trophyDetail              is the detailed description of the trophy.
 * @param trophyIconUrl             is a url for the icon.
 * @param trophyGroupId             is the group the trophy belongs to.
 * @param trophyProgressTargetValue is a PS5 only indicator of how much progress needs to be made to unlock the trophy.
 * @param trophyRewardName          is a PS5 only field stating the type of reward for unlocking the trophy.
 * @param trophyRewardImageUrl      is a PS5 only field with url to an image of the reward.
 */
public record Trophy(
    int trophyId,
    boolean trophyHidden,
    @NonNull TrophyType trophyType,
    @NonNull String trophyName,
    @NonNull String trophyDetail,
    @NonNull String trophyIconUrl,
    @NonNull String trophyGroupId,
    String trophyProgressTargetValue,
    String trophyRewardName,
    String trophyRewardImageUrl
) {
}
