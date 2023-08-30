package org.openpsn.api;

import io.jooby.test.MockRouter;
import org.junit.jupiter.api.extension.*;
import org.testcontainers.containers.PostgreSQLContainer;

public class IntegrationTestExtension implements BeforeAllCallback, ParameterResolver {
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
        new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("openpsn")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        POSTGRESQL_CONTAINER.start();

        System.setProperty("db.url", POSTGRESQL_CONTAINER.getJdbcUrl());
    }

    private MockRouter router;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        router = new MockRouter(new ApiApplication());
        router.setFullExecution(true);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return MockRouter.class.equals(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (MockRouter.class.equals(parameterContext.getParameter().getType())) {
            return router;
        }

        throw new ParameterResolutionException(String.format(
            "Parameter %s of type %s is not supported",
            parameterContext.getParameter().getName(),
            parameterContext.getParameter().getType()));
    }
}
