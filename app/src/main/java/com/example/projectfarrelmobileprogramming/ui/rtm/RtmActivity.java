package com.example.projectfarrelmobileprogramming.ui.rtm;

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

public class RtmActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rtm);

        MaterialToolbar toolbar = findViewById(R.id.toolbarRtm);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBarRtm);
        tvContent = findViewById(R.id.tvContentRtm);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvContentRtm), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(16, 16, 16, systemBars.bottom + 16);
            return insets;
        });

        loadRtmData();
    }

    private void loadRtmData() {
        progressBar.setVisibility(View.VISIBLE);
        tvContent.setVisibility(View.GONE);
        
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvRtm);
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        RtmAdapter adapter = new RtmAdapter();
        rv.setAdapter(adapter);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getRtm(100).enqueue(new Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>>() {
            @Override
            public void onResponse(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> call, Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    if (response.body().getData().isEmpty()) {
                        tvContent.setVisibility(View.VISIBLE);
                        tvContent.setText("Tidak ada data Rencana Tugas Mandiri.");
                    } else {
                        adapter.setRtmList(response.body().getData());
                    }
                } else {
                    tvContent.setVisibility(View.VISIBLE);
                    String errBody = "";
                    try { if(response.errorBody() != null) errBody = response.errorBody().string(); } catch(Exception e){}
                    tvContent.setText("Gagal mengambil RTM. Code: " + response.code() + " " + errBody);
                }
            }

            @Override
            public void onFailure(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText("Error Koneksi: " + t.getMessage());
            }
        });
    }
}
