package org.openpsn.api.module;

import dagger.Module;
import dagger.Provides;
import io.jooby.Jooby;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbutils.QueryRunner;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.util.Objects;

@Module
@RequiredArgsConstructor
public class ApplicationModule {
    private final Jooby app;

    @Provides
    @Singleton
    public Jooby joobyApplication() {
        return app;
    }

    @Provides
    @Singleton
    public QueryRunner queryRunner() {
        final var dataSource = Objects.requireNonNull(app.require(DataSource.class));
        return new QueryRunner(dataSource);
    }
}
