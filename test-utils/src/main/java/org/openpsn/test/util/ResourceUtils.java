package org.openpsn.test.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceUtils {
    /**
     * Fetch the raw bytes of a resource file.
     *
     * @param path is the path the resource is located at.
     */
    public static byte[] getResourceBytes(@NonNull String path) throws IOException {
        try (final var stream = getResourceStream(path).orElseThrow()) {
            return stream.readAllBytes();
        }
    }

    /**
     * Fetch an InputStream for the given resource path.
     *
     * @param path is an Optional containing the InputStream, empty if not found.
     */
    public static Optional<InputStream> getResourceStream(@NonNull String path) {
        final var stream = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(path);

        return Optional.ofNullable(stream);
    }
}
