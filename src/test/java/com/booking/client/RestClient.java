package com.booking.client;

import io.restassured.RestAssured;
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
}
