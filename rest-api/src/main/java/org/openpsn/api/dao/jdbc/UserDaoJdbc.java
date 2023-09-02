package org.openpsn.api.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.openpsn.api.dao.UserDao;
import org.openpsn.api.db.RecordHandler;
import org.openpsn.api.db.ValueHandler;
import org.openpsn.api.exception.auth.UserDoesNotExistException;
import org.openpsn.api.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class UserDaoJdbc implements UserDao {
    private final QueryRunner queryRunner;

    private static final String AUTHENTICATE_QUERY = """
        select password = crypt(?, password)
        from users
        where psn_name = ?
        """;

    private static final String GET_USER_QUERY = """
        select id, psn_name, created_at, updated_at
        from users
        where psn_name = ?
        """;

    @Override
    @SneakyThrows
    public boolean authenticate(String psnName, String password) {
        return queryRunner.query(AUTHENTICATE_QUERY, ValueHandler.BOOLEAN, password, psnName)
            .orElse(false);
    }

    @Override
    @SneakyThrows
    public User getUser(String psnName) {
        final var maybeUser = queryRunner.query(GET_USER_QUERY, (RecordHandler<User>) rs -> new User(
            rs.getString("id"),
            rs.getString("psn_name"),
            rs.getObject("created_at", LocalDateTime.class),
            rs.getObject("updated_at", LocalDateTime.class)
        ), psnName);

        return maybeUser.orElseThrow(() -> new UserDoesNotExistException(psnName));
    }
}
