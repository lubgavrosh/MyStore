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

public class MainActivity extends AppCompatActivity {

    private Button openChromeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivLogo =findViewById(R.id.ivLogo);
        String url = "https://spu123.itstep.click/images/1.jpg";
        Glide.with(HomeApplication.getAppContext()).load(url).into(ivLogo);
    }
}
