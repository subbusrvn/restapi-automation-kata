package com.booking.logger;

import java.time.LocalDateTime;

public class ChainTestLogger {

    public static void startScenario(String name) {
        System.out.println("────────── START SCENARIO: " + name + " ──────────");
        System.out.println("START TIME: " + LocalDateTime.now());
    }

    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }

    public static void endScenario() {
        System.out.println("────────── END SCENARIO ──────────");
        System.out.println();
    }
}
