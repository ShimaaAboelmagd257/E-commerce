package com.techie.ecommerce.security;

public class JwtAuthenticationResponse {
    private String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void getAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
