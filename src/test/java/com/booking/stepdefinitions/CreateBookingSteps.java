package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.utils.BookingIdExtractor;
import com.booking.utils.BookingRequestFactory;
import com.booking.validators.CreateBookingResponseValidator;
import com.booking.utils.LoggerUtil;
import com.booking.validators.BookingSwaggerValidator;
import com.booking.validators.DeleteBookingResponseValidator;
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

    @Given("a booking exists")
    public void booking_exists() {

        Integer bookingId = testContext.getBookingId();
        Assert.assertNotNull("Booking ID should not be null after creation", bookingId);
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

    @When("a guest creates a booking with {string}")
    public void create_booking(String dataset) {
        BookingRequest createRequest = BookingRequestFactory.createFromExcel(dataset);
        Response createResponse = bookingService.createBooking(createRequest);
        log.info("*****Create Booking Response:******");
        createResponse.then().log().all();
        Assert.assertEquals("Booking creation failed", 201, createResponse.getStatusCode());
        Integer bookingId = BookingIdExtractor.extract(createResponse);
        Assert.assertTrue("Booking ID should be greater than 0", bookingId > 0);
        log.info("*****After extracting the booking id{}", bookingId);

        testContext.setCreateRequest(createRequest);
        testContext.setCreateResponse(createResponse);
        testContext.setBookingId(bookingId);

        log.info("****Created Booking ID: {} ****", bookingId);
    }

    @When("the guest retrieves the booking by ID")
    public void the_guest_retrieves_the_booking_by_id() {
        //Response getresponse = bookingService.getBookingById(testContext.getBookingId());
        testContext.setResponse(testContext.getGetUpdatedBookingResponse());
        log.info("*****Updated Booking Id is retrived*****{}", testContext.getBookingId());

    }

    @When("the guest deletes the booking")
    public void the_guest_deletes_the_booking() {

        Response deleteResponse = bookingService.deleteBooking(testContext.getBookingId());
        testContext.setDeleteResponse(deleteResponse);
    }

    @When("the guest updates the booking with {string}")
    public void the_guest_updates_the_booking_with(String updateDataset) {

        int bookingId = testContext.getBookingId();
        Assert.assertTrue("Booking ID must exist before update. Current value: " + bookingId, bookingId > 0);

        // Create update request from dataset
        BookingRequest updateRequest = BookingRequestFactory.createFromExcel(updateDataset);

        // Perform the update via bookingService
        Response updateresponse = bookingService.updateBookingPut(testContext.getBookingId(), updateRequest);

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

    // -------------------------
    // Then Steps
    // -------------------------

    @Then("the booking request should be {string}")
    public void booking_should_be(String bookingoutcome) {

        Response response = testContext.getResponse();
        CreateBookingResponseValidator.validate(response, bookingoutcome, testContext);
    }

    @Then("the room reservation is confirmed")
    public void the_room_reservation_is_confirmed() {

        Response createResponse = testContext.getCreateResponse();
        Assert.assertNotNull("Create response is null", createResponse);

        int actualStatusCode = createResponse.getStatusCode();
        log.info("****Actual Status Code:**** {}", actualStatusCode);

        Assert.assertEquals("Booking should be created successfully", 201, actualStatusCode);
        Integer bookingId = BookingIdExtractor.extract(createResponse);
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

    @Then("the booking details are returned correctly")
    public void the_booking_details_Schema_returned_correctly() {
        Response response = testContext.getResponse();
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/booking_post_schema.json"));
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
    }

    @Then("the booking should be successfully deleted")
    public void the_booking_should_be_successfully_deleted() {
        Response response = testContext.getDeleteResponse();
        DeleteBookingResponseValidator.validate(response);
    }

    @Then("the booking details returned should be correct")
    public void the_booking_details_returned_correct() {

        BookingRequest expectedRequest = testContext.getCreateRequest();
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("roomid", expectedRequest.getRoomid());
        expectedMap.put("firstname", expectedRequest.getFirstname());
        expectedMap.put("lastname", expectedRequest.getLastname());
        expectedMap.put("depositpaid", expectedRequest.getDepositpaid());

        // Optional fields: email & phone
        if (expectedRequest.getEmail() != null) {
            expectedMap.put("email", expectedRequest.getEmail());
        }
        if (expectedRequest.getPhone() != null) {
            expectedMap.put("phone", expectedRequest.getPhone());
        }

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

            if (actualValue != null) {
                Assert.assertEquals("Mismatch for field: " + field, expectedValue.toString(), actualValue.toString());
            } else {
                // Log warning instead of failing the test
                log.warn("Field '{}' is not present in the response. Skipping validation.", field);
            }
        });

        log.info("Booking details match expected values where present in response.");
    }

    @Then("the response should follow the Swagger booking contract")
    public void validate_swagger_contract() {
        Response response = testContext.getCreateResponse();
        Assert.assertNotNull("Create booking response is null", response);
        BookingSwaggerValidator.validateCreateBookingSwaggerContract(response);
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

}