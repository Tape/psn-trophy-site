package org.openpsn.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Console platform for the title. A title can be on several platforms, so in cases like that the game is actually
 * replicated across each of the platforms it has been ported to.
 */
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
