package org.openpsn.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public record TokenResponse(
    @JsonProperty("access_token")
    @NonNull String accessToken,
    @JsonProperty("token_type")
    @NonNull String tokenType,
    @JsonProperty("expires_in")
    int expiresIn,
    @NonNull String scope,
    @JsonProperty("id_token")
    @NonNull String idToken,
    @JsonProperty("refresh_token")
    @NonNull String refreshToken,
    @JsonProperty("refresh_token_expires_in")
    int refreshTokenExpiresIn
) {
}
