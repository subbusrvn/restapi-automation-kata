package com.booking.utils;

import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;

public class PutBookingRequestBuilder {

    private PutBookingRequestBuilder() {}

    public static BookingRequest build(
            Integer roomid,
            String firstname,
            String lastname,
            Boolean depositpaid,
            BookingDates bookingDates,
            String email,
            String phone
    ) {
        BookingRequest request = new BookingRequest();

        request.setRoomid(roomid);
        request.setFirstname(firstname);
        request.setLastname(lastname);
        request.setDepositpaid(depositpaid);
        request.setBookingdates(bookingDates);
        request.setEmail(email);
        request.setPhone(phone);

        return request;
    }
}
