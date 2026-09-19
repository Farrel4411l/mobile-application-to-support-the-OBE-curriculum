package com.example.projectfarrelmobileprogramming.ui.rps;

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

public class RpsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rps);

        MaterialToolbar toolbar = findViewById(R.id.toolbarRps);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBarRps);
        tvContent = findViewById(R.id.tvContentRps);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvContentRps), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(16, 16, 16, systemBars.bottom + 16);
            return insets;
        });

        loadRpsData();
    }

    private void loadRpsData() {
        progressBar.setVisibility(View.VISIBLE);
        tvContent.setVisibility(View.GONE);
        
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvRps);
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        RpsAdapter adapter = new RpsAdapter();
        rv.setAdapter(adapter);
        
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getRps(100).enqueue(new Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>>() {
            @Override
            public void onResponse(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> call, Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    if (response.body().getData().isEmpty()) {
                        tvContent.setVisibility(View.VISIBLE);
                        tvContent.setText("Tidak ada data RPS.");
                    } else {
                        adapter.setRpsList(response.body().getData());
                    }
                } else {
                    tvContent.setVisibility(View.VISIBLE);
                    tvContent.setText("Gagal mengambil data RPS.");
                }
            }

            @Override
            public void onFailure(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText("Error Koneksi: " + t.getMessage());
            }
        });
    }
}
