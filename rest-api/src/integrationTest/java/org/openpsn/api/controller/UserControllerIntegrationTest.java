package org.openpsn.api.controller;

import io.jooby.StatusCode;
import io.restassured.http.ContentType;
import io.undertow.util.Headers;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openpsn.api.ApiApplication;
import org.openpsn.api.IntegrationTest;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.openpsn.api.util.AuthUtils.getAccessToken;
import static org.openpsn.api.util.QueryUtils.clearUsers;
import static org.openpsn.api.util.QueryUtils.createUser;

@IntegrationTest(ApiApplication.class)
public class UserControllerIntegrationTest {
    private String accessToken;

    @BeforeEach
    public void setUp(QueryRunner queryRunner) throws SQLException {
        createUser(queryRunner, "test", "pass");
        accessToken = getAccessToken("test", "pass");
    }

    @AfterEach
    public void tearDown(QueryRunner queryRunner) throws SQLException {
        clearUsers(queryRunner);
    }

    @Test
    public void getUser_should_throwExceptionWithoutAuthHeader() {
        given()
            .contentType(ContentType.JSON)
            .get("/api/v1/users/@me")
            .then()
            .statusCode(equalTo(StatusCode.UNAUTHORIZED_CODE));
    }

    @Test
    public void getUser_should_returnOwnUserUsingMe() {
        given()
            .header(Headers.AUTHORIZATION_STRING, "Bearer " + accessToken)
            .get("/api/v1/users/@me")
            .then()
            .statusCode(equalTo(StatusCode.OK_CODE))
            .body("psnName", is("test"));
    }

    @Test
    public void getUser_should_returnUserUsingPsnName() {
        given()
            .header(Headers.AUTHORIZATION_STRING, "Bearer " + accessToken)
            .get("/api/v1/users/test")
            .then()
            .statusCode(equalTo(StatusCode.OK_CODE))
            .body("psnName", is("test"));
    }
}
