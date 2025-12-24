package com.booking.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.booking.stepdefinitions", "com.booking.hooks", "com.booking.context",},

        plugin = {
                "pretty",
                "html:target/cucumber-report.html"
        },
        monochrome = true
        )
public class TestRunner {
}
