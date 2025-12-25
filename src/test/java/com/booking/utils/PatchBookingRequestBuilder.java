package com.booking.utils;

import com.booking.enums.UpdateType;
import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;

import java.util.Map;

public class PatchBookingRequestBuilder {

    private PatchBookingRequestBuilder() {
        // utility class
    }

    public static BookingRequest build(Map<String, String> data, UpdateType type) {

        BookingRequest request = new BookingRequest();

        setString(data, "firstname", request::setFirstname);
        setString(data, "lastname", request::setLastname);
        setBoolean(data, "depositpaid", request::setDepositpaid);
        if (type == UpdateType.PUT) {
            setString(data, "email", request::setEmail);
            setString(data, "phone", request::setPhone);
            setInteger(data, "roomid", request::setRoomid);
            setDates(data, request);
        }

        return request;
    }

    private static void setString(Map<String, String> data,
                                  String key,
                                  java.util.function.Consumer<String> setter) {
        String value = data.get(key);
        if (isValid(value)) {
            setter.accept(value);
        }
    }

    private static void setBoolean(Map<String, String> data,
                                   String key,
                                   java.util.function.Consumer<Boolean> setter) {
        String value = data.get(key);
        if (isValid(value)) {
            setter.accept(Boolean.parseBoolean(value));
        }
    }

    private static void setInteger(Map<String, String> data,
                                   String key,
                                   java.util.function.Consumer<Integer> setter) {
        String value = data.get(key);
        if (isValid(value)) {
            setter.accept(Integer.parseInt(value));
        }
    }

    private static void setDates(Map<String, String> data,
                                 BookingRequest request) {

        String checkin = data.get("bookingdates.checkin");
        String checkout = data.get("bookingdates.checkout");

        if (isValid(checkin) || isValid(checkout)) {
            request.setBookingdates(new BookingDates(checkin, checkout));
        }
    }

    private static boolean isValid(String value) {
        return value != null && !value.isEmpty() && !"[empty]".equalsIgnoreCase(value);
    }
}
