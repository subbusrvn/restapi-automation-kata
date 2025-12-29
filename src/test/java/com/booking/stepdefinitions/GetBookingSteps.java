package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.services.BookingService;
import com.booking.utils.LoggerUtil;
import com.booking.utils.TokenManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class GetBookingSteps {
    private static final Logger log = LoggerUtil.getLogger(CreateBookingSteps.class);
    private final TestContext testContext;
    private final BookingService bookingService;

    public GetBookingSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookingService = new BookingService();
    }

    @Given("Set the invalid authentication token to the created bookingid")
    public void i_use_an_invalid_authentication_token() {
        String invalidToken = "INVALID_TOKEN_12222345";

        testContext.setToken(invalidToken);
        testContext.setUseInvalidToken(true);
        TokenManager.setToken(invalidToken);
        log.info("************After set the token to Null******{}", invalidToken);
    }

    @Given("Update the invalid authentication token to the created bookingid")
    public void set_NonExisting_BookingIdGET() {
        String nonExistinBookingID = "9999999";

        testContext.setToken(nonExistinBookingID);
        testContext.setUseInvalidToken(true);
        TokenManager.setToken(nonExistinBookingID);
        log.info("************After set the token to Null******{}", nonExistinBookingID);
    }
/*
    @When("the guest retrieves the booking by booked id")
    public void the_guest_retrieves_the_booking_by_id() {
        testContext.setResponse(testContext.getCreateResponse());
        log.info("*****Updated Booking Id is retrived*****{}", testContext.getBookingId());
    }

    @Then("the system Will not show the booking dettails, Since the user has no authorization")
    public void the_system_will_not_show_booking_details() {
        testContext.getBookingId();
        Assert.assertNotNull("Booking Id is not null",testContext.getBookingId());
        log.info("*****Updated Booking Id is retrived*****{}", testContext.getBookingId());
    }

 */
}
