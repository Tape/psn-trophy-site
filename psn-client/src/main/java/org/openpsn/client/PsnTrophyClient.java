package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.response.TitleTrophiesResponse;
import org.openpsn.client.response.TrophyTitlesResponse;
import org.openpsn.client.response.UserTitleTrophiesResponse;
import org.openpsn.client.rest.RestClient;

import java.util.concurrent.CompletableFuture;

public class PsnTrophyClient extends AbstractApiClient {
    /**
     * Creates a trophy client with a user context that can fetch trophy and title information.
     *
     * @param restClient  is the shared REST client.
     * @param accessToken is the access token for the user.
     */
    PsnTrophyClient(@NonNull RestClient restClient, @NonNull String accessToken) {
        super(restClient, accessToken);
    }

    /**
     * Fetch the trophy titles for the currently logged-in user. Functionally equivalent to getTrophyTitles("me").
     */
    public CompletableFuture<TrophyTitlesResponse> getTrophyTitles() {
        return getTrophyTitles("me");
    }

    /**
     * Fetch the trophy titles for any user id. If the user id provided is not the currently logged-in user titles that
     * have been masked via a privacy preference will not be returned.
     *
     * @param userId is the unique id for the user.
     */
    public CompletableFuture<TrophyTitlesResponse> getTrophyTitles(@NonNull String userId) {
        return get(String.format("/users/%s/trophyTitles", userId), TrophyTitlesResponse.class);
    }

    /**
     * Fetches the trophies for a title under all trophy groups. Functionally equivalent to
     * getTitleTrophies(npCommunicationId, "all").
     *
     * @param npCommunicationId is the unique id for the title.
     */
    public CompletableFuture<TitleTrophiesResponse> getTitleTrophies(@NonNull String npCommunicationId) {
        return getTitleTrophies(npCommunicationId, "all");
    }

    /**
     * Fetches the trophies for a title under a specific trophy group.
     *
     * @param npCommunicationId is the unique id for the title.
     * @param trophyGroupId     is the identifier for the trophy group.
     */
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

    /**
     * Fetches the trophies that the currently logged-in user has unlocked for a title under all trophy groups.
     * Functionally equivalent to getUserTitleTrophies("me", "all", npCommunicationId).
     *
     * @param npCommunicationId is the unique id for the title.
     */
    public CompletableFuture<UserTitleTrophiesResponse> getUserTitleTrophies(@NonNull String npCommunicationId) {
        return getUserTitleTrophies(npCommunicationId, "all");
    }

    /**
     * Fetches the trophies that the currently logged-in user has unlocked for a title under a specific trophy group.
     * Functionally equivalent to getUserTitleTrophies("me", trophyGroupId, npCommunicationId).
     *
     * @param npCommunicationId is the unique id for the title.
     * @param trophyGroupId     is the identifier for the trophy group.
     */
    public CompletableFuture<UserTitleTrophiesResponse> getUserTitleTrophies(
        @NonNull String npCommunicationId,
        @NonNull String trophyGroupId
    ) {
        return getUserTitleTrophies("me", npCommunicationId, trophyGroupId);
    }

    /**
     * Fetches the trophies that a user has unlocked for a title under a specific trophy group. If the user provided is
     * not the currently logged-in user trophies earned under a title that has been masked may not return.  Functionally
     * equivalent to getUserTitleTrophies("me", trophyGroupId, npCommunicationId).
     *
     * @param userId            is the unique id for the user.
     * @param npCommunicationId is the unique id for the title.
     * @param trophyGroupId     is the identifier for the trophy group.
     */
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
