package org.openpsn.api;

import io.jooby.Jooby;
import io.jooby.flyway.FlywayModule;
import io.jooby.hikari.HikariModule;

public class ApiApplication extends Jooby {
    public ApiApplication() {
        install(new HikariModule());
        install(new FlywayModule());
    }
}
