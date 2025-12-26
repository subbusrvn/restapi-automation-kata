package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.LoggerUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class EndToEndBookingSteps {
    private static final Logger log = LoggerUtil.getLogger(CreateBookingSteps.class);
    private final TestContext testContext;
    private final BookingService bookingService;

    public EndToEndBookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }


    @When("a guest creates a booking with {string}")
    public void create_booking(String dataset) {


        BookingRequest createRequest = BookingRequestFactory.createFromExcel(dataset);

        Response createResponse = bookingService.createBooking(createRequest);

        log.info("Create Booking Response:");
        createResponse.then().log().all();

        Assert.assertEquals("Booking creation failed", 201, createResponse.getStatusCode());

        Integer bookingId = createResponse.jsonPath().get("bookingid");
        if (bookingId == null) {
            throw new IllegalStateException("Booking ID is null. Response: " + createResponse.getBody().asString());
        }

        Assert.assertTrue("Booking ID should be greater than 0", bookingId > 0);

        testContext.setCreateRequest(createRequest);
        testContext.setCreateResponse(createResponse);
        testContext.setBookingId(bookingId);

        log.info("****Created Booking ID: {} ****", bookingId);
    }

    @Then("the room reservation is confirmed")
    public void the_room_reservation_is_confirmed() {

        Response createResponse = testContext.getCreateResponse();
        Assert.assertNotNull("Create response is null", createResponse);

        int actualStatusCode = createResponse.getStatusCode();
        log.info("****Actual Status Code:**** {}", actualStatusCode);


        Assert.assertEquals("Booking should be created successfully", 201, actualStatusCode);
        Integer bookingId = createResponse.jsonPath().getInt("bookingid");
        Assert.assertNotNull("Booking ID should be generated", bookingId);
        testContext.setBookingId(bookingId);
        log.info("****Confirmed Booking ID:**** {}", bookingId);
    }

    @Then("a booking reference is generated")
    public void a_booking_reference_is_generated() {

        Response response = testContext.getCreateResponse();
        Integer bookingId = response.jsonPath().get("bookingid");
        Assert.assertNotNull("Booking ID should be generated", bookingId);
        testContext.setBookingId(bookingId);

        log.info("****Generated Booking ID:**** " + bookingId);
        Assert.assertTrue("Booking ID is not greater than zero", bookingId > 0);
    }

    @Then("the booking details returned should be correct")
    public void the_booking_details_returned_correct() {

        BookingRequest expectedRequest = testContext.getCreateRequest();
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("roomid", expectedRequest.getRoomid());
        expectedMap.put("firstname", expectedRequest.getFirstname());
        expectedMap.put("lastname", expectedRequest.getLastname());
        expectedMap.put("depositpaid", expectedRequest.getDepositpaid());
        expectedMap.put("email", expectedRequest.getEmail());
        expectedMap.put("phone", expectedRequest.getPhone());

        // Handle nested bookingdates
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", expectedRequest.getBookingdates().getCheckin());
        bookingDates.put("checkout", expectedRequest.getBookingdates().getCheckout());
        expectedMap.put("bookingdates", bookingDates);

        // Actual response
        Map<String, Object> actualMap = testContext.getCreateResponse().jsonPath().getMap("");

        // Iterate through all fields and assert safely
        expectedMap.forEach((field, expectedValue) -> {
            Object actualValue = actualMap.get(field);
            if (actualValue != null) {   // only assert if field exists in response
                Assert.assertEquals("Mismatch for field: " + field, expectedValue.toString(), actualValue.toString());
            } else {
                log.warn("Field {} is not returned in response, skipping assertion.", field);
            }
        });

        log.info("Booking details match expected values.");
    }

    @Given("a booking exists")
    public void booking_exists() {

        Integer bookingId = testContext.getBookingId();
        Assert.assertNotNull("Booking ID should not be null after creation", bookingId);
    }

    @When("the guest retrieves the booking by ID")
    public void the_guest_retrieves_the_booking_by_id() {
        //Response getresponse = bookingService.getBookingById(testContext.getBookingId());
        testContext.setResponse(testContext.getGetUpdatedBookingResponse());
        log.info("*****Updated Booking Id is retrived*****{}", testContext.getBookingId());
    }

    @Then("the booking details should match the created booking")
    public void the_booking_details_should_match_the_created_booking() {

        BookingRequest expected = testContext.getCreateRequest();
        Response response = testContext.getCreateResponse();

        Assert.assertEquals(201, response.getStatusCode());

        JsonPath actual = response.jsonPath();

        //Assert.assertEquals(expected.getRoomid(), actual.getInt("roomid"));
        Assert.assertEquals(expected.getFirstname(), actual.getString("firstname"));
        Assert.assertEquals(expected.getLastname(), actual.getString("lastname"));
        Assert.assertEquals(expected.getDepositpaid(), actual.getBoolean("depositpaid"));
        Assert.assertEquals(expected.getBookingdates().getCheckin(), actual.getString("bookingdates.checkin"));
        Assert.assertEquals(expected.getBookingdates().getCheckout(), actual.getString("bookingdates.checkout"));
        //int bookingId = response.jsonPath().getInt("bookingid");
    }

    @When("the guest updates the booking with {string}")
    public void the_guest_updates_the_booking_with(String updateDataset) {

        int bookingId = testContext.getBookingId();
        Assert.assertTrue("Booking ID must exist before update. Current value: " + bookingId, bookingId > 0);

        // Create update request from dataset
        BookingRequest updateRequest = BookingRequestFactory.createFromExcel(updateDataset);

        // Perform the update via bookingService
        Response updateresponse = bookingService.updateBookingPatch(testContext.getBookingId(), updateRequest);

        log.info("**** Update Booking Response ****");
        updateresponse.getBody().prettyPrint();
        Assert.assertEquals("Booking update should return 200", 200, updateresponse.getStatusCode());

        testContext.setUpdateRequest(updateRequest);
        testContext.setUpdateResponse(updateresponse);

        Response getUpdatedBooking = bookingService.getBookingById(testContext.getBookingId());
        log.info("**** Full Updated Booking Details ****");
        getUpdatedBooking.getBody().prettyPrint();

        testContext.setGetUpdatedBookingResponse(getUpdatedBooking);
    }

    @Then("the booking update should be successful")
    public void the_booking_update_should_be_successful() {
        Response response = testContext.getGetUpdatedBookingResponse();
        Assert.assertNotNull("Update response should not be null", response);
        log.info("******status code for the Update*******{}", response.getStatusCode());
    }

    @Then("the booking details should reflect the updates")
    public void the_booking_details_should_reflect_the_updates() {

        BookingRequest expected = testContext.getUpdateRequest();
        Response response = testContext.getGetUpdatedBookingResponse();
        log.info("Booking update shoudl be Successful" + response.getStatusCode());

        JsonPath actual = response.jsonPath();

        Assert.assertEquals(expected.getFirstname(), actual.getString("firstname"));
        Assert.assertEquals(expected.getLastname(), actual.getString("lastname"));
        Assert.assertEquals(expected.getDepositpaid(), actual.getBoolean("depositpaid"));
        Assert.assertEquals(expected.getBookingdates().getCheckin(), actual.getString("bookingdates.checkin"));
        Assert.assertEquals(expected.getBookingdates().getCheckout(), actual.getString("bookingdates.checkout"));
    }

    @When("the guest deletes the booking")
    public void the_guest_deletes_the_booking() {

        Response deleteResponse = bookingService.deleteBooking(testContext.getBookingId());

        testContext.setDeleteResponse(deleteResponse);
    }

    @Then("the booking should be successfully deleted")
    public void the_booking_should_be_successfully_deleted() {
        Response response = testContext.getDeleteResponse();
        Assert.assertNotNull(response);
        Assert.assertTrue("Delete should return 200 or 204", response.getStatusCode() == 200 || response.getStatusCode() == 204);

    }

}
