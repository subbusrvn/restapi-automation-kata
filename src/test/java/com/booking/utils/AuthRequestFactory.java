package com.booking.utils;

import com.booking.config.ConfigManager;
import com.booking.models.auth.AuthRequest;

import java.util.Map;

/**
 * Pure factory: maps userType -> AuthRequest
 * No branching inside -> CC=1
 */
public class AuthRequestFactory {

    private static final Map<String, String[]> CREDENTIALS_MAP = Map.ofEntries(
            Map.entry("valid", new String[]{"valid.username", "valid.password"}),
            Map.entry("invalidUser", new String[]{"invalid.username", "valid.password"}),
            Map.entry("invalidPassword", new String[]{"valid.username", "invalid.password"}),
            Map.entry("emptyUsername", new String[]{"empty.username", "valid.password"}),
            Map.entry("emptyPassword", new String[]{"valid.username", "empty.password"}),
            Map.entry("missingRequest", new String[]{"", ""}),
            Map.entry("sqlinjUsername", new String[]{"sql.injection.username", "valid.password"}),
            Map.entry("sqlinjPassword", new String[]{"valid.username", "sql.injection.password"}),
            Map.entry("caseSenUsername", new String[]{"caseSenUsername", "valid.password"}),
            Map.entry("caseSenPassword", new String[]{"valid.username", "caseSenPassword"}),
            Map.entry("splcharUsername", new String[]{"splcharUsername", "valid.password"}),
            Map.entry("splcharPassword", new String[]{"valid.username", "splcharUsername"})
    );

    private AuthRequestFactory() {}

    /**
     * Pure mapping: assumes caller has valid userType and handles empty keys
     */
    public static AuthRequest build(String userType) {
        String[] keys = CREDENTIALS_MAP.get(userType);  // no if/ternary
        return new AuthRequest(
                ConfigManager.getProperty(keys[0]),
                ConfigManager.getProperty(keys[1])
        );
    }
}
