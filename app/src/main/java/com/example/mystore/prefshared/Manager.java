package com.example.mystore.prefshared;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.mystore.R;


public class Manager {
    private final SharedPreferences prefs;

    public Manager(Context context) {
        prefs = context.getSharedPreferences(
                context.getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
    }

    public static final String USER_TOKEN = "user_token";
    public static final String REFRESH_TOKEN = "refresh_token";

    public void saveAuthToken(String token) {
        editPrefs(USER_TOKEN, token);
    }

    public String fetchAuthToken() {
        return prefs.getString(USER_TOKEN, null);
    }

    public void saveRefreshToken(String token) {
        editPrefs(REFRESH_TOKEN, token);
    }

    public String fetchRefreshToken() {
        return prefs.getString(REFRESH_TOKEN, null);
    }

    public void clearTokens() {
        editPrefs(USER_TOKEN, null);
        editPrefs(REFRESH_TOKEN, null);
    }

    private void editPrefs(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
