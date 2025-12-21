package com.booking.services;

import com.booking.client.RestClient;
import com.booking.config.ConfigManager;
import com.booking.endpoints.AuthEndpoints;
import com.booking.models.auth.AuthRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;


public class AuthService {

    private final RestClient restClient;

    public AuthService() {
        this.restClient = new RestClient();
    }

    public Response login(AuthRequest authRequest) {
/*
        JSONObject payload = new JSONObject();
        payload.put("username", username);
        payload.put("password", password);
*/
        return  given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)        // important
                .body(authRequest)
                .when()
                .post(AuthEndpoints.LOGIN)
                .then()
                .extract().response();



    }

    /**
     * Invalid Content-Type scenario – business negative case
     */
    public Response loginWithInvalidContentType(AuthRequest authRequest) {
/*
        JSONObject payload = new JSONObject();
        payload.put("username", ConfigManager.getProperty("username"));
        payload.put("password", ConfigManager.getProperty("password"));
*/
        return RestClient.loginPostRequest()
                .header("Content-Type", "text/plain")
                .body(authRequest)
                .post(AuthEndpoints.LOGIN);
    }

}