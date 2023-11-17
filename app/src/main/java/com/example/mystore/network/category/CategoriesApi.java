package com.example.mystore.network.category;

import com.example.mystore.dto.category.CategoryItemDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CategoriesApi {
    @Multipart
    @POST("/api/categories/create")
    public Call<CategoryItemDTO> create(@Part MultipartBody.Part image,
                                        @Part RequestBody name,
                                        @Part RequestBody description);
    @Multipart
    @PUT("/api/categories/update/{categoryId}")
    Call<CategoryItemDTO> updateCategory(
            @Path("categoryId") int categoryId,
            @Part MultipartBody.Part image,
            @Part RequestBody name,
            @Part RequestBody description
    );


    @GET("/api/categories/list")
    public Call<List<CategoryItemDTO>> list();

    @GET("/api/categories/{categoryId}")
    Call<CategoryItemDTO> getCategoryById(@Path("categoryId") int categoryId);
    @DELETE("/api/categories/delete/{categoryId}")
    public Call<Void> deleteCategory(@Path("categoryId") int categoryId);
}