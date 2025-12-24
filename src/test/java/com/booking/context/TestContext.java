package com.booking.context;

import com.booking.config.ConfigManager;
import com.booking.models.booking.BookingRequest;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private Response getresponse;
    private Response createResponse;
    private Response updateResponse;
    private Response getUpdatedBookingResponse;
    private Response deleteResponse;

    private BookingRequest createRequest;
    private BookingRequest updateRequest;

    private int bookingId;
    private String token;
    public static final String baseUri = ConfigManager.getProperty("base_url");

    //----------------------------------------------------------------------------------
    // Create, update, get Booking response getter and setter
    //----------------------------------------------------------------------------------
    public Response getCreateResponse() {
        return createResponse;
    }

    public void setCreateResponse(Response createResponse) {
        this.createResponse = createResponse;
    }

    public Response getUpdateResponse() {
        return updateResponse;
    }

    public void setUpdateResponse(Response updateResponse) {
        this.updateResponse = updateResponse;
    }

    public Response getResponse() {
        return getresponse;
    }

    public void setResponse(Response response) {
        this.getresponse = response;
    }

    public void setGetUpdatedBookingResponse(Response response) {
        this.getUpdatedBookingResponse = response;
    }

    public Response getGetUpdatedBookingResponse() {
        return getUpdatedBookingResponse;
    }

    public Response getDeleteResponse() {
        return deleteResponse;
    }

    public void setDeleteResponse(Response deleteResponse) {
        this.deleteResponse = deleteResponse;
    }

    //----------------------------------------------------------------------------------
// Create, update, get Booking Request getter and setter
//----------------------------------------------------------------------------------
    public BookingRequest getCreateRequest() {
        return createRequest;
    }

    public void setCreateRequest(BookingRequest bookingRequest) {
        this.createRequest = bookingRequest;
    }

    public BookingRequest getUpdateRequest() {
        return updateRequest;
    }

    public void setUpdateRequest(BookingRequest bookingRequest) {
        this.updateRequest = bookingRequest;
    }

    //----------------------------------------------------------------------------------
//BOOKING ID getter and setter
//----------------------------------------------------------------------------------
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    //----------------------------------------------------------------------------------
//Token getter and setter
//----------------------------------------------------------------------------------
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//----------------------------------------------------------------------------------
//Base URI
//----------------------------------------------------------------------------------

    public String getBaseUri() {

        return baseUri;
    }

    //----------------------------------------------------------------------------------


    private final Map<String, Object> scenarioData = new HashMap<>();


    public void set(String key, Object value) {
        scenarioData.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) scenarioData.get(key);
    }


}


