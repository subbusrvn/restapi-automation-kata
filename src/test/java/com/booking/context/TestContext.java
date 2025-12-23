package com.booking.context;

import com.booking.config.ConfigManager;
import com.booking.models.booking.BookingRequest;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private Response response;
    private int bookingId;
    private BookingRequest bookingRequest;
    public static final String baseUri = ConfigManager.getProperty("base_url");

    public BookingRequest getBookingRequest() {
        return bookingRequest;
    }

    public void setBookingRequest(BookingRequest bookingRequest) {
        this.bookingRequest = bookingRequest;
    }

    public Response getResponse() {

        return response;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }


    public void setResponse(Response response) {
        this.response = response;
    }

    private final  Map<String, Object> scenarioData = new HashMap<>();


    public void set(String key, Object value) {
        scenarioData.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) scenarioData.get(key);
    }


    public String getBaseUri() {

        return baseUri;
    }
}


