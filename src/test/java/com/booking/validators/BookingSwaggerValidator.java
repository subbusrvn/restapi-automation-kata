package com.booking.validators;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

public final class BookingSwaggerValidator {

    private BookingSwaggerValidator() {}

    public static void validateCreateBookingSwaggerContract(Response response) {

        Assert.assertNotNull("Create booking response is null", response);
        Assert.assertEquals("Invalid status code", 201, response.getStatusCode());

        JsonPath json = response.jsonPath();

        if (json.get("booking") != null) {
            // Swagger-compliant structure
            validateNestedBooking(json);
        } else {
            // Actual API behavior
            validateFlatBooking(json);
        }
    }

    public static void validateNestedBooking(JsonPath json) {
        Assert.assertNotNull("Swagger violation: 'roomid' missing",json.get("booking.roomid"));
        Assert.assertNotNull("Swagger violation: 'firstname' missing",json.get("booking.firstname"));
        Assert.assertNotNull("Swagger violation: 'lastname' missing",json.get("booking.lastname"));
        Assert.assertNotNull("Swagger violation: 'depositpaid' missing",json.get("booking.depositpaid"));
        Assert.assertNotNull("Swagger violation: 'checkin' missing",json.get("booking.bookingdates.checkin"));
        Assert.assertNotNull("Swagger violation: 'checkout' missing",json.get("booking.bookingdates.checkout"));
        Assert.assertNotNull("Swagger violation: 'email' missing",json.get("booking.email"));
        Assert.assertNotNull("Swagger violation: 'phone' missing",json.get("booking.phone"));
    }

    public static void validateFlatBooking(JsonPath json) {
        Assert.assertNotNull("Swagger violation: 'roomid' missing",json.get("roomid"));
        Assert.assertNotNull("Swagger violation: 'firstname' missing",json.get("firstname"));
        Assert.assertNotNull("Swagger violation: 'lastname' missing",json.get("lastname"));
        Assert.assertNotNull("Swagger violation: 'depositpaid' missing",json.get("depositpaid"));
        Assert.assertNotNull("Swagger violation: 'checkin' missing",json.get("bookingdates.checkin"));
        Assert.assertNotNull("Swagger violation: 'checkout' missing",json.get("bookingdates.checkout"));
        Assert.assertNotNull("Swagger violation: 'email' missing",json.get("email"));
        Assert.assertNotNull("Swagger violation: 'phone' missing",json.get("phone"));
    }
}