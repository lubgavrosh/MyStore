package com.example.mystore.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystore.R;
import com.example.mystore.adapters.CategoriesAdapter;
import com.example.mystore.dto.category.CategoryItemDTO;
import com.example.mystore.servises.ApplicationNetwork;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    RecyclerView rcSholom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcSholom = findViewById(R.id.rcSholom);
        rcSholom.setHasFixedSize(true);
        rcSholom.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        if(response.isSuccessful()) {
                            List<CategoryItemDTO> list = response.body();
                            CategoriesAdapter ca = new CategoriesAdapter(list);
                            rcSholom.setAdapter(ca);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable t) {

                    }
                });

//        ImageView ivLogo = findViewById(R.id.ivLogo);
//        String url = "https://img.freepik.com/free-photo/happiness-wellbeing-and-confidence-concept-cheerful-attractive-african-american-woman-curly-haircut-cross-arms-chest-in-self-assured-powerful-pose-smiling-determined-wear-yellow-sweater_176420-35063.jpg?w=360";
//        String url = "http://10.0.2.2:5190/images/1.jpg";
//        String url = "https://spu123.itstep.click/images/1.jpg";
//        Glide
//                .with(HomeApplication.getAppContext())
//                .load(url)
//                .apply(new RequestOptions().override(600))
//                .into(ivLogo);


    }
}