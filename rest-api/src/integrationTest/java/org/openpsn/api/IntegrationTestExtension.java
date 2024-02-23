package org.openpsn.api;

import io.jooby.ExecutionMode;
import io.jooby.Jooby;
import io.jooby.Server;
import io.jooby.SneakyThrows;
import io.restassured.RestAssured;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.extension.*;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

public class IntegrationTestExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
        new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("openpsn")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        POSTGRESQL_CONTAINER.start();

        System.setProperty("db.url", POSTGRESQL_CONTAINER.getJdbcUrl());
        System.setProperty("db.user", POSTGRESQL_CONTAINER.getUsername());
        System.setProperty("db.password", POSTGRESQL_CONTAINER.getPassword());

        System.setProperty("jooby.useShutdownHook", "false");
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        final var application = createApplication(extensionContext);
        final var server = application.start();

        final var store = getStore(extensionContext);
        store.put("application", application);
        store.put("server", server);

        RestAssured.baseURI = String.format("http://localhost:%d", server.getOptions().getPort());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
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
        return parameterContext.getParameter().getType() == QueryRunner.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final var application = getStore(extensionContext).get("application", Jooby.class);
        return new QueryRunner(application.require(DataSource.class));
    }

    private Jooby createApplication(ExtensionContext extensionContext) {
        final var annotation = extensionContext.getRequiredTestClass().getAnnotation(IntegrationTest.class);

        return Jooby.createApp(
            new String[]{annotation.environment()},
            ExecutionMode.DEFAULT,
            SneakyThrows.throwingSupplier(() -> annotation.value().getConstructor().newInstance()));
    }

    private ExtensionContext.Store getStore(ExtensionContext extensionContext) {
        final var namespace = ExtensionContext.Namespace.create(extensionContext.getRequiredTestClass());
        return extensionContext.getStore(namespace);
    }
}
