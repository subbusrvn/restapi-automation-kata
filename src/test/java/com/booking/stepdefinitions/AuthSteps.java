package com.booking.stepdefinitions;

import com.booking.config.ConfigManager;
import com.booking.context.TestContext;
import com.booking.models.auth.AuthRequest;

import com.booking.services.AuthService;
import com.booking.utils.AuthRequestFactory;
import com.booking.utils.LoggerUtil;
import com.booking.utils.TokenManager;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class AuthSteps {
    private static final Logger log =
            LoggerUtil.getLogger(BookingSteps.class);

    private final TestContext context;
    private final AuthService authService;
    private String token;

    public AuthSteps(TestContext context) {
        this.context = context;
        this.authService = new AuthService();
    }

    // -------------------------
    // Given Steps
    // -------------------------

    @Given("a user wants to access the booking system")
    public void auth_endpoint_available() {
        log.info("****\"Auth endpoint ready at {}" ,context.getBaseUri());
    }

    @Given("the system is available for user access")
    public void the_authentication_service_is_running() {
        RestAssured.baseURI = ConfigManager.getProperty("base_url");
        log.info("*****Auth endpoint ready at{}*****", RestAssured.baseURI);
    }

    @Given("a user is logged into the booking system")
    public void user_is_logged_in() {
        AuthRequest request = AuthRequestFactory.build("valid");
        login(request, "token");
    }

    private void login(AuthRequest request, String tokenKey) {
        Response response = authService.login(request);
        context.setResponse(response);

        String token = response.jsonPath().getString("token");

        log.info("****Logged in user token{}:****", token);
        Assert.assertNotNull("Token should not be null", token);
        context.set(tokenKey, token);
        TokenManager.setToken(token);
    }

    // -------------------------
    // When Steps
    // -------------------------
    @When("the user logs in with {string} credentials")
    public void send_auth_request_by_user_type(String userType) {
        //login(buildAuthRequestByUserType(userType));
        AuthRequest request = AuthRequestFactory.build(userType); // Delegated!
        Response response = authService.login(request);
        context.setResponse(response);

    }

    @When("login with valid credentials")
    @When("the user logs in again with the same credentials")
    public void login_with_valid_credentials() {
        AuthRequest request = AuthRequestFactory.build("valid");

        // Call the login helper and store the second token
        login(request, "token2");
    }

    @When("a client submits a login request in an unsupported format")
    public void sendLoginWithInvalidContentType() {
        // Build AuthRequest using the factory
        AuthRequest request = AuthRequestFactory.build("valid");
        // Call the service with invalid content type
        Response response = authService.loginWithInvalidContentType(request);
        // Store response in context
        context.setResponse(response);
    }
    // -------------------------
    // Then Steps
    // -------------------------
    @Then("access should be {word}")
    public void access_should_be(String loginResult) {
        switch (loginResult.toLowerCase()) {
            case "granted" -> assertAccessGranted();
            case "denied" -> assertAccessDenied();
            default -> throw new IllegalArgumentException("Unsupported: " + loginResult);
        }
    }

    @Then("the system should reject the request")
    public void verifyUnsupportedMediaType() {

        Assert.assertEquals(415, context.getResponse().getStatusCode());
    }

    @Then("the system should return a token")
    public void verify_token_exists() {
        token = context.getResponse().jsonPath().getString("token");
        Assert.assertNotNull(token);
        Assert.assertFalse(token.isEmpty());
    }

    @Then("the token format should be alphanumeric")
    public void verify_token_format() {

        Assert.assertTrue(token.matches("^[a-zA-Z0-9]+$"));
    }

    @Then("the token length should be greater than 10 characters")
    public void verify_token_length() {

        Assert.assertTrue(token.length() > 10);
    }

    @Then("Content-Type is genearted in the header")
    public void is_cookie_Genearted_InHeader() {
        String contentType = context.getResponse().getHeader("Content-Type");
        if (contentType != null) Assert.assertTrue(contentType.contains("application/json"));
    }

    @Then("each login should create a unique session")
    public void each_login_should_create_unique_session() {
        Assert.assertNotEquals(context.get("token"), context.get("token2"));
    }

    // -------------------------
    // Private helpers
    // -------------------------
    private void assertAccessGranted() {
        Response response = context.getResponse();
        Assert.assertEquals(200, response.getStatusCode());
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token);
        Assert.assertFalse(token.isEmpty());
    }

    private void assertAccessDenied() {
        Response response = context.getResponse();
        Assert.assertEquals(401, response.getStatusCode());
        String actual = response.jsonPath().getString("error");
        String expected = ConfigManager.getProperty("login.error.message");
        Assert.assertEquals(expected, actual);
        Assert.assertNull(token);
    }
}