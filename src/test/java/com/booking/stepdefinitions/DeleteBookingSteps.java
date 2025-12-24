package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.BookingRequestFactory;
import com.booking.utils.LoggerUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;


import static org.hamcrest.CoreMatchers.containsString;
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
/*
    // Optional: negative scenario for unauthorized deletion
    @Given("the user is not logged in")
    public void user_not_logged_in() {
        testContext.setToken(null); // Clear token
    }
*/
    /*
    @Given("the user is authenticated with a valid token")
    public void the_user_is_authenticated_with_a_valid_token() {
        log.info(" Before deleting the Data, Token value", testContext.getToken());
    }

     */

    @Given("an invalid booking ID is set")
    public void an_invalid_booking_id_is_set() {
        testContext.setBookingId(999999);
    }

    @Given("a valid booking ID exists")
    public void a_valid_booking_id_exists() {
        // You can either create a booking dynamically or use a known booking ID
        int bookingId = 5; // example booking ID
        log.info("Booking Id set to delete{}",bookingId);
    }

    @Given("the user has an invalid or expired token")
    public void set_invalid_token() {
        testContext.setToken("invalidToken123"); // or expired token if you have one
    }

/*
    @When("the user deletes the booking with ID {int}")
    public void delete_booking_unauthorized(int bookingId) {
        Response deleteResponse = bookingService.deleteBooking(bookingId);
        testContext.setDeleteResponse(deleteResponse);
    }
*/
    @When("the user creates a booking with {string}")
    public void submit_booking_request(String dataset) {
        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);
        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);
        testContext.setBookingId(response.jsonPath().getInt("bookingid"));
    }

    @When("the user deletes the booking using the stored booking ID")
    public void the_user_sends_a_delete_request_to_the_booking_endpoint() {

        Integer bookingId = testContext.getBookingId();
        String token = testContext.getToken(); // can be null for unauthorized tests

        if (bookingId == null) {
            throw new IllegalStateException("Booking ID is null");
        }

        log.info("**** Attempting to delete Booking ID: " + bookingId + " with token: " + token);

        // Call delete API using bookingService
        Response deleteResponse = bookingService.deleteBooking(bookingId);

        // Save the response in TestContext so we can validate later
        testContext.setDeleteResponse(deleteResponse);
    }

    @When("the user sends a DELETE request without the auth token")
    public void the_user_sends_DELETE_request_without_token() {
        testContext.setToken(null);
        int bookingId = 5;
        log.info("**** Set Invalid Booking Id is*****" + bookingId);
        Response deleteResponse = bookingService.deleteBooking(bookingId);
        testContext.setResponse(deleteResponse);
    }


    @Then("the booking is successfully created")
    public void booking_successfully_created() {
        Integer bookingId = testContext.getBookingId();
        assert bookingId != null && bookingId > 0;


    }


    @Then("the deletion should be successful with status code {int}")
    public void validate_delete_status(int expectedStatus) {
        Response deleteResponse = testContext.getDeleteResponse();
        assertEquals(expectedStatus, deleteResponse.getStatusCode());
    }

    @Then("retrieving the same booking ID should return {int} not found")
    public void validate_deleted_booking(int expectedStatus) {
        int bookingId = testContext.getBookingId();
        Response getResponse = bookingService.getBookingById(bookingId);
        assertEquals(expectedStatus, getResponse.getStatusCode());
    }
/*
    @Then("the system should return {int} forbidden")
    public void validate_forbidden_status(int expectedStatus) {
        Response deleteResponse = testContext.getDeleteResponse();
        assertEquals(expectedStatus, deleteResponse.getStatusCode());
    }
*/
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode.intValue(), testContext.getResponse().getStatusCode());
    }

    @Then("the response body should indicate booking not found")
    public void the_response_body_should_indicate_booking_not_found() {
        testContext.getResponse().then().body(containsString("Not Found"));
    }

    @Then("the response body should indicate authorization error")
    public void validate_response_body() {
        String errorMessage = testContext.getResponse().jsonPath().getString("error");
        assertTrue(errorMessage.toLowerCase().contains("unauthorized") ||
                errorMessage.toLowerCase().contains("forbidden"));
    }

    @Then("the deletion should fail with status code {int}")
    public void validate_delete_failure(int expectedStatus) {
        Response deleteResponse = testContext.getDeleteResponse();
        assertNotNull("Delete response is null!", deleteResponse);
        assertEquals(expectedStatus, deleteResponse.getStatusCode());
    }

    @Then("the error message should indicate unauthorized access")
    public void validate_error_message() {
        Response deleteResponse = testContext.getDeleteResponse();
        String message = deleteResponse.jsonPath().getString("error");
        log.info("Delete Response Message {}", message);
    }

}