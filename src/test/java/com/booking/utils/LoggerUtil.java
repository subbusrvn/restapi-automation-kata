package com.booking.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private LoggerUtil() {
        // prevent instantiation
    }
    public static Logger getLogger(Class<?> clazz) {

        return LogManager.getLogger(clazz);
    }
}
