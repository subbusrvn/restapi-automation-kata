package com.booking.client;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RestClient {

    public static RequestSpecification loginPostRequest() {
        return RestAssured
                .given()
                .header("Content-Type", "application/json");
    }
}
