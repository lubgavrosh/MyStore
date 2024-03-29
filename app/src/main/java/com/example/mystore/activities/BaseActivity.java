package com.example.mystore.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystore.R;
import com.example.mystore.activities.category.CategoryCreateActivity;
import com.example.mystore.activities.category.CategoryUpdateActivity;
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int select = item.getItemId();
        if (select==R.id.m_create) {
            Intent intent = new Intent(BaseActivity.this, CategoryCreateActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if(select==R.id.m_home) {
            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (select == R.id.m_edit) {

            Intent intent = new Intent(BaseActivity.this, CategoryUpdateActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}