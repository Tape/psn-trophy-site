package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.response.TitleTrophiesResponse;
import org.openpsn.client.response.TrophyTitlesResponse;
import org.openpsn.client.response.UserTitleTrophiesResponse;
import org.openpsn.client.rest.RestClient;

import java.util.concurrent.CompletableFuture;

public class PsnTrophyClient extends AbstractApiClient {
    PsnTrophyClient(@NonNull RestClient restClient, @NonNull String accessToken) {
        super(restClient, accessToken);
    }

    public CompletableFuture<TrophyTitlesResponse> getTrophyTitles() {
        return getTrophyTitles("me");
    }

    public CompletableFuture<TrophyTitlesResponse> getTrophyTitles(@NonNull String userId) {
        return get(String.format("/users/%s/trophyTitles", userId), TrophyTitlesResponse.class);
    }

    public CompletableFuture<TitleTrophiesResponse> getTitleTrophies(@NonNull String npCommunicationId) {
        return getTitleTrophies(npCommunicationId, "all");
    }

    public CompletableFuture<TitleTrophiesResponse> getTitleTrophies(
        @NonNull String npCommunicationId,
        @NonNull String trophyGroupId
    ) {
        final String path = String.format(
            "/npCommunicationIds/%s/trophyGroups/%s/trophies",
            npCommunicationId,
            trophyGroupId);

        return get(path, TitleTrophiesResponse.class);
    }

    public CompletableFuture<UserTitleTrophiesResponse> getUserTitleTrophies(@NonNull String npCommunicationId) {
        return getUserTitleTrophies(npCommunicationId, "all");
    }

    public CompletableFuture<UserTitleTrophiesResponse> getUserTitleTrophies(
        @NonNull String npCommunicationId,
        @NonNull String trophyGroupId
    ) {
        return getUserTitleTrophies("me", npCommunicationId, trophyGroupId);
    }

    public CompletableFuture<UserTitleTrophiesResponse> getUserTitleTrophies(
        @NonNull String userId,
        @NonNull String npCommunicationId,
        @NonNull String trophyGroupId
    ) {
        final String path = String.format(
            "/users/%s/npCommunicationIds/%s/trophyGroups/%s/trophies",
            userId,
            npCommunicationId,
            trophyGroupId);

        return get(path, UserTitleTrophiesResponse.class);
    }

    @Override
    protected String urlBase() {
        return System.getProperty(
            "openpsn.client.trophyUrlBase",
            "https://m.np.playstation.com/api/trophy/v1");
    }
}
