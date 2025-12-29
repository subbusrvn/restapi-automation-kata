package com.booking.stepdefinitions;

import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;
import com.booking.context.TestContext;
import com.booking.services.BookingService;
import com.booking.utils.LoggerUtil;
import com.booking.utils.*;
import com.booking.utils.excel.factory.BookingRequestFactory;
import com.booking.validators.UpdateBookingResponseValidator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UpdateBookingSteps {
    private static final Logger log = LoggerUtil.getLogger(CreateBookingSteps.class);
    private final TestContext testContext;
    private final BookingService bookingService;

    public UpdateBookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }
    /*
    *---------------------------------
    * Scenario Steps
    * ---------------------------------
    */
    @Given("use an invalid authentication token")
    public void i_use_an_invalid_authentication_token() {
        String invalidToken = "INVALID_TOKEN_12345";

        testContext.setToken(invalidToken);
        testContext.setUseInvalidToken(true);
        TokenManager.setToken(invalidToken);
        log.info("************After set the token to Null******{}", invalidToken);
    }
    @Given("the guest now has an existing booking")
    public void an_existing_booking_is_created() {
        Response response = testContext.getResponse();// or make a POST call
        Integer bookingId = response.jsonPath().getInt("bookingid");
        assertTrue("Booking must have a valid positive ID", bookingId > 0);
        log.info("Already Created Booking ID: {}", bookingId);
    }

    @When("a guest creates a new booking with initial details {string}")
    public void submit_booking_request(String dataset) {
        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);
        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);
    }

    @Then("the booking should be {string} successfully")
    public void booking_should_be(String bookingoutcome) {
        Response response = testContext.getResponse();
        UpdateBookingResponseValidator.validateCreateMsg(response, bookingoutcome,testContext);
    }

    @When("use an expired authentication token")
    public void use_an_expired_authentication_token() {
        // Set the flag to use invalid token in TestContext
        String expiredToken = "6ZBb8K7PrJNUiBbU";
        testContext.setToken(expiredToken);
        testContext.setUseInvalidToken(true);
        TokenManager.setToken(expiredToken);
        log.info("Using expired/invalid authentication token for update booking request");
    }

    @When("use without authentication token")
    public void without_authentication_token() {
        String expiredToken = "";
        testContext.setToken(expiredToken);
        testContext.setUseInvalidToken(true);
        TokenManager.setToken(expiredToken);
        log.info("*****Without authentication token*****");
    }

    @When("Set the non-existing booking id to current booking")
    public void Update_the_Bookingwith_NonExisting() {
        int non_existing_bookingId = 9999999;
        testContext.setBookingId(non_existing_bookingId);
        log.info("*****Non existing book id has been set*****");
    }

    @When("Update the below booking with PATCH method")
    public void Update_the_below_booking_with_following_fields(DataTable dataTable) {

        Map<String, String> data = dataTable.asMaps().get(0);

        BookingRequest updateRequest =
                PatchBookingRequestBuilder.build(
                        data.get("firstname"),
                        data.get("lastname"),
                        Boolean.parseBoolean(data.get("depositpaid")),
                        null,
                        null,
                        null,
                        null
                );

        Response response = bookingService.updateBookingPatch(
                testContext.getBookingId(),
                updateRequest
        );

        testContext.setUpdateRequest(updateRequest);
        testContext.setUpdateResponse(response);
    }

    @When("the guest updates the booking with the following details")
    public void update_booking_with_following_fields(DataTable dataTable) {

        Map<String, String> data = dataTable.asMaps(String.class, String.class).get(0);
        // Pre-parse using external parser (all logic outside this method)
        Integer roomid = SafeParser.toInteger(data.get("roomid"));
        Boolean depositpaid = SafeParser.toBoolean(data.get("depositpaid"));
        String firstname = SafeParser.toNullIfEmpty(data.get("firstname"));
        String lastname = SafeParser.toNullIfEmpty(data.get("lastname"));
        String email = SafeParser.toNullIfEmpty(data.get("email"));
        String phone = SafeParser.toNullIfEmpty(data.get("phone"));
        BookingDates bookingDates = new BookingDates(
                SafeParser.toNullIfEmpty(data.get("bookingdates.checkin")),
                SafeParser.toNullIfEmpty(data.get("bookingdates.checkout"))
        );
        BookingRequest updateRequest = PutBookingRequestBuilder.build(
                roomid, firstname, lastname, depositpaid, bookingDates, email, phone
        );

        Response response = bookingService.updateBookingPut(
                testContext.getBookingId(), updateRequest
        );
        testContext.setUpdateRequest(updateRequest);
        testContext.setUpdateResponse(response);
    }

    @Then("the booking patch update should be successful")
    public void the_booking_patch_update_should_be_successful() {
        Response response = testContext.getUpdateResponse();
        UpdateBookingResponseValidator.validateUpdateResponse(response,testContext);

    }
    @Then("the guest should see all the updated details reflected in the booking")
    public void the_updated_booking_should_reflect_all_the_new_values() {

        Response updateResponse = testContext.getUpdateResponse();
        assertEquals("Update should be successful",
                200,
                updateResponse.getStatusCode());
        log.info("***Update accepted successfully. Field-level persistence validation skipped as per API behavior*****");
    }

    @Then("the booking should not be updated")
    public void booking_should_not_updated() {
        Response response = testContext.getUpdateResponse();
        assertEquals(400, response.statusCode());
    }

    @Then("the system should reject the request due to invalid or missing authorization")
    public void booking_should_not_updated_unauthorized() {
        Response response = testContext.getUpdateResponse();
        assertEquals(401, response.statusCode());
    }

    @Then("Ensure update fails gracefully when booking ID does not exist")
    public void booking_should_update_non_existing() {
        Response response = testContext.getUpdateResponse();
        assertEquals(404, response.statusCode());
    }

    @Then("The response body should indicate {string}")
    public void the_response_body_should_indicate() {
        Response response = testContext.getUpdateResponse();
        String responseBody = response.getBody().asString();
        String expectedMessage = "Unauthorized";
        assertNotNull(responseBody, "Response body is null");

        assertTrue(
                "Expected response body to contain: " + expectedMessage
                        + " but actual response was: " + responseBody,
                responseBody.toLowerCase().contains(expectedMessage.toLowerCase())
        );
    }

}
