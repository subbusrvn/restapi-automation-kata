package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.utils.LoggerUtil;
import com.booking.utils.excel.factory.BookingRequestFactory;
import com.booking.validators.BookingSwaggerValidator;
import com.booking.validators.CreateBookingResponseValidator;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateBookingSteps {
    private static final Logger log = LoggerUtil.getLogger(CreateBookingSteps.class);
    private final TestContext testContext;
    private final BookingService bookingService;

    public CreateBookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }

    // -------------------------
    // Given Steps
    // -------------------------
    @Given("rooms are available for booking")
    public void rooms_are_available_for_booking() {
        log.info("****Rooms are available for booking****");
    }

    /*-------------------------
     * When Steps
     *-------------------------
     */
    @When("a guest tries to book a room with {string}")
    public void submit_booking_request(String dataset) {

        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);
        testContext.setCreateRequest(request);

       Response response = bookingService.createBooking(request);
        testContext.setCreateResponse(response);
    }
/*
    @When("the booking reservation confirmed {string}")
    public void submit_booking_reseration(String dataset) {

        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);
        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);
    }
*/
    /*-------------------------
     * Then Steps
     *-------------------------
     */

    @Then("the booking request should be {string}")
    public void booking_should_be(String bookingoutcome) {
        Response response = testContext.getCreateResponse();
        CreateBookingResponseValidator.validateCreateMsg(response, bookingoutcome,testContext);
    }

    @Then("the booking details should be as per schema")
    public void the_booking_details_Schema_returned_correctly() {
        Response response = testContext.getCreateResponse();
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/booking_post_schema.json"));
    }

    @Then("the response should follow the Swagger booking contract")
    public void validate_swagger_contract() {
        Response response = testContext.getCreateResponse();
        BookingSwaggerValidator.validateCreateBookingSwaggerContract(response);
    }

}