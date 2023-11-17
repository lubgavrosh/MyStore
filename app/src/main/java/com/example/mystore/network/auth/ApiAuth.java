package com.example.mystore.network.auth;

import com.example.mystore.dto.user.LoginModel;
import com.example.mystore.dto.user.RegisterModel;
import com.example.mystore.dto.user.TokensModel;

import java.util.Map;

import kotlin.Unit;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiAuth{
    @POST("/auth/login")
    Call<TokensModel> login(@Body LoginModel model);

    @POST("/auth/refresh")
    Call<TokensModel> refresh(@Body TokensModel model);

    @Multipart
    @POST("/auth/register")
    Call<Void> register(
            @PartMap Map<String, RequestBody> model,
            @Part MultipartBody.Part image
    );
}
