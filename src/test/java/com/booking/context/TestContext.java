package com.booking.context;

import com.booking.config.ConfigManager;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private Response response;
    public static final String baseUri = ConfigManager.getProperty("base_url");

    public Response getResponse() {

        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    private Map<String, Object> scenarioData = new HashMap<>();

    public void set(String key, Object value) {
        scenarioData.put(key, value);
    }
    public <T> T get(String key) {
        return (T) scenarioData.get(key);
    }


    public String getBaseUri() {

        return baseUri;
    }
}


