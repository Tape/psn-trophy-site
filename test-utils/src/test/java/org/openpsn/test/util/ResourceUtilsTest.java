package org.openpsn.test.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.openpsn.test.util.ResourceUtils.*;

class ResourceUtilsTest {
    @Test
    void getResourceBytes_should_readFileBytes() throws IOException {
        assertThat(getResourceBytes("test.txt"))
            .contains("test\n".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void getResourceBytes_should_throwIfNoResource() {
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(() -> getResourceBytes("does-not-exist.txt"));
    }

    @Test
    void getResourceStream_should_returnStream() throws IOException {
        final var stream = getResourceStream("test.txt");
        assertThat(stream).isNotEmpty();
        stream.get().close();
    }

    @Test
    void getResourceStream_should_returnEmptyIfNoResource() {
        final var stream = getResourceStream("does-not-exist.txt");
        assertThat(stream).isEmpty();
    }

    @Test
    void getResourceString_should_readString() throws IOException {
        assertThat(getResourceString("test.txt"))
            .isEqualTo("test\n");
    }

    @Test
    void getResourceString_should_throwIfNoResource() {
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(() -> getResourceString("does-not-exist.txt"));
    }
}
