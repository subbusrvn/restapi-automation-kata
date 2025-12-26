package com.booking.utils;

public class SafeParser {

    public static Integer toInteger(String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Boolean toBoolean(String value) {
        if (value == null || value.isEmpty()) return null;
        return Boolean.parseBoolean(value);
    }

    public static String toNullIfEmpty(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }
}

