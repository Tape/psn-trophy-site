package org.openpsn.client.rest.util;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openpsn.client.rest.util.EncodingUtils.*;

class EncodingUtilsTest {
    @Test
    void urlDecode_should_decodeToString() {
        assertThat(urlDecode("some+value")).isEqualTo("some value");
    }

    @Test
    void urlDecodeMap_should_decodeToMultiMap() {
        assertThat(EncodingUtils.urlDecodeMap("test=some+value&test=some+value+2"))
            .containsEntry("test", List.of("some value", "some value 2"));
    }

    @Test
    void urlEncode_should_encodeToString() {
        assertThat(urlEncode("some value")).isEqualTo("some+value");
    }

    @Test
    void urlEncodeMap_should_encodeToString() {
        // Use LHM to guarantee insertion order for encoding
        final var map = new LinkedHashMap<String, String>() {{
            put("test", "test");
            put("test 2", "test 2");
        }};

        assertThat(urlEncodeMap(map))
            .isEqualTo("test=test&test+2=test+2");
    }

    @Test
    void urlEncodeMultiMap_should_encodeToString() {
        // Use LHM to guarantee insertion order for encoding
        final var map = new LinkedHashMap<String, List<String>>() {{
            put("test", List.of("test"));
            put("test 2", List.of("test 2", "test 3"));
        }};

        assertThat(urlEncodeMultiMap(map))
            .isEqualTo("test=test&test+2=test+2&test+2=test+3");
    }
}
