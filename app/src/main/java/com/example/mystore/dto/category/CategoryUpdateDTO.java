package com.example.mystore.dto.category;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CategoryUpdateDTO {
    private RequestBody name;
    private MultipartBody.Part image;
    private RequestBody description;

    public CategoryUpdateDTO(String name, String imageFilePath, String description) {
        this.name = RequestBody.create(MediaType.parse("text/plain"), name);

        if (imageFilePath != null) {
            File imageFile = new File(imageFilePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            this.image = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);
        }

        this.description = RequestBody.create(MediaType.parse("text/plain"), description);
    }

    public RequestBody getName() {
        return name;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public RequestBody getDescription() {
        return description;
    }
}
