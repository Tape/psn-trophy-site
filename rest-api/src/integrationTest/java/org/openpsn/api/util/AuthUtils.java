package org.openpsn.api.util;

import io.restassured.http.ContentType;
import org.openpsn.api.controller.AuthController;

import static io.restassured.RestAssured.given;

public class AuthUtils {
    public static String getAccessToken(String username, String password) {
        return given()
            .contentType(ContentType.JSON)
            .body(new AuthController.TokenRequest(username, password))
            .post("/auth/token")
            .jsonPath()
            .getString("accessToken");
    }
}
