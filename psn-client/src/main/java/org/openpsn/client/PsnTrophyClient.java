package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.response.TrophyTitleResponse;
import org.openpsn.client.rest.RestClient;

import java.util.concurrent.CompletableFuture;

public class PsnTrophyClient extends AbstractApiClient {
    PsnTrophyClient(@NonNull RestClient restClient, @NonNull String accessToken) {
        super(restClient, accessToken);
    }

    public CompletableFuture<TrophyTitleResponse> getTrophyTitles() {
        return getTrophyTitles("me");
    }

    public CompletableFuture<TrophyTitleResponse> getTrophyTitles(@NonNull String userId) {
        return get(String.format("/users/%s/trophyTitles", userId), TrophyTitleResponse.class);
    }

    @Override
    protected String urlBase() {
        return System.getProperty(
            "openpsn.client.trophyUrlBase",
            "https://m.np.playstation.com/api/trophy/v1");
    }
}
