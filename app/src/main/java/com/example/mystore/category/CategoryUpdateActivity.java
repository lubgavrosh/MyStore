package com.example.mystore.category;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mystore.BaseActivity;
import com.example.mystore.MainActivity;
import com.example.mystore.R;
import com.example.mystore.application.HomeApplication;
import com.example.mystore.dto.CategoryItemDTO;
import com.example.mystore.servises.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryUpdateActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    TextInputLayout tfCategoryName;
    ImageView ivSelectImage;
    String filePath;
    TextInputLayout tfCategoryDescription;
    int categoryId; // Отримуємо ідентифікатор категорії, яку редагуємо

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_update);

        ivSelectImage = findViewById(R.id.ivSelectImage);
        tfCategoryName = findViewById(R.id.tfCategoryName);
        tfCategoryDescription = findViewById(R.id.tfCategoryDescription);

        // Отримайте ідентифікатор категорії для редагування з Intent
        categoryId = getIntent().getIntExtra("categoryId", 0); // 0 - значення за замовчуванням, ви можете вибрати інше

        // Завантажте дані категорії для редагування з сервера на основі categoryId і відобразіть їх на інтерфейсі користувача.
        loadCategoryData(categoryId);
    }

    public void onClickUpdateCategory(View view) {
        String name = tfCategoryName.getEditText().getText().toString().trim();
        String description = tfCategoryDescription.getEditText().getText().toString().trim();

        // Оновлення категорії на сервері з використанням categoryId
        updateCategoryOnServer(categoryId, name, description, filePath);
    }

    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            Glide
                    .with(HomeApplication.getAppContext())
                    .load(uri)
                    .apply(new RequestOptions().override(300))
                    .into(ivSelectImage);

            filePath = getPathFromURI(uri);
        }
    }

    private void loadCategoryData(int categoryId) {
        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .getCategoryById(categoryId)
                .enqueue(new Callback<CategoryItemDTO>() {
                    @Override
                    public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                        if (response.isSuccessful()) {
                            CategoryItemDTO category = response.body();
                            // Оновіть тексти та зображення на інтерфейсі користувача на основі отриманих даних
                            tfCategoryName.getEditText().setText(category.getName());
                            tfCategoryDescription.getEditText().setText(category.getDescription());

                            // Відобразіть зображення категорії, якщо воно є
                            if (category.getImageUrl() != null) {
                                Glide
                                        .with(HomeApplication.getAppContext())
                                        .load(category.getImageUrl())
                                        .apply(new RequestOptions().override(300))
                                        .into(ivSelectImage);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryItemDTO> call, Throwable t) {
                        // Обробіть помилку при завантаженні даних категорії
                    }
                });
    }

    private void updateCategoryOnServer(int categoryId, String name, String description, String filePath) {
        // Створіть запит для оновлення категорії на сервері
        MultipartBody.Part imagePart = null;
        if (filePath != null) {
            // Якщо ви вибрали нове зображення, то створіть MultipartBody.Part для завантаження
            File imageFile = new File(filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            imagePart = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);
        }

        RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody descriptionPart = RequestBody.create(MediaType.parse("text/plain"), description);

        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .updateCategory(categoryId, imagePart, namePart, descriptionPart)
                .enqueue(new Callback<CategoryItemDTO>() {
                    @Override
                    public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                        if (response.isSuccessful()) {
                            CategoryItemDTO updatedCategory = response.body();
                            // Обробіть успішну відповідь та виконайте необхідні дії (наприклад, перейдіть назад до MainActivity)
                            Intent intent = new Intent(CategoryUpdateActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryItemDTO> call, Throwable t) {
                        // Обробіть помилку під час оновлення категорії на сервері
                    }
                });
    }


    private String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }
}
