package com.booking.hooks;



import com.booking.config.ConfigManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;

public class Hooks {

    @Before
    public void beforeScenario() {
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(
                        ObjectMapperConfig.objectMapperConfig()
                                .defaultObjectMapperType(ObjectMapperType.JACKSON_2)
                );

        RestAssured.baseURI =
                ConfigManager.getProperty("base_url");
    }

    @After
    public void afterScenario() {
        RestAssured.reset();
    }
}
