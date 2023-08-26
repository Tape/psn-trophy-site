package org.openpsn.api.module;

import dagger.Module;
import dagger.Provides;
import io.jooby.Jooby;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Module
@RequiredArgsConstructor
public class ApplicationModule {
    private final Jooby app;

    @Provides
    @Singleton
    public Jooby joobyApplication() {
        return app;
    }
}
