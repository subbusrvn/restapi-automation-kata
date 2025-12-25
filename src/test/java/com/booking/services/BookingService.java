package com.booking.services;

import com.booking.client.RestClient;
import com.booking.models.booking.BookingRequest;

import io.restassured.response.Response;

import static com.booking.endpoints.BookingEndpoints.*;

public class BookingService {
    private final RestClient restClient;

    public BookingService() {
        this.restClient = new RestClient();
    }

    public Response createBooking(BookingRequest bookingRequest) {

        return restClient.post(BOOKING_ENDPOINT, bookingRequest);
    }

    public Response getBookingById(int bookingId) {
        return restClient.get(
                BOOKING_GET.replace("{id}", String.valueOf(bookingId)));

    }

    public Response updateBookingPatch(int bookingId, BookingRequest request) {
        return RestClient.patch(
                BOOKING_PATCH.replace("{id}", String.valueOf(bookingId)),
                request
        );
    }

    public Response updateBookingPut(int bookingId, BookingRequest request) {
        return RestClient.put(
                BOOKING_PATCH.replace("{id}", String.valueOf(bookingId)),
                request
        );
    }

    public Response deleteBooking(int bookingId) {
        return restClient.delete(
                BOOKING_DELETE.replace("{id}", String.valueOf(bookingId)));
    }

}
