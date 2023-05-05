package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RestClient;

abstract class AbstractApiClient {
    protected final RestClient restClient;

    protected AbstractApiClient(@NonNull RestClient restClient) {
        this.restClient = restClient;
    }

    protected abstract String urlBase();
}
