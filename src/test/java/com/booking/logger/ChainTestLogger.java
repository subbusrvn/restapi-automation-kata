package com.booking.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ChainTestLogger {
    private static final Logger log = LoggerFactory.getLogger(ChainTestLogger.class);


    public static void startScenario(String name) {
        log.info("────────── START SCENARIO: " + name + " ──────────");
        log.info("START TIME: " + LocalDateTime.now());
    }

    public static void log(String message) {
        log.info("[LOG] " + message);
    }

    public static void endScenario() {
        log.info("────────── END SCENARIO ──────────");

    }
}
