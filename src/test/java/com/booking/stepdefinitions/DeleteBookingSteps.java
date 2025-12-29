package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.BookingIdExtractor;
import com.booking.utils.LoggerUtil;
import com.booking.utils.TokenManager;
import com.booking.utils.excel.factory.BookingRequestFactory;
import com.booking.validators.DeleteBookingResponseValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import static org.junit.Assert.*;

public class DeleteBookingSteps {

    private final TestContext testContext;
    private final BookingService bookingService;


    public DeleteBookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();

    }

    private static final Logger log = LoggerUtil.getLogger(CreateBookingSteps.class);

    @Given("the booking system is available for user access")
    public void booking_system_is_available() {
    }

    @Given("an invalid booking ID is set")
    public void an_invalid_booking_id_is_set() {
        int invalidBookingId = 99999999; //
        testContext.setBookingId(invalidBookingId);
        log.info("Invalid booking ID set in context: {}", invalidBookingId);

    }

    @Given("a valid booking ID exists")
    public void a_valid_booking_id_exists() {
        // You can either create a booking dynamically or use a known booking ID
        int bookingId = 5; // example booking ID
        log.info("Booking Id set to delete{}", bookingId);
    }

    @Given("the user has an invalid or expired token")
    public void set_invalid_token() {
        String invalidToken = "invalidToken123";

        testContext.setToken(invalidToken);
        testContext.setUseInvalidToken(true);
        TokenManager.setToken(invalidToken);
        log.info("Using expired/invalid authentication token for delete booking request");

    }

    @When("the user creates a booking with {string}")
    public void submit_booking_request(String dataset) {
        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);
        Response response = bookingService.createBooking(request);
        testContext.setCreateResponse(response);
    }


    @When("the user deletes the booking using the stored booking ID")
    public void the_user_sends_a_delete_request_to_the_booking_endpoint() {

        Integer bookingId  = testContext.getBookingId();
        String token = testContext.getToken();

        log.info("**** Attempting to delete Booking ID: " + bookingId + " with token: " + token);
        Response deleteResponse = bookingService.deleteBooking(bookingId);

        // Save the response in TestContext so we can validate later
        testContext.setDeleteResponse(deleteResponse);
    }

    @When("the user sends a DELETE request without the auth token")
    public void the_user_sends_DELETE_request_without_token() {
        testContext.setToken(null);
        TokenManager.setToken(null);
        int bookingId = 5;
        log.info("**** Set null token to valid  Booking Id *****" + bookingId);
        Response deleteResponse = bookingService.deleteBooking(bookingId);
        testContext.setDeleteResponse(deleteResponse);
    }

    @Then("the booking is successfully created")
    public void booking_successfully_created() {
        Response response = testContext.getCreateResponse();
        Integer bookingId = BookingIdExtractor.extract(response);
        Assert.assertNotNull("Booking ID should not be null", bookingId);
        testContext.setBookingId(bookingId);
    }

    @Then("the deletion should be successful")
    public void validate_delete_status() {
        Response deleteResponse = testContext.getDeleteResponse();
        DeleteBookingResponseValidator.validateDeleteResponse(deleteResponse);
    }

    @Then("retrieving the same booking ID should display not found")
    public void validate_deleted_booking() {
        Integer bookingId = testContext.getBookingId();
        Response getResponse = bookingService.getBookingById(bookingId);
        DeleteBookingResponseValidator.validateDeleteResponse(getResponse);
    }

    @Then("the API should return an unauthorized status")
    public void the_response_status_unauthorized() {
        Response response = testContext.getDeleteResponse();
        DeleteBookingResponseValidator.validateDeleteResponse(response);
    }

    @Then("attempt to update a booking that does not exist")
    public void the_response_status_code_should_be() {
        Response response = testContext.getResponse();
        DeleteBookingResponseValidator.validateDeleteResponse(response);
    }

    @Then("the response should state that the requested booking does not exist")
    public void the_response_body_should_indicate_booking_not_found() {
        Response response =  testContext.getResponse();
        DeleteBookingResponseValidator.validateDeleteResponse(response);
    }

    @Then("the response should clearly communicate that the request is not authorized")
    public void validate_response_body() {
        String errorMessage = testContext.getResponse().jsonPath().getString("error");
        assertTrue(errorMessage.toLowerCase().contains("unauthorized") ||
                errorMessage.toLowerCase().contains("forbidden"));
    }

    @Then("the error message should indicate unauthorized access")
    public void validate_error_message() {
        Response deleteResponse = testContext.getDeleteResponse();
        DeleteBookingResponseValidator.validateDeleteResponse(deleteResponse);
    }

}