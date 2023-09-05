package org.openpsn.api.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.openpsn.api.dao.UserDao;
import org.openpsn.api.db.RecordHandler;
import org.openpsn.api.exception.auth.UserDoesNotExistException;
import org.openpsn.api.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class UserDaoJdbc implements UserDao {
    private final QueryRunner queryRunner;

    private static final String AUTHENTICATE_QUERY = """
        select id, psn_name, created_at, updated_at
        from users
        where psn_name = ? and password = crypt(?, password)
        """;

    private static final String GET_USER_QUERY = """
        select id, psn_name, created_at, updated_at
        from users
        where psn_name = ?
        """;

    private static final RecordHandler<User> USER_HANDLER = rs -> new User(
        rs.getString("id"),
        rs.getString("psn_name"),
        rs.getObject("created_at", LocalDateTime.class),
        rs.getObject("updated_at", LocalDateTime.class)
    );

    @Override
    @SneakyThrows
    public Optional<User> authenticate(String psnName, String password) {
        return queryRunner.query(AUTHENTICATE_QUERY, USER_HANDLER, psnName, password);
    }

    @Override
    @SneakyThrows
    public User getUser(String psnName) {
        final var maybeUser = queryRunner.query(GET_USER_QUERY, USER_HANDLER, psnName);
        return maybeUser.orElseThrow(() -> new UserDoesNotExistException(psnName));
    }
}
