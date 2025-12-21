package com.booking.hooks;



import com.booking.config.ConfigManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.restassured.RestAssured;


public class Hooks {

    @Before
    public void beforeScenario() {
        RestAssured.baseURI =
                ConfigManager.getProperty("base_url");
    }

    @After
    public void afterScenario() {
        RestAssured.reset();
    }
}
