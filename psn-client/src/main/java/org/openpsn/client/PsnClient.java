package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RestClient;

public class PsnClient {
    private final RestClient restClient;

    public PsnClient(@NonNull RestClient restClient) {
        this.restClient = restClient;
    }

    public PsnTrophyClient trophies() {
        // TODO: Figure out how to handle the auth headers
        return new PsnTrophyClient(restClient, null);
    }
}
