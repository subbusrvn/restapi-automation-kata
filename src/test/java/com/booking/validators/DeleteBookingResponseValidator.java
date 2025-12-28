package com.booking.validators;

import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DeleteBookingResponseValidator {
    private DeleteBookingResponseValidator() {
        // prevent instantiation
    }

    private static final Logger log =
            LoggerFactory.getLogger(DeleteBookingResponseValidator.class);

    public static void validateDeleteResponse(Response response) {
        Assert.assertNotNull("Delete response is null", response);

        int statusCode = response.getStatusCode();
        log.info("Validating delete booking response. Status Code: {}", statusCode);

        switch (statusCode) {

            case 201:
                log.info("********Booking deleted successfully********");
                Assert.assertEquals("Expected 201 status code", 201, statusCode);
                break;

            case 404:
                log.info("********Booking ID is not found********");
                Assert.assertEquals("Expected 404 status code", 404, statusCode);

                String errorMessage = response.jsonPath().getString("error");
                Assert.assertNotNull("Error message should be present", errorMessage);

                log.info("Error message received: {}", errorMessage);
                break;

            case 401:
                log.error("********Unauthorized delete attempt********");
                Assert.assertTrue(
                        "********Expected Unauthorized error message********",
                        response.asString().contains("Unauthorized"));
                break;

            default:
                log.error("********Unexpected delete response********");
                log.error("Status Code: {}", statusCode);
                log.error("Response Body:\n{}", response.asPrettyString());

                Assert.fail("********Unexpected delete status code:******** " + statusCode);
        }
    }
}
