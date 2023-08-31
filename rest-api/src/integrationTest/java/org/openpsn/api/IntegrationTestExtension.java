package org.openpsn.api;

import io.jooby.ExecutionMode;
import io.jooby.Jooby;
import io.jooby.Server;
import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.*;
import org.testcontainers.containers.PostgreSQLContainer;

public class IntegrationTestExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
        new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("openpsn")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        POSTGRESQL_CONTAINER.start();

        System.setProperty("db.url", POSTGRESQL_CONTAINER.getJdbcUrl());
        System.setProperty("jooby.useShutdownHook", "false");
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        final var application = createApplication(extensionContext);
        final var server = application.start();

        final var store = getStore(extensionContext);
        store.put("application", application);
        store.put("server", server);

        RestAssured.baseURI = "http://localhost:" + server.getOptions().getPort();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        final var server = getStore(extensionContext).get("server", Server.class);
        if (server != null) {
            server.stop();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return null;
    }

    private Jooby createApplication(ExtensionContext extensionContext) {
        return Jooby.createApp(
            new String[]{"test"},
            ExecutionMode.DEFAULT,
            ApiApplication::new);
    }

    private ExtensionContext.Store getStore(ExtensionContext extensionContext) {
        final var namespace = ExtensionContext.Namespace.create(extensionContext.getRequiredTestClass());
        return extensionContext.getStore(namespace);
    }
}
