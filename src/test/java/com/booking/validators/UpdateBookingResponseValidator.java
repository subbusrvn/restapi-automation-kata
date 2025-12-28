package com.booking.validators;

import com.booking.context.TestContext;
import com.booking.utils.BookingIdExtractor;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateBookingResponseValidator {
    private static final Logger log = LoggerFactory.getLogger(CreateBookingResponseValidator.class);

    public static void validateCreateMsg(Response response, String bookingoutcome, TestContext context) {
        log.info("Validating Create Booking response. Expected outcome: {}", bookingoutcome);
        if ("created".equalsIgnoreCase(bookingoutcome)) {
            validateStatusCode(response, context);
        } else {
            validateFailure(response);
        }
    }

    public static void validateStatusCode(Response response, TestContext context) {
        int actualStatusCode = response.getStatusCode();
        log.info("*****Create Booking Failed*****{}", actualStatusCode);

        //Expected 200
        // In api we are getting 201
        if (actualStatusCode != 201) {
            log.error("*****Create Booking Failed*****");
            log.error("*****Expected Status Code: 200 *****, Actual: {}", actualStatusCode);
            log.error("*****Response Body*****:\n{}", response.asPrettyString());
        }
        Assert.assertEquals(201, response.getStatusCode());
        Integer bookingId = BookingIdExtractor.extract(response);
        Assert.assertTrue("*****Invalid bookingId received*****", bookingId != null && bookingId > 0);
        Assert.assertTrue(bookingId > 0);
        context.setBookingId(bookingId);
        log.info("*****Booking created successfully. BookingId set in context*****: {}", bookingId);

    }

    public static void validateFailure(Response response) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != 400) {
            log.error("*****Expected failure but received different status code*****");
            log.error("*****Expected Status Code: 400*****, Actual: {}", actualStatusCode);
            log.error("*****Response Body*****:\n{}", response.asPrettyString());
        }
        Assert.assertEquals(400, response.getStatusCode());
        log.info("*****Failure response validated successfully with status code 400*****");
    }

    public static void validateUpdateResponse(Response response, TestContext testcontext) {
        Assert.assertNotNull("Update response is null", response);

        int actualStatusCode = response.getStatusCode();
        int bookingID = testcontext.getBookingId();
        log.info("Validating Booking Update response. Status Code: {}", actualStatusCode);

        switch (actualStatusCode) {

            case 200:

                log.info("********Booking Updated successfully********");
                log.info("*****Expected Status Code: 200 *****, Actual: {}", actualStatusCode);
                //Integer bookingId = BookingIdExtractor.extract(response);
                testcontext.setBookingId(bookingID);
                log.info("*****Booking updated successfully. BookingId set in context*****: {}", bookingID);
                break;

            case 404:
                log.info("********Booking ID is not found********");
                Assert.assertEquals("Expected 404 status code", 404, actualStatusCode);
                break;

            case 400:
                Assert.assertEquals(400, response.getStatusCode());
                log.info("*****Failure response validated successfully with status code 400*****", actualStatusCode);
                break;

            case 401:
                Assert.assertEquals(401, response.getStatusCode());
                log.info("*****Unauthorized error during update*****", actualStatusCode);
                break;
            default:
                log.error("********Unexpected delete response********");
                log.error("Status Code: {}", actualStatusCode);
                log.error("Response Body:\n{}", response.asPrettyString());
                Assert.fail("********Unexpected booking update status code:******** " + actualStatusCode);
        }
    }
}
