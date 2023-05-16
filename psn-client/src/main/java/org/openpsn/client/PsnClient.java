package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RestClient;

public class PsnClient {
    private final RestClient restClient;

    public PsnClient(@NonNull RestClient restClient) {
        this.restClient = restClient;
    }

    public PsnAuthClient auth() {
        return new PsnAuthClient(restClient);
    }

    public PsnTrophyClient trophies(@NonNull String accessToken) {
        return new PsnTrophyClient(restClient, accessToken);
    }
}
