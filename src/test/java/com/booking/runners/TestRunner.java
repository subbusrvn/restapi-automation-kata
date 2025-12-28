package com.booking.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.booking.stepdefinitions", "com.booking.hooks", "com.booking.context",},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "com.aventstack.chaintest.plugins.ChainTestCucumberListener:"
        },
        //tags = "@E2E",
        monochrome = true
)
public class TestRunner {
}
