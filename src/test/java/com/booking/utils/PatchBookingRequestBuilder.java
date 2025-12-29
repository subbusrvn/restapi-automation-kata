package com.booking.utils;

import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;

public class PatchBookingRequestBuilder {

    private PatchBookingRequestBuilder() {
        // utility class
    }
          public static BookingRequest build(
                String firstname,
                String lastname,
                Boolean depositpaid,
                String email,
                String phone,
                Integer roomid,
                BookingDates bookingDates
        ) {
            BookingRequest request = new BookingRequest();

            request.setFirstname(firstname);
            request.setLastname(lastname);
            request.setDepositpaid(depositpaid);
            request.setEmail(email);
            request.setPhone(phone);
            request.setRoomid(roomid);
            request.setBookingdates(bookingDates);

            return request;
        }
    }