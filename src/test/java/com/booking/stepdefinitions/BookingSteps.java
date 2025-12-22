package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.utils.BookingRequestFactory;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;



public class BookingSteps {

    private final TestContext testContext;
    private final BookingService bookingService;

    public BookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }

    @Given("rooms are available for booking")
    public void rooms_are_available_for_booking() {
        System.out.println("Assuming rooms are available for booking.");
    }

    @When("a guest tries to book a room with {string}")
    public void submit_booking_request(String dataset) {

        BookingRequest request = BookingRequestFactory.createFromExcel(dataset);

        // Save request in TestContext for later assertions
        testContext.setBookingRequest(request);

        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);
    }

    @Then("the booking request should be {string}")
    public void booking_should_be(String bookingoutcome) {

        Response response = testContext.getResponse();
        int actualStatusCode = response.getStatusCode();
        int expectedStatusCode;

        if ("created".equalsIgnoreCase(bookingoutcome)) {
            expectedStatusCode = 201;
            System.out.println("Expected Status Code: " + expectedStatusCode +
                    ", Actual Status Code: " + actualStatusCode);
            Assert.assertEquals(expectedStatusCode, actualStatusCode);
            Assert.assertNotNull(response.jsonPath().getInt("bookingid"));
        } else {
            expectedStatusCode = 400;
            System.out.println("Expected Status Code: " + expectedStatusCode +
                    ", Actual Status Code: " + actualStatusCode);
            Assert.assertEquals(expectedStatusCode, actualStatusCode);
            System.out.println("Response Body:\n" + response.getBody().prettyPrint());
        }
    }

    @Then("the room reservation is confirmed")
    public void the_room_reservation_is_confirmed() {
        Response response = testContext.getResponse();
        int actualStatusCode = response.getStatusCode();
        System.out.println("Actual Status Code: " + actualStatusCode);

        Assert.assertEquals("Booking should be created successfully", 201, actualStatusCode);

        Integer bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertNotNull("Booking ID should be generated", bookingId);
        Assert.assertTrue("Booking ID should be > 0", bookingId > 0);
    }

    @Then("a booking reference is generated")
    public void a_booking_reference_is_generated() {
        Response response = testContext.getResponse();
        Integer bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Generated Booking ID: " + bookingId);

        Assert.assertNotNull("Booking ID is null", bookingId);
        Assert.assertTrue("Booking ID is not greater than zero", bookingId > 0);
    }
    @Then("the booking details are returned correctly")
    public void the_booking_details_Schema_returned_correctly() {
        Response response = testContext.getResponse();
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
        expectedMap.put("roomid", expected.getRoomid());
        expectedMap.put("firstname", expected.getFirstname());
        expectedMap.put("lastname", expected.getLastname());
        expectedMap.put("depositpaid", expected.getDepositpaid());
        expectedMap.put("email", expected.getEmail());
        expectedMap.put("phone", expected.getPhone());
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

            System.out.println("ASSERT FIELD: " + path +
                    " | Expected: " + expectedValue +
                    " | Actual: " + actualValue);

            Assert.assertEquals("Mismatch for field: " + path, expectedValue, actualValue);
        });
    }
}
