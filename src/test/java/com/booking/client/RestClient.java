package com.booking.client;

import com.booking.utils.TokenManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {

    public static RequestSpecification loginPostRequest() {
        return given()
                .header("Content-Type", "application/json");
    }

    public static Response post(String url, Object bdy) {
        return given()
                .headers("Content-Type", "application/json")
                .body(bdy)
                .when()
                .post(url)
                .then()
                .extract().response();
    }
    public static Response get(String url) {
        String token = TokenManager.getToken();

        return given()
                .log().all()
                .cookie("token", token)
                .when()
                .get(url)
                .then()
                .log().all()
                .extract().response();
    }


    public static Response patch(String endpoint, Object body) {
        String token = TokenManager.getToken();
        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body)
                .log().body()
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        String token = TokenManager.getToken();
        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .log().body()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

}
