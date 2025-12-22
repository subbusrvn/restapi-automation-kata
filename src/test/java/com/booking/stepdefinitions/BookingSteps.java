package com.booking.stepdefinitions;

import com.booking.context.TestContext;
import com.booking.utils.BookingRequestFactory;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import com.booking.models.booking.BookingRequest;
import com.booking.services.BookingService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;


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

        BookingRequest request =
                BookingRequestFactory.createFromExcel(dataset);

        Response response = bookingService.createBooking(request);
        testContext.setResponse(response);
    }

    @Then("the booking request should be {string}")
    public void booking_should_be(String bookingoutcome) {

        {

            Response response = testContext.getResponse();
            int actualStatusCode = response.getStatusCode();
            int expectedStatusCode;

            if ("created".equalsIgnoreCase(bookingoutcome)) {
                expectedStatusCode = 201;
                System.out.println("Expected Status Code: " + expectedStatusCode +
                        ", Actual Status Code: " + actualStatusCode);
                Assert.assertEquals(expectedStatusCode, actualStatusCode);
                Assert.assertNotNull(response.jsonPath().get("bookingid"));
            } else {
                expectedStatusCode = 400;
                System.out.println("Expected Status Code: " + expectedStatusCode +
                        ", Actual Status Code: " + actualStatusCode);
                Assert.assertEquals(expectedStatusCode, actualStatusCode);
                System.out.println("Response Body:\n" + response.getBody().prettyPrint());
            }
        }

    }
}