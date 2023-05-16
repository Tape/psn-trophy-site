package org.openpsn.client;

import lombok.NonNull;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class AbstractApiClient {
    protected final String accessToken;
    protected final RestClient restClient;

    protected AbstractApiClient(@NonNull RestClient restClient) {
        this(restClient, null);
    }

    protected AbstractApiClient(@NonNull RestClient restClient, String accessToken) {
        this.accessToken = accessToken;
        this.restClient = restClient;
    }

    protected String urlDecode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    protected Map<String, String> urlDecodeMap(@NonNull String params) {
        return Stream.of(params.split("&"))
            .map(it -> it.split("="))
            .collect(Collectors.toMap(
                it -> urlDecode(it[0]),
                it -> it.length > 1 ? urlDecode(it[1]) : "true"));
    }

    protected String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    protected String urlEncodeMap(@NonNull Map<String, String> params) {
        return params.entrySet()
            .stream()
            .map(pair -> urlEncode(pair.getKey()) + "=" + urlEncode(pair.getValue()))
            .collect(Collectors.joining("&"));
    }

    protected <T> CompletableFuture<T> get(@NonNull String path, Class<T> responseType) {
        final var entity = RequestEntity.get(URI.create(urlBase() + path));
        if (accessToken != null) {
            entity.headers(headers -> headers.set("Authorization", "Bearer " + accessToken));
        }

        return restClient.requestObjectAsync(entity, responseType);
    }

    protected abstract String urlBase();
}
