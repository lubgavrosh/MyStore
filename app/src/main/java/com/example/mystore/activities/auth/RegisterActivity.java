package com.example.mystore.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.contract.ActivityResultContracts;

import com.example.mystore.R;
import com.example.mystore.activities.BaseActivity;
import com.example.mystore.dto.user.RegisterModel;
import com.example.mystore.network.ApiClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private String userImage = null;

    private TextInputLayout emailField;
    private TextInputLayout firstNameField;
    private TextInputLayout lastNameField;
    private TextInputLayout usernameField;
    private TextInputLayout passwordField;
    private TextInputLayout repeatPasswordField;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailField = findViewById(R.id.emailField);
        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        usernameField = findViewById(R.id.unameField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        imageView = findViewById(R.id.ivSelectImage);

        Button imagePickBtn = findViewById(R.id.userImgBtn);
        imagePickBtn.setOnClickListener(view -> pickMedia.launch(new PickVisualMediaRequest()));

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this::onRegisterBtnClicked);

        listenToConnectionStatusWithDefaultCallback();
    }

    private final ActivityResultContracts.PickVisualMedia pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), result -> {
                if (result == null) {
                    return;
                }

                userImage = getPathFromURI(result);
                Picasso.get().load(result).into(imageView);
            });

    private void onRegisterBtnClicked(View contextView) {
        String formData = getPicFormDataFromPath(userImage);
        RegisterModel model = new RegisterModel(
                Objects.requireNonNull(emailField.getEditText()).getText().toString(),
                Objects.requireNonNull(usernameField.getEditText()).getText().toString(),
                Objects.requireNonNull(passwordField.getEditText()).getText().toString(),
                Objects.requireNonNull(firstNameField.getEditText()).getText().toString(),
                Objects.requireNonNull(lastNameField.getEditText()).getText().toString()
        );

        AuthValidator.RegisterErrors errors = AuthValidator.validateRegister(
                model.getEmail(), model.getFirstName(), model.getLastName(), model.getUserName(),
                model.getPassword(), Objects.requireNonNull(repeatPasswordField.getEditText()).getText().toString()
        );

        if (!errors.isValid()) {
            emailField.setError(errors.getEmail());
            usernameField.setError(errors.getUsername());
            firstNameField.setError(errors.getFirstName());
            lastNameField.setError(errors.getLastName());
            usernameField.setError(errors.getUsername());
            passwordField.setError(errors.getPassword());
            repeatPasswordField.setError(errors.getRepeatPassword());

            return;
        }

        ApiClient.authService.register(model.toMap(), formData).enqueue(
                new Callback<Unit>() {
                    @Override
                    public void onResponse(Call<Unit> call, Response<Unit> response) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }

                    @Override
                    public void onFailure(Call<Unit> call, Throwable t) {
                        Log.d("Register", t.getMessage());
                        Snackbar.make(
                                Objects.requireNonNull(findViewById(android.R.id.content)).getRootView(),
                                Objects.requireNonNull(t.getMessage()),
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
