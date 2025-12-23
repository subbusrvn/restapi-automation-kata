package com.booking.config;

import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // Simple getter
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Optional utility
    @SuppressWarnings("unused")
    public static boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}