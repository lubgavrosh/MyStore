package com.example.mystore.network;

import com.example.mystore.network.auth.ApiAuth;
import com.example.mystore.network.category.CategoriesApi;

public class ApiClient {
    private static final String apiUrl = "https://lubgavrosh/";

    public static CategoriesApi getCategoryService() {
        return RetrofitClient.getClient(apiUrl).create(CategoriesApi.class);
    }

    public static ApiAuth  getAuthService() {
        return RetrofitClient.getClient(apiUrl).create(ApiAuth.class);
    }
}