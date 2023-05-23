package org.openpsn.client.rest.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MultiValueMapTest {

    @Test
    void caseInsensitive_should_createMap() {
        final var map = MultiValueMap.<String>caseInsensitive();
        map.put("test", List.of("test1"));

        assertThat(map).containsKey("TEST");
    }

    @Test
    void add_should_addVarargsValues() {
        final var map = new MultiValueMap<String, String>()
            .add("test", "test1", "test2")
            .add("test", "test3");

        assertThat(map).containsEntry("test", List.of("test1", "test2", "test3"));
    }

    @Test
    void add_should_addCollectionOfValues() {
        final var map = new MultiValueMap<String, String>()
            .add("test", List.of("test1", "test2"))
            .add("test", List.of("test3"));

        assertThat(map).containsEntry("test", List.of("test1", "test2", "test3"));
    }

    @Test
    void getFirst_should_returnFirstValue() {
        final var map = new MultiValueMap<String, String>();
        map.put("test", List.of("test1", "test2"));

        assertThat(map.getFirst("test")).contains("test1");
    }

    @Test
    void set_should_replaceExistingValueVarargs() {
        final var map = new MultiValueMap<String, String>();
        map.put("test", List.of("test1"));
        map.set("test", "test2", "test3");

        assertThat(map).containsEntry("test", List.of("test2", "test3"));
    }

    @Test
    void set_should_replaceExistingValueCollection() {
        final var map = new MultiValueMap<String, String>();
        map.put("test", List.of("test1"));
        map.set("test", List.of("test2", "test3"));

        assertThat(map).containsEntry("test", List.of("test2", "test3"));
    }
}
