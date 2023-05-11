package org.openpsn.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
