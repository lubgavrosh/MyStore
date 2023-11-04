package com.example.mystore.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.mystore.BaseActivity;
import com.example.mystore.MainActivity;
import com.example.mystore.R;
import com.example.mystore.network.CategoriesApi;
import com.example.mystore.servises.ApplicationNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDeleteActivity extends BaseActivity {
    private int categoryId; // Отримуємо ідентифікатор категорії, яку користувач бажає видалити

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_delete);

        categoryId = getIntent().getIntExtra("categoryId", 0); // Отримуємо ідентифікатор категорії з попередньої активності

        Button deleteButton = findViewById(R.id.btnDeleteCategory);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Додайте код для видалення категорії за допомогою categoryId
                // Видаліть категорію та перейдіть до головного екрану або виконайте інші дії
                // Наприклад, можна видалити категорію зі списку та оновити відображення на головному екрані.
                // Далі перейдіть до головного екрану або виконайте інші дії
                deleteCategory(categoryId);
            }
        });
    }

    private void deleteCategory(int categoryId) {
        CategoriesApi categoriesApi = ApplicationNetwork.getInstance().getCategoriesApi();
        Call<Void> call = categoriesApi.deleteCategory(categoryId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Категорія видалена успішно
                    Intent intent = new Intent(CategoryDeleteActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Обробити помилку видалення, якщо необхідно
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Обробити помилку видалення, якщо необхідно
            }
        });
    }
}