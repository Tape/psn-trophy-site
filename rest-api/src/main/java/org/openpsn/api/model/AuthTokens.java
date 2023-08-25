package org.openpsn.api.model;

public record AuthTokens(
    String accessToken,
    String refreshToken
) {
}
