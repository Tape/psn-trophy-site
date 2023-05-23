package org.openpsn.client.rest.util;

import lombok.NonNull;

import java.util.*;

public class MultiValueMap<K, V> extends TreeMap<K, List<V>> {
    public static <V> MultiValueMap<String, V> caseInsensitive() {
        return new MultiValueMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Creates a new map.
     */
    public MultiValueMap() {
        super();
    }

    /**
     * Creates a map with a provided key comparator.
     */
    public MultiValueMap(@NonNull Comparator<K> comparator) {
        super(comparator);
    }

    /**
     * Add values using the varargs syntax.
     *
     * @param key    is the key to insert at.
     * @param values is the values that should be set.
     */
    @SafeVarargs
    public final MultiValueMap<K, V> add(@NonNull K key, V... values) {
        return add(key, Arrays.asList(values));
    }

    /**
     * Add values using the collection syntax.
     *
     * @param key    is the key to insert at.
     * @param values is the values that should be set.
     */
    public MultiValueMap<K, V> add(@NonNull K key, Collection<V> values) {
        computeIfAbsent(key, k -> new ArrayList<>()).addAll(values);
        return this;
    }

    /**
     * Fetch the first value for a given key.
     *
     * @param key is the key to fetch from.
     */
    public Optional<V> getFirst(@NonNull K key) {
        return getOrDefault(key, Collections.emptyList()).stream().findFirst();
    }

    /**
     * Sets the values using the varargs syntax. The previously set values are overwritten.
     *
     * @param key    is the key to insert at.
     * @param values is the values that should be set.
     */
    @SafeVarargs
    public final MultiValueMap<K, V> set(@NonNull K key, V... values) {
        return set(key, Arrays.asList(values));
    }

    /**
     * Sets the values using the collection syntax. The previously set values are overwritten.
     *
     * @param key    is the key to insert at.
     * @param values is the values that should be set.
     */
    public MultiValueMap<K, V> set(@NonNull K key, Collection<V> values) {
        put(key, new ArrayList<>(values));
        return this;
    }
}
