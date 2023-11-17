package com.example.mystore.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystore.R;
import com.example.mystore.activities.MainActivity;
import com.example.mystore.application.HomeApplication;
import com.example.mystore.dto.user.LoginModel;
import com.example.mystore.dto.user.TokensModel;
import com.example.mystore.network.ApiClient;
import com.example.mystore.prefshared.Manager;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String errorMessage = "Invalid credentials";
    private final Manager sessionManager = new Manager(HomeApplication.getAppContext());

    private TextInputLayout usernameField;
    private TextInputLayout pwdField;
    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(Button.class.cast(findViewById(R.id.loginBtn))).setOnClickListener(this::login);

        usernameField = findViewById(R.id.unameField);
        pwdField = findViewById(R.id.passwordField);
        registerTextView = findViewById(R.id.registerTextView);

        usernameField.getEditText().addTextChangedListener(new TextWatcherHelper(usernameField));
        pwdField.getEditText().addTextChangedListener(new TextWatcherHelper(pwdField));

        registerTextView.setClickable(true);
        registerTextView.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void login(View view) {
        String username = usernameField.getEditText().getText().toString().trim();
        String pwd = pwdField.getEditText().getText().toString().trim();
        LoginModel loginModel = new LoginModel(username, pwd);

        AuthValidator.LoginErrors errors = AuthValidator.validateLogin(username, pwd);
        if (!errors.isValid()) {
            usernameField.setError(errors.getEmail());
            pwdField.setError(errors.getPassword());
            return;
        }

        ApiClient.authService.login(loginModel).enqueue(
                new Callback<TokensModel>() {
                    @Override
                    public void onResponse(Call<TokensModel> call, Response<TokensModel> response) {
                        if (response.isSuccessful()) {
                            TokensModel tokens = response.body();

                            sessionManager.saveAuthToken(tokens.getAccessToken());
                            sessionManager.saveRefreshToken(tokens.getRefreshToken());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            onFailure(call, new Throwable(response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<TokensModel> call, Throwable t) {
                        Log.e("LoginActivity", t.getMessage());
                        usernameField.setError(errorMessage);
                        pwdField.setError(errorMessage);
                    }
                }
        );
    }

    private static class TextWatcherHelper implements TextWatcher {
        private final TextInputLayout textInputLayout;

        TextWatcherHelper(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // перед зміною тексту
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // під час зміни тексту
            resetErrors(textInputLayout);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // після зміни тексту
        }

        private void resetErrors(TextInputLayout textInputLayout) {
            textInputLayout.setError(null);
        }
    }
}
