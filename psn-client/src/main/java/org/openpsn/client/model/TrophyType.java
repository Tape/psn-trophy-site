package org.openpsn.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes the various types of trophies available.
 */
public enum TrophyType {
    @JsonProperty("bronze")
    BRONZE,
    @JsonProperty("silver")
    SILVER,
    @JsonProperty("gold")
    GOLD,
    @JsonProperty("platinum")
    PLATINUM,
}
