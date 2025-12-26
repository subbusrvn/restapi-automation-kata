package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.utils.BookingIdExtractor;
import com.booking.utils.BookingRequestFactory;
import com.booking.utils.LoggerUtil;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

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
        log.info("****Assuming rooms are available for booking****");
    }

    // -------------------------
    // When Steps
    // -------------------------
    @When("a guest tries to book a room with {string}")
    public void submit_booking_request(String dataset) {

        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);

        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);
    }

    // -------------------------
    // Then Steps
    // -------------------------

    @Then("the booking request should be {string}")
    public void booking_should_be(String bookingoutcome) {

        Response response = testContext.getResponse();
        int actualStatusCode = response.getStatusCode();
        int expectedStatusCode;

        if ("created".equalsIgnoreCase(bookingoutcome)) {
            expectedStatusCode = 201;
            log.info("****Expected Status Code:**** " + expectedStatusCode + ", ****Actual Status Code:**** " + actualStatusCode);
            log.info("****Booking Accepted****");
            Assert.assertEquals(expectedStatusCode, actualStatusCode);
            Integer bookingId = BookingIdExtractor.extract(response);
            testContext.setBookingId(bookingId);

            Assert.assertTrue("Booking ID must be greater than 0", bookingId > 0);
        } else {
            expectedStatusCode = 400;
            log.info("****Expected Status Code:**** " + expectedStatusCode + ", ****Actual Status Code:**** " + actualStatusCode);
            log.info("****Booking Operation failed****");
            Assert.assertEquals(expectedStatusCode, actualStatusCode);
            log.info("****Response Body:****\n" + response.getBody().prettyPrint());
        }
    }

    @Then("the booking details are returned correctly")
    public void the_booking_details_Schema_returned_correctly() {
        Response response = testContext.getResponse();
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/booking_post_schema.json"));
    }

    @Then("the response should follow the Swagger booking contract")
    public void validate_swagger_contract() {

        Response response = testContext.getResponse();
        Assert.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        // bookingid must exist
        Assert.assertNotNull("bookingid missing", json.get("bookingid"));
        log.info("*****Booking ID must be there:***** " + json.get("bookingid"));
        // Swagger expects nested booking object
        Assert.assertNotNull("Swagger violation: 'booking' object missing", json.get("booking"));
        log.warn("***** Swagger violation: 'booking' object is missing in response *****");

        // Inside booking object
        Assert.assertNotNull(json.get("booking.roomid"));
        Assert.assertNotNull(json.get("booking.firstname"));
        Assert.assertNotNull(json.get("booking.lastname"));
        Assert.assertNotNull(json.get("booking.depositpaid"));
        Assert.assertNotNull(json.get("booking.bookingdates.checkin"));
        Assert.assertNotNull(json.get("booking.bookingdates.checkout"));
        Assert.assertNotNull(json.get("booking.email"));
        Assert.assertNotNull(json.get("booking.phone"));
    }

}