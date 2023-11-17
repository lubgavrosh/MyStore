package com.example.mystore.dto.user;

public class TokensModel {
    private String accessToken;
    private String refreshToken;

    public TokensModel() {
        // Конструктор за замовчуванням
    }

    public TokensModel(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
