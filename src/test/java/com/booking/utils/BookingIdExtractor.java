package com.booking.utils;

import io.restassured.response.Response;
import org.junit.Assert;

public class BookingIdExtractor {
    private BookingIdExtractor() {}

    public static Integer extract(Response response) {
        Integer bookingId = response.jsonPath().get("bookingid");
        Assert.assertNotNull("bookingid missing in response", bookingId);
        Assert.assertTrue("bookingid must be > 0", bookingId > 0);
        return bookingId;
    }
}
