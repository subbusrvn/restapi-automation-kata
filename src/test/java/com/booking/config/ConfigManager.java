package com.booking.config;


import java.io.FileInputStream;
import java.util.Properties;

public class ConfigManager {

    public static Properties properties = new Properties();

    static {
        try {
            //FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            //properties.load(fis);
            properties.load(ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"));

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
