package com.example.mystore;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Button openChromeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openChromeButton = findViewById(R.id.openChromeButton);

        openChromeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInChrome("https://spu123.itstep.click/images/1.jpg");
            }
        });
    }

    private void openInChrome(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
