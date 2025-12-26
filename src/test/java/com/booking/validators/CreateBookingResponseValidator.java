package com.booking.validators;

import com.booking.context.TestContext;
import com.booking.utils.BookingIdExtractor;
import io.restassured.response.Response;
import org.junit.Assert;

public class CreateBookingResponseValidator {

    public static void validate(Response response, String outcome, TestContext context) {
        if ("created".equalsIgnoreCase(outcome)) {
            validateCreated(response, context);
        } else {
            validateFailure(response);
        }
    }

    private static void validateCreated(Response response, TestContext context) {
        Assert.assertEquals(201, response.getStatusCode());
        Integer bookingId = BookingIdExtractor.extract(response);
        Assert.assertTrue(bookingId > 0);
        context.setBookingId(bookingId);
    }

    private static void validateFailure(Response response) {
        Assert.assertEquals(400, response.getStatusCode());
    }

}
