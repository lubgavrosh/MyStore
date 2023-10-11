package com.example.mystore;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.mystore.application.HomeApplication;
import com.example.mystore.models.CategoryModel;
import com.example.mystore.servises.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {

    private Button openChromeButton;
    private static final String BASE_URL = "https://spu123.itstep.click/api/";
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ImageView ivLogo =findViewById(R.id.ivLogo);
      //  String url= "https://spu123.itstep.click/images/1.jpg";
      //  Glide.with(HomeApplication.getAppContext()).load(url).into(ivLogo);


        // Ініціалізація Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Виконання запиту до сервера
        Call<List<CategoryModel>> call = apiService.getCategories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful()) {
                    List<CategoryModel> categories = response.body();
                    for (CategoryModel category : categories) {
                        System.out.println("Категорія: " + category.getName());
                        System.out.println("Опис: " + category.getDescription());
                        System.out.println("Фотографія: " + category.getImage());
                    }
                } else {
                    System.err.println("Помилка запиту до сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                System.err.println("Помилка: " + t.getMessage());
            }
        });
    }
}