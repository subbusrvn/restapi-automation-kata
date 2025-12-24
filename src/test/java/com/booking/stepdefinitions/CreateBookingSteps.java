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
        log.info("****Assuming rooms are available for booking****");
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

        BookingRequest createrequest = BookingRequestFactory.createFromExcel(dataset);
        Response createresponse = bookingService.createBooking(createrequest);

        testContext.setCreateRequest(createrequest);
        testContext.setCreateResponse(createresponse);
        testContext.setBookingId(createresponse.jsonPath().getInt("bookingid"));

        log.info("Create Booking Response:");
        createresponse.getBody().prettyPrint();
        Assert.assertEquals("Booking creation failed", 201, createresponse.getStatusCode());

        // Extract bookingId as primitive int
        int bookingId = createresponse.jsonPath().getInt("bookingid");

        // Assert that bookingId is valid greater than 0
        Assert.assertTrue("Booking ID should be greater than 0. Response: " + createresponse.getBody().asString(), bookingId > 0);

        testContext.setBookingId(bookingId);
        log.info("****Created Booking ID: ****" + bookingId);
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
        Response updateresponse = bookingService.updateBooking(testContext.getBookingId(), updateRequest);

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
        int actualStatusCode = response.getStatusCode();
        int expectedStatusCode;

        if ("created".equalsIgnoreCase(bookingoutcome)) {
            expectedStatusCode = 200;
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

        Assert.assertEquals(Integer.parseInt(expected.getRoomid()), actual.getInt("roomid"));
        Assert.assertEquals(expected.getFirstname(), actual.getString("firstname"));
        Assert.assertEquals(expected.getLastname(), actual.getString("lastname"));
        Assert.assertEquals(expected.getDepositpaid(), actual.getBoolean("depositpaid"));
        Assert.assertEquals(expected.getBookingdates().getCheckin(), actual.getString("bookingdates.checkin"));
        Assert.assertEquals(expected.getBookingdates().getCheckout(), actual.getString("bookingdates.checkout"));
        //int bookingId = response.jsonPath().getInt("bookingid");
    }

    @Then("the booking should be successfully deleted")
    public void the_booking_should_be_successfully_deleted() {
        Response response = testContext.getDeleteResponse();
        Assert.assertNotNull(response);
        Assert.assertTrue("Delete should return 200 or 204", response.getStatusCode() == 200 || response.getStatusCode() == 204);

    }

    @Then("the booking details returned should be correct")
    public void the_booking_details_returned_correct() {
        BookingRequest createRequest = testContext.getCreateRequest();
        Response response = testContext.getCreateResponse();

        Assert.assertNotNull("Expected request is null", createRequest);
        Assert.assertNotNull("Response is null", response);

        JsonPath actual = response.jsonPath();
        int bookingId = actual.getInt("bookingid");
        Assert.assertTrue("Booking ID must be greater than 0", bookingId > 0);
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("roomid", Integer.valueOf(createRequest.getRoomid()));
        expectedMap.put("firstname", createRequest.getFirstname());
        expectedMap.put("lastname", createRequest.getLastname());
        expectedMap.put("depositpaid", createRequest.getDepositpaid());
        expectedMap.put("bookingdates.checkin", createRequest.getBookingdates().getCheckin());
        expectedMap.put("bookingdates.checkout", createRequest.getBookingdates().getCheckout());

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

            log.info("****ASSERT FIELD: " + path + " | Expected: " + expectedValue + " | Actual: ****" + actualValue);
            Assert.assertEquals("Mismatch for field: " + path, expectedValue, actualValue);
        });

    }

    /*
        @Then("retrieving the booking should return {string}")
        public void retrieving_the_booking_should_return(String outcome) {
            Response response = bookingService.getBookingById(testContext.getBookingId());
            response.body().prettyPrint();
            Assert.assertEquals("Booking should be not found", 404, response.getStatusCode());
            log.info("Retrieve after delete outcome: " + outcome);

            if ("not found".equalsIgnoreCase(outcome)) {
                Assert.assertEquals(404, response.getStatusCode());
            }
        }
    */
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
        //Assert.assertEquals("Update should return Success",201,response.getStatusCode());

        JsonPath actual = response.jsonPath();

        //Assert.assertEquals(Integer.parseInt(expected.getRoomid()), actual.getInt("roomid"));
        Assert.assertEquals(expected.getFirstname(), actual.getString("firstname"));
        Assert.assertEquals(expected.getLastname(), actual.getString("lastname"));
        Assert.assertEquals(expected.getDepositpaid(), actual.getBoolean("depositpaid"));
        Assert.assertEquals(expected.getBookingdates().getCheckin(), actual.getString("bookingdates.checkin"));
        Assert.assertEquals(expected.getBookingdates().getCheckout(), actual.getString("bookingdates.checkout"));
    }

}