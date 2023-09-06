package org.openpsn.api.module;

import dagger.Binds;
import dagger.Module;
import org.openpsn.api.dao.UserDao;
import org.openpsn.api.dao.jdbc.UserDaoJdbc;

@Module
public abstract class DataAccessModule {
    @Binds
    abstract UserDao userDao(UserDaoJdbc userDaoJdbc);
}
