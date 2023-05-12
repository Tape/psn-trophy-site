package org.openpsn.client.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TrophyCountsTest {
    @ParameterizedTest
    @MethodSource("forTrophyTypeArgs")
    public void forTrophyType_should_returnCorrectValuesForType(TrophyType type, int expected) {
        final var counts = new TrophyCounts(1, 2, 3, 4);
        assertThat(counts.forTrophyType(type)).isEqualTo(expected);
    }

    private static Stream<Arguments> forTrophyTypeArgs() {
        return Stream.of(
            Arguments.of(TrophyType.BRONZE, 1),
            Arguments.of(TrophyType.SILVER, 2),
            Arguments.of(TrophyType.GOLD, 3),
            Arguments.of(TrophyType.PLATINUM, 4));
    }
}
