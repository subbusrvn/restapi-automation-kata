package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.utils.BookingRequestFactory;
import com.booking.utils.LoggerUtil;
import com.booking.utils.TokenManager;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;



public class BookingSteps {

    private static final Logger log =
            LoggerUtil.getLogger(BookingSteps.class);
    private final TestContext testContext;
    private final BookingService bookingService;
    private int bookingId;
    public BookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }

    @Given("rooms are available for booking")
    public void rooms_are_available_for_booking() {
        log.info("****Assuming rooms are available for booking****");
    }

    @When("a guest tries to book a room with {string}")
    public void submit_booking_request(String dataset) {

        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);
        // Save request in TestContext for later assertions
        testContext.setBookingRequest(request);

        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);
    }

    @When("a guest creates a booking with {string}")
    public void create_booking(String dataset) {

        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);
        testContext.setBookingRequest(request);

        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);



        log.info("****Create Booking Response:****");
        response.getBody().prettyPrint();
        Assert.assertEquals(
                "Booking creation failed",
                201,
                response.getStatusCode()
        );

        Integer bookingId = response.jsonPath().getInt("bookingid");

        if (bookingId == null) {
            throw new RuntimeException(
                    "Booking ID is null. Response: " + response.getBody().asString()
            );
        }

        testContext.setBookingId(bookingId);
        log.info("****Created Booking ID: ****" + bookingId);
    }

    @When("the guest retrieves the booking by ID")
    public void the_guest_retrieves_the_booking_by_id() {
        Response response = bookingService.getBookingById(testContext.getBookingId());
        testContext.setResponse(response);
    }

    @When("the guest deletes the booking")
    public void the_guest_deletes_the_booking() {

        Response response = bookingService.deleteBooking(testContext.getBookingId(), TokenManager.getToken());
        testContext.setResponse(response);
    }


    @Given("a booking exists")
    public void booking_exists() {
        Assert.assertNotNull("Booking ID must exist", testContext.getBookingId());
    }



    @Then("the booking request should be {string}")
    public void booking_should_be(String bookingoutcome) {

        Response response = testContext.getResponse();
        int actualStatusCode = response.getStatusCode();
        int expectedStatusCode;

        if ("created".equalsIgnoreCase(bookingoutcome)) {
            expectedStatusCode = 201;
            log.info("****Expected Status Code:**** " + expectedStatusCode + ", ****Actual Status Code:**** " + actualStatusCode);
            Assert.assertEquals(expectedStatusCode, actualStatusCode);
            Assert.assertNotNull(response.jsonPath().getInt("bookingid"));
        } else {
            expectedStatusCode = 400;
            log.info("****Expected Status Code:**** " + expectedStatusCode + ", ****Actual Status Code:**** " + actualStatusCode);
            Assert.assertEquals(expectedStatusCode, actualStatusCode);
            log.info("****Response Body:****\n" + response.getBody().prettyPrint());
        }
    }

    @Then("the room reservation is confirmed")
    public void the_room_reservation_is_confirmed() {
        Response response = testContext.getResponse();
        int actualStatusCode = response.getStatusCode();
log.info("*****Actual Status Code: *****" + actualStatusCode);

        Assert.assertEquals("Booking should be created successfully", 201, actualStatusCode);

        Integer bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertNotNull("Booking ID should be generated", bookingId);
        Assert.assertTrue("Booking ID should be > 0", bookingId > 0);
        testContext.setBookingId(bookingId);
    }

    @Then("a booking reference is generated")
    public void a_booking_reference_is_generated() {
        Response response = testContext.getResponse();
        Integer bookingId = response.jsonPath().getInt("bookingid");
        log.info("*****Generated Booking ID:*****" + bookingId);

        Assert.assertNotNull("Booking ID is null", bookingId);
        Assert.assertTrue("Booking ID is not greater than zero", bookingId > 0);
    }
    @Then("the booking details are returned correctly")
    public void the_booking_details_Schema_returned_correctly() {
        Response response = testContext.getResponse();
    }

    @Then("the booking details should match the created booking")
    public void the_booking_details_should_match_the_created_booking() {

        BookingRequest expected = testContext.getBookingRequest();
        Response response = testContext.getResponse();

        Assert.assertEquals(200, response.getStatusCode());

        JsonPath actual = response.jsonPath();

        Assert.assertEquals(Integer.parseInt(expected.getRoomid()), actual.getInt("roomid"));
        Assert.assertEquals(expected.getFirstname(), actual.getString("firstname"));
        Assert.assertEquals(expected.getLastname(), actual.getString("lastname"));
        Assert.assertEquals(expected.getDepositpaid(), actual.getBoolean("depositpaid"));
        Assert.assertEquals(expected.getBookingdates().getCheckin(), actual.getString("bookingdates.checkin"));
        Assert.assertEquals(expected.getBookingdates().getCheckout(), actual.getString("bookingdates.checkout"));
        bookingId = response.jsonPath().getInt("bookingid");
    }

    @Then("the booking should be successfully deleted")
    public void the_booking_should_be_successfully_deleted() {

        Response response = testContext.getResponse();
        Assert.assertTrue("Delete booking failed", response.getStatusCode() == 200 || response.getStatusCode() == 204);

    }

    @Then("the booking details returned should be correct")
    public void the_booking_details_returned_correct() {

        BookingRequest expected = testContext.getBookingRequest();
        Response response = testContext.getResponse();

        Assert.assertNotNull("Expected request is null", expected);
        Assert.assertNotNull("Response is null", response);

        JsonPath actual = response.jsonPath();

        Integer bookingId = actual.getInt("bookingid");
        Assert.assertNotNull("Booking ID is null", bookingId);
        Assert.assertTrue("Booking ID should be > 0", bookingId > 0);

        // Map expected values for assertion
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("roomid", Integer.valueOf(expected.getRoomid()));
        expectedMap.put("firstname", expected.getFirstname());
        expectedMap.put("lastname", expected.getLastname());
        expectedMap.put("depositpaid", expected.getDepositpaid());
        expectedMap.put("bookingdates.checkin", expected.getBookingdates().getCheckin());
        expectedMap.put("bookingdates.checkout", expected.getBookingdates().getCheckout());

        // Iterate and assert each field
        expectedMap.forEach((path, expectedValue) -> {
            Object actualValue;
            if (path.contains(".")) {
                // handle nested fields
                String[] keys = path.split("\\.");
                actualValue = actual.get(keys[0] + "." + keys[1]);
            } else {
                actualValue = actual.get(path);
            }

            log.info("ASSERT FIELD: " + path +
                    " | Expected: " + expectedValue +
                    " | Actual: " + actualValue);

            Assert.assertEquals("Mismatch for field: " + path, expectedValue, actualValue);
        });
    }

    @Then("retrieving the booking should return {string}")
    public void retrieving_the_booking_should_return(String outcome) {
        Response response = bookingService.getBookingById(testContext.getBookingId());
        Assert.assertEquals("Booking should be not found", 404, response.getStatusCode());
        log.info("Retrieve after delete outcome: " + outcome);

        if ("not found".equalsIgnoreCase(outcome)) {
            Assert.assertEquals(404, response.getStatusCode());
        }
    }

}
