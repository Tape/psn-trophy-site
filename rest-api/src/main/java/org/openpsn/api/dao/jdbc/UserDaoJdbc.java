package org.openpsn.api.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.openpsn.api.dao.UserDao;
import org.openpsn.api.db.RecordHandler;
import org.openpsn.api.model.User;
import org.openpsn.api.service.SecurityService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class UserDaoJdbc implements UserDao {
    private final QueryRunner queryRunner;
    private final SecurityService securityService;

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

    private static final String REGISTER_QUERY = """
        insert into users (psn_name, password, validation_code)
        values (?, crypt(?, gen_salt('bf')), crypt(?, gen_salt('bf')))
        on conflict (psn_name) do update set
          password = excluded.password,
          validation_code = excluded.validation_code,
          updated_at = excluded.updated_at
        where users.validation_code is not null
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
    public Optional<User> getUser(String psnName) {
        return queryRunner.query(GET_USER_QUERY, USER_HANDLER, psnName);
    }

    @Override
    @SneakyThrows
    public Optional<String> register(String psnName, String password) {
        final var validationCode = String.format("%08x", securityService.randomInt());

        // If a user exists and is validated, 0 will be returned
        return queryRunner.execute(REGISTER_QUERY, psnName, password, validationCode) == 1
            ? Optional.of(validationCode)
            : Optional.empty();
    }
}
