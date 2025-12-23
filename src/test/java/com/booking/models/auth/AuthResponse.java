package com.booking.models.auth;
@SuppressWarnings("unused")
public class AuthResponse {
    private String token;

    // Default constructor
    public AuthResponse() {

    }

    // Getter and Setter

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthResponse{token='" + token + "'}";
    }
}
