package org.openpsn.client.model;

/**
 * Contains the trophy counts for each trophy type.
 *
 * @param bronze   is the number of bronze level trophies.
 * @param silver   is the number of silver level trophies.
 * @param gold     is the number of gold level trophies.
 * @param platinum is the number of platinum level trophies.
 */
public record TrophyCounts(
    int bronze,
    int silver,
    int gold,
    int platinum
) {
    /**
     * Utility function to fetch the value for a given trophy type.
     */
    public int forTrophyType(TrophyType type) {
        return switch (type) {
            case BRONZE -> bronze;
            case SILVER -> silver;
            case GOLD -> gold;
            case PLATINUM -> platinum;
        };
    }
}

