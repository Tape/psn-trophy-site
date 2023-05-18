package org.openpsn.client.rest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncodingUtils {
    /**
     * Url decodes a string value.
     */
    public static String urlDecode(@NonNull String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    /**
     * Url decodes a string into a map containing multiple values for each key.
     */
    public static Map<String, List<String>> urlDecodeMap(@NonNull String params) {
        return Stream.of(params.split("&"))
            .map(it -> it.split("="))
            .collect(Collectors.toMap(
                it -> urlDecode(it[0]),
                it -> {
                    final var values = new ArrayList<String>();
                    if (it.length > 1) {
                        values.add(urlDecode(it[1]));
                    }
                    return values;
                },
                (existingValues, newValues) -> {
                    existingValues.addAll(newValues);
                    return existingValues;
                }
            ));
    }

    /**
     * Url encodes a string value.
     */
    public static String urlEncode(@NonNull String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * Url encodes a map containing a single value for each key.
     */
    public static String urlEncodeMap(@NonNull Map<String, String> params) {
        return params.entrySet()
            .stream()
            .map(pair -> urlEncodePair(pair.getKey(), pair.getValue()))
            .collect(Collectors.joining("&"));
    }

    /**
     * Url encodes a map containing multiple values for each key.
     */
    public static String urlEncodeMultiMap(@NonNull Map<String, List<String>> params) {
        return params.entrySet()
            .stream()
            .flatMap(pair -> pair.getValue().stream()
                .map(value -> urlEncodePair(pair.getKey(), value)))
            .collect(Collectors.joining("&"));
    }

    private static String urlEncodePair(String key, String value) {
        return urlEncode(key) + "=" + urlEncode(value);
    }
}
