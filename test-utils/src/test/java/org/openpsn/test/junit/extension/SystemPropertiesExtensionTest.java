package org.openpsn.test.junit.extension;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class SystemPropertiesExtensionTest {
    @Test
    public void extension_should_storeAndRestoreProperties() {
        final var properties = new Properties();
        properties.putAll(System.getProperties());

        final var extension = new SystemPropertiesExtension();
        extension.beforeEach(null);
        System.setProperty("test.value1", "value1");
        System.setProperty("test.value2", "value2");
        extension.afterEach(null);

        assertThat(System.getProperties()).isEqualTo(properties);
        assertThat(System.getProperty("test.value1")).isNull();
        assertThat(System.getProperty("test.value2")).isNull();
    }
}
