package com.example.mystore.servises;
import com.example.mystore.models.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("categories/list")
    Call<List<CategoryModel>> getCategories();
}