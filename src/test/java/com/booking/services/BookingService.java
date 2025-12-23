package com.booking.services;
import com.booking.client.RestClient;
import com.booking.models.booking.BookingRequest;
import com.booking.utils.TokenManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.booking.endpoints.BookingEndpoints.*;
import static io.restassured.RestAssured.given;

public class BookingService {

    public Response createBooking(BookingRequest bookingRequest) {

        return RestClient.post(BOOKING_ENDPOINT, bookingRequest);
    }

    public Response getBookingById(int bookingId) {
        return RestClient.get(
                BOOKING_GET.replace("{id}", String.valueOf(bookingId)));

    }

    public Response updateBooking(int bookingId, BookingRequest request) {
        return RestClient.patch(
                BOOKING_PATCH.replace("{id}", String.valueOf(bookingId)),
                request
        );
    }

    public Response deleteBooking(int bookingId, String token) {
        return RestClient.delete(
                BOOKING_DELETE.replace("{id}", String.valueOf(bookingId)));
    }

}
