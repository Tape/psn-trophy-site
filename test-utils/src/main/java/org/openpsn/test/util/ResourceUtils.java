package org.openpsn.test.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceUtils {
    public static byte[] getResourceBytes(@NonNull String path) throws IOException {
        return getResourceStream(path).readAllBytes();
    }

    public static InputStream getResourceStream(@NonNull String path) {
        return Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(path);
    }
}
