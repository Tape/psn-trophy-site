package org.openpsn.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

/**
 * Response from calling the access token endpoint, which returns
 *
 * @param accessToken           is the access token which can be used for authenticated PSN requests.
 * @param tokenType             is the type of token, typically "bearer".
 * @param expiresIn             is the time in seconds until the access token expires, typically 1 hour.
 * @param scope                 is the scope that this request is authorized for.
 * @param idToken               is the id token which is a JWT containing various claims about the user.
 * @param refreshToken          is the refresh token that can be used to refresh the access token.
 * @param refreshTokenExpiresIn is the time in seconds until the refresh token expires, typically 60 days.
 */
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
