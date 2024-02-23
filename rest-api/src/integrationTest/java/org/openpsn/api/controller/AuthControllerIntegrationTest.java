package org.openpsn.api.controller;

import io.jooby.StatusCode;
import io.restassured.http.ContentType;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openpsn.api.ApiApplication;
import org.openpsn.api.IntegrationTest;
import org.openpsn.api.db.ValueHandler;

import java.sql.SQLException;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.openpsn.api.util.QueryUtils.clearUsers;
import static org.openpsn.api.util.QueryUtils.createUser;

@IntegrationTest(ApiApplication.class)
public class AuthControllerIntegrationTest {
    @AfterEach
    public void tearDown(QueryRunner queryRunner) throws SQLException {
        clearUsers(queryRunner);
    }

    @Test
    public void exchangeForAuthToken_should_errorIfUserDoesNotExist() {
        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.AuthenticationRequest("test", "pass"))
            .post("/auth/token")
            .then()
            .statusCode(equalTo(StatusCode.FORBIDDEN_CODE));
    }

    @Test
    public void exchangeForAuthToken_should_errorIfCredentialsIncorrect(QueryRunner queryRunner) throws SQLException {
        createDefaultUser(queryRunner, null);

        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.AuthenticationRequest("test", "wrong"))
            .post("/auth/token")
            .then()
            .statusCode(equalTo(StatusCode.FORBIDDEN_CODE));
    }

    @Test
    public void exchangeForAuthToken_should_generateTokens(QueryRunner queryRunner) throws SQLException {
        createDefaultUser(queryRunner, null);

        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.AuthenticationRequest("test", "pass"))
            .post("/auth/token")
            .then()
            .statusCode(equalTo(StatusCode.OK_CODE))
            .body("accessToken", notNullValue())
            .body("refreshToken", notNullValue());
    }

    @Test
    public void register_should_createNewUser(QueryRunner queryRunner) throws SQLException {
        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.AuthenticationRequest("test", "pass"))
            .post("/auth/register")
            .then()
            .statusCode(equalTo(StatusCode.OK_CODE))
            .body("validationCode", hasLength(8));

        assertUserFound(queryRunner, "test");
    }

    @Test
    public void register_should_updateUserIfExistingNotValidated(QueryRunner queryRunner) throws SQLException {
        createDefaultUser(queryRunner, "abc123");

        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.AuthenticationRequest("test", "pass"))
            .post("/auth/register")
            .then()
            .statusCode(equalTo(StatusCode.OK_CODE))
            .body("validationCode", hasLength(8));
    }

    @Test
    public void register_should_errorIfUserAlreadyValidated(QueryRunner queryRunner) throws SQLException {
        createDefaultUser(queryRunner, null);

        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.AuthenticationRequest("test", "pass"))
            .post("/auth/register")
            .then()
            .statusCode(equalTo(StatusCode.BAD_REQUEST_CODE));
    }

    private void assertUserFound(QueryRunner queryRunner, String psnName) throws SQLException {
        final var matchingUsers = queryRunner.query(
            "select count(*)::int from users where psn_name = ?",
            ValueHandler.INT,
            psnName);

        assertThat(matchingUsers).isEqualTo(Optional.of(1));
    }

    private void createDefaultUser(QueryRunner queryRunner, String validationCode) throws SQLException {
        createUser(queryRunner, "test", "pass", validationCode);
    }
}
