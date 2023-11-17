package com.example.mystore.network.auth;

import com.example.mystore.prefshared.Manager;
import com.example.mystore.application.HomeApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInter implements Interceptor {
    private final Manager sessionManager = new Manager(HomeApplication.getInstance());

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        String authToken = sessionManager.fetchAuthToken();
        if (authToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + authToken);
        }

        return chain.proceed(requestBuilder.build());
    }
}
