package org.openpsn.api.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.openpsn.api.ApiApplication;
import org.openpsn.api.IntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@IntegrationTest(ApiApplication.class)
public class AuthControllerIntegrationTest {
    @Test
    public void exchangeForAuthToken_should_generateTokens() {
        given()
            .contentType(ContentType.JSON)
            .body(new AuthController.TokenRequest("test", "pass"))
            .post("/auth/token")
            .then()
            .statusCode(equalTo(200))
            .body("accessToken", notNullValue())
            .body("refreshToken", notNullValue());
    }
}
