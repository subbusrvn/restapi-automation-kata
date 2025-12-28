package com.booking.services;

import com.booking.client.RestClient;
import com.booking.context.TestContext;
import com.booking.endpoints.AuthEndpoints;
import com.booking.models.auth.AuthRequest;
import io.restassured.response.Response;

public class AuthService {

    private final RestClient restClient;
    private final TestContext testContext;

    public AuthService() {

        this.restClient = new RestClient();
        this.testContext = new TestContext();
    }

    public Response login(AuthRequest authRequest, TestContext testContext) {
        return restClient.post(AuthEndpoints.LOGIN, authRequest);
    }

    /**
     * Invalid Content-Type scenario – business negative case
     */
    public Response loginWithInvalidContentType(AuthRequest authRequest) {

        return restClient.loginPostRequest()
                .header("Content-Type", "text/plain")
                .log().all()
                .body(authRequest)
                .post(AuthEndpoints.LOGIN);
    }

}