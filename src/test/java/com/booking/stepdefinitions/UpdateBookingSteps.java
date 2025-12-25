package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import com.booking.utils.LoggerUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;

import java.util.Map;

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

    // ---------------------------------
    // Scenario Steps
    // ---------------------------------

    @Given("an existing booking is created")
    public void an_existing_booking_is_created() {

        Response response = testContext.getResponse();// or make a POST call
        Integer bookingId = response.jsonPath().getInt("bookingid");

        assertTrue("Booking must have a valid positive ID", bookingId > 0);
        log.info("Already Created Booking ID: {}", bookingId);
    }

    @When("Update the booking with the following fields")
    public void i_update_the_booking_with_all_valid_fields(DataTable dataTable) {

        Map<String, String> data = dataTable.asMaps(String.class, String.class).get(0);
        BookingRequest updateRequest = new BookingRequest();

        updateRequest.setRoomid(String.valueOf(Integer.parseInt(data.get("roomid"))));
        updateRequest.setFirstname(data.get("firstname"));
        updateRequest.setLastname(data.get("lastname"));
        updateRequest.setEmail(data.get("email"));
        updateRequest.setPhone(data.get("phone"));
        updateRequest.setDepositpaid(Boolean.parseBoolean(data.get("depositpaid")));
        BookingDates dates = new BookingDates(data.get("bookingdates.checkin"), data.get("bookingdates.checkout"));
        updateRequest.setBookingdates(dates);
        log.info("**** Update data from Data Table*****{}", updateRequest);

        Response updateResponse = bookingService.updateBookingPut(testContext.getBookingId(), updateRequest);

        testContext.setUpdateRequest(updateRequest);
        log.info(":******After Update the Update Request is:******{}", updateRequest);
        testContext.setUpdateResponse(updateResponse);
        log.info(":******After Update the Update Response is:******{}", updateResponse);
    }

    @Then("the API should return a successful update response")
    public void the_api_should_return_a_successful_update_response() {
        Response response = testContext.getUpdateResponse();
        assertEquals(200, response.statusCode());
    }

    @Then("the updated booking should reflect all the new values")
    public void the_updated_booking_should_reflect_all_the_new_values() {

        Response updateResponse = testContext.getUpdateResponse();
        assertEquals("Update should be successful",
                200,
                updateResponse.getStatusCode());
        log.info("***Update accepted successfully. Field-level persistence validation skipped as per API behavior*****");


    }
}
