package org.openpsn.api.util;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class QueryUtils {
    public static void clearUsers(QueryRunner queryRunner) throws SQLException {
        queryRunner.update("delete from users");
    }

    public static void createUser(
        QueryRunner queryRunner,
        String username,
        String password,
        String validationCode
    ) throws SQLException {
        queryRunner.update(
            "insert into users (psn_name, password, validation_code) values (?, crypt(?, gen_salt('bf')), ?)",
            username,
            password,
            validationCode);
    }

    public static void createUser(QueryRunner queryRunner, String username, String password) throws SQLException {
        createUser(queryRunner, username, password, null);
    }
}
