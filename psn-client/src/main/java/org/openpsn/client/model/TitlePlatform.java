package org.openpsn.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TitlePlatform {
    @JsonProperty("PS3")
    PS3,
    @JsonProperty("PS4")
    PS4,
    @JsonProperty("PS5")
    PS5,
    @JsonProperty("Vita")
    VITA,
}
