package com.booking.stepdefinitions;

import com.booking.config.ConfigManager;
import com.booking.context.TestContext;
import com.booking.services.AuthService;
import com.booking.utils.TokenManager;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

public class AuthSteps {

    private final TestContext context;
    private final AuthService authService;
    private Response response;
    private String token;

    // Constructor injection for shared TestContext
    public AuthSteps(TestContext context) {
        this.context = context;
        this.authService = new AuthService();
    }

    // -------------------------
    // Given Steps
    // -------------------------

    @Given("a user wants to access the booking system")
    public void auth_endpoint_available() {
        System.out.println("Auth endpoint ready at " + context.getBaseUri());
    }

    @Given("the system is available for user access")
    public void the_authentication_service_is_running() {
        RestAssured.baseURI = ConfigManager.getProperty("base_url");
        System.out.println("Auth endpoint ready at " + RestAssured.baseURI);
    }

    @Given("a user is logged into the booking system")
    public void user_is_logged_in() {

        Response response = authService.login(
                ConfigManager.getProperty("valid.username"),
                ConfigManager.getProperty("valid.password")
        );

        Assert.assertEquals(200, response.getStatusCode());

        String token = response.jsonPath().getString("token");
        System.out.println("First User Loggedin Totken is :"+ token);
        Assert.assertNotNull("Token should not be null", token);
        context.set("token", token);
        TokenManager.setToken(token);
    }

    @When("the user logs in again with the same credentials")
    public void user_logs_in_second_time() {

        Response response = authService.login(
                ConfigManager.getProperty("valid.username"),
                ConfigManager.getProperty("valid.password")
        );

        Assert.assertEquals(200, response.getStatusCode());
        String token2 = response.jsonPath().getString("token");
        System.out.println("Second User Login Token is :" +token2);
        Assert.assertNotNull(token2);
        context.set("token2", token2);
    }



    // -------------------------
    // When Steps
    // -------------------------

    @When("the user logs in with {string} credentials")
    public void send_auth_request_by_user_type(String userType) {

        String username;
        String password;

        switch (userType) {
            case "valid":
                username = ConfigManager.getProperty("valid.username");
                password = ConfigManager.getProperty("valid.password");
                break;
            case "invalidUser":
                username = ConfigManager.getProperty("invalid.username");
                password = ConfigManager.getProperty("valid.password");
                break;
            case "invalidPassword":
                username = ConfigManager.getProperty("valid.username");
                password = ConfigManager.getProperty("invalid.password");
                break;
            case "emptyUsername":
                username = ConfigManager.getProperty("empty.username");
                password = ConfigManager.getProperty("valid.password");
                break;
            case "emptyPassword":
                username = ConfigManager.getProperty("valid.username");
                password = ConfigManager.getProperty("empty.password");
                break;
            case "missingRequest":
                username = "";
                password = "";
                break;
            case "sqlinjUsername":
                username = ConfigManager.getProperty("sql.injection.username");
                password = ConfigManager.getProperty("valid.password");
                break;
            case "sqlinjPassword":
                username = ConfigManager.getProperty("valid.username");
                password = ConfigManager.getProperty("sql.injection.password");
                break;
            case "caseSenUsername":
                username = ConfigManager.getProperty("caseSenUsername");
                password = ConfigManager.getProperty("valid.password");
                break;
            case "caseSenPassword":
                username = ConfigManager.getProperty("valid.username");
                password = ConfigManager.getProperty("caseSenPassword");
                break;

            case "splcharUsername":
                username = ConfigManager.getProperty("splcharUsername");
                password = ConfigManager.getProperty("valid.password");
                break;
            case "splcharPassword":
                username = ConfigManager.getProperty("valid.username");
                password = ConfigManager.getProperty("splcharUsername");
                break;

            default:
                throw new RuntimeException("Unknown user type: " + userType);
        }

        context.setResponse(authService.login(username, password));
    }

    @When("a client submits a login request in an unsupported format")
    public void sendLoginWithInvalidContentType() {
        response = authService.loginWithInvalidContentType();
    }

    @When("login with valid credentials")
    public void submit_valid_login() {
        response = authService.login(
                ConfigManager.getProperty("valid.username"),
                ConfigManager.getProperty("valid.password")
        );
    }

    @When("the user logs out")
    public void user_logs_out() {
        System.out.println("Logout Status code" +response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
    }


    // -------------------------
    // Then Steps
    // -------------------------

    @Then("access should be {word}")
    public void access_should_be(String loginResult) {
        switch (loginResult.toLowerCase()) {
            case "granted":
                assertAccessGranted();
                break;
            case "denied":
                assertAccessDenied();
                break;
            default:
                throw new IllegalArgumentException("Unsupported login result: " + loginResult);
        }
    }

    @Then("the system should reject the request")
    public void verifyUnsupportedMediaType() {
        Assert.assertEquals(415, response.getStatusCode());
    }

    @Then("the system should return a token")
    public void verify_token_exists() {

        token = response.jsonPath().getString("token");
        System.out.println("Response: " + response.asString());
        System.out.println("Token value is " + token);
        Assert.assertNotNull("Token should not be null", token);
        Assert.assertFalse("Token should not be empty", token.isEmpty());
    }

    @Then("the token format should be alphanumeric")
    public void verify_token_format() {
        Assert.assertTrue("Token is not alphanumeric: " + token, token.matches("^[a-zA-Z0-9]+$")
        );
    }

    @Then("the token length should be greater than 10 characters")
    public void verify_token_length() {

        Assert.assertTrue("Token too short", token.length() > 10);
    }

    @Then("Content-Type is genearted in the header")
    public void is_cookie_Genearted_InHeader() {
        String contentType = response.getHeader("Content-Type");
        System.out.println("Content-Type Value is: "+contentType);

        if (contentType != null) {
            Assert.assertTrue(contentType.contains("application/json"));
        } else {
            System.out.println("INFO: Login API does not Content-Type");
        }
    }


    @Then("each login should create a unique session")
    public void each_login_should_create_unique_session() {

        String token1 = context.get("token1");
        String token2 = context.get("token2");

        Assert.assertNotEquals(
                "Each login must create a unique session token", token1,token2
        );
    }

    // -------------------------
    // Private Helper Methods
    // -------------------------

    private void assertAccessGranted() {
        Assert.assertEquals(200, context.getResponse().getStatusCode());
        String token = context.getResponse().jsonPath().getString("token");
        Assert.assertNotNull("Token should not be null", token);
        Assert.assertNotNull("Token should not be empty", token.isEmpty());
    }

    private void assertAccessDenied() {

        Assert.assertEquals(401, context.getResponse().getStatusCode());

        Response response = context.getResponse(); // use TestContext
        Assert.assertNotNull("Response is null", response);

        String actualMessage = response.jsonPath().getString("error");
        String expectedMessage = ConfigManager.getProperty("login.error.message");
        System.out.println("Error message from API: " + actualMessage);
        Assert.assertEquals(expectedMessage,actualMessage);
        Assert.assertNull("Token Value must be empty",token); // or ensure token is NOT present
    }
}
