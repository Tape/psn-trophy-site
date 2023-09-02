package org.openpsn.api.controller;

import io.jooby.StatusCode;
import io.restassured.http.ContentType;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openpsn.api.ApiApplication;
import org.openpsn.api.IntegrationTest;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@IntegrationTest(ApiApplication.class)
public class AuthControllerIntegrationTest {
    @AfterEach
    public void tearDown(QueryRunner queryRunner) throws SQLException {
        queryRunner.update("delete from users");
    }

    @Test
    public void exchangeForAuthToken_should_errorIfUserDoesNotExist() {
        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.TokenRequest("test", "pass"))
            .post("/auth/token")
            .then()
            .statusCode(equalTo(StatusCode.FORBIDDEN_CODE));
    }

    @Test
    public void exchangeForAuthToken_should_errorIfCredentialsIncorrect(QueryRunner queryRunner) throws SQLException {
        createDefaultUser(queryRunner);

        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.TokenRequest("test", "wrong"))
            .post("/auth/token")
            .then()
            .statusCode(equalTo(StatusCode.FORBIDDEN_CODE));
    }

    @Test
    public void exchangeForAuthToken_should_generateTokens(QueryRunner queryRunner) throws SQLException {
        createDefaultUser(queryRunner);

        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.TokenRequest("test", "pass"))
            .post("/auth/token")
            .then()
            .statusCode(equalTo(StatusCode.OK_CODE))
            .body("accessToken", notNullValue())
            .body("refreshToken", notNullValue());
    }

    private void createDefaultUser(QueryRunner queryRunner) throws SQLException {
        queryRunner.update(
            "insert into users (psn_name, password) values (?, crypt(?, gen_salt('bf')))",
            "test",
            "pass");
    }
}
