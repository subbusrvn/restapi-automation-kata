package com.booking.services;
import com.booking.client.RestClient;
import com.booking.models.booking.BookingRequest;
import com.booking.utils.TokenManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.booking.endpoints.BookingEndpoints.BOOKING_ENDPOINT;
import static io.restassured.RestAssured.given;

public class BookingService {

    public Response createBooking(BookingRequest bookingRequest) {

        return RestClient.post(BOOKING_ENDPOINT, bookingRequest);


    }
}
