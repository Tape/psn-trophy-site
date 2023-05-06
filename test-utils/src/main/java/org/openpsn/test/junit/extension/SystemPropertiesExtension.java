package org.openpsn.test.junit.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Properties;

public class SystemPropertiesExtension implements BeforeEachCallback, AfterEachCallback {
    private final Properties originalProperties = new Properties();

    @Override
    public void beforeEach(ExtensionContext context) {
        originalProperties.putAll(System.getProperties());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        final var systemProperties = System.getProperties();
        systemProperties.clear();
        systemProperties.putAll(originalProperties);

        originalProperties.clear();
    }
}
