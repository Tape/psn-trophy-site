package org.openpsn.client.model;

/**
 * Describes the rarity level of a trophy, determined by how frequently it has been earned by other PSN users.
 */
public enum TrophyRarity {
    // NOTE: These are deserialized by ordinal, so the order must not change
    ULTRA_RARE,
    VERY_RARE,
    RARE,
    COMMON,
}
