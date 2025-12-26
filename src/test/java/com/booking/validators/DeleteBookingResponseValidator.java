package com.booking.validators;

import io.restassured.response.Response;
import org.junit.Assert;

public final class DeleteBookingResponseValidator {

    private DeleteBookingResponseValidator() {
        // prevent instantiation
    }

    public static void validate(Response response) {
        Assert.assertNotNull("Delete response is null", response);

        int statusCode = response.getStatusCode();
        Assert.assertTrue(
                "Delete should return 200 or 204 but was " + statusCode,
                statusCode == 200 || statusCode == 204
        );
    }
}
