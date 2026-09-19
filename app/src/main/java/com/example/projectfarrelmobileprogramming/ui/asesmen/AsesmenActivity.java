package com.example.projectfarrelmobileprogramming.ui.asesmen;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsesmenActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asesmen);

        MaterialToolbar toolbar = findViewById(R.id.toolbarAsesmen);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBarAsesmen);
        tvContent = findViewById(R.id.tvContentAsesmen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvContentAsesmen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(16, 16, 16, systemBars.bottom + 16);
            return insets;
        });

        loadAsesmenData();
    }

    private void loadAsesmenData() {
        progressBar.setVisibility(View.VISIBLE);
        tvContent.setVisibility(View.GONE);
        
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvAsesmen);
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        AsesmenAdapter adapter = new AsesmenAdapter();
        rv.setAdapter(adapter);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getHasilAsesmen(100).enqueue(new Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>>() {
            @Override
            public void onResponse(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> call, Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    if (response.body().getData().isEmpty()) {
                        tvContent.setVisibility(View.VISIBLE);
                        tvContent.setText("Tidak ada data Hasil Asesmen.");
                    } else {
                        adapter.setAsesmenList(response.body().getData());
                    }
                } else {
                    tvContent.setVisibility(View.VISIBLE);
                    String errBody = "";
                    try { if(response.errorBody() != null) errBody = response.errorBody().string(); } catch(Exception e){}
                    tvContent.setText("Gagal mengambil Asesmen. Code: " + response.code() + " " + errBody);
                }
            }

            @Override
            public void onFailure(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText("Error Koneksi: " + t.getMessage());
            }
        });
    }
}
