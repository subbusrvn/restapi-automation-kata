package com.booking.hooks;


import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.booking.logger.ChainTestLogger; // your custom logger

public class ChainTestHooks {

    @Before
    public void beforeScenario(Scenario scenario) {

        ChainTestLogger.startScenario(scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ChainTestLogger.log("❌ Scenario Failed: " + scenario.getName());
        } else {
            ChainTestLogger.log("✅ Scenario Passed");
        }
    }

    @AfterAll
    public static void afterAllScenarios() {

    }
}
