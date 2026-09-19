package com.example.projectfarrelmobileprogramming.ui.mk;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.BahanKajian;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;
import com.google.android.material.appbar.MaterialToolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BkActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvEmpty;
    private RecyclerView rvBk;
    private BkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bk);

        MaterialToolbar toolbar = findViewById(R.id.toolbarBk);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBarBk);
        tvEmpty = findViewById(R.id.tvEmptyBk);
        rvBk = findViewById(R.id.rvBk);

        rvBk.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BkAdapter();
        rvBk.setAdapter(adapter);

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainBk), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        rvBk.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getBahanKajian(100).enqueue(new Callback<ApiResponse<BahanKajian>>() {
            @Override
            public void onResponse(Call<ApiResponse<BahanKajian>> call, Response<ApiResponse<BahanKajian>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    if (response.body().getData().isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText("Tidak ada data Bahan Kajian.");
                    } else {
                        rvBk.setVisibility(View.VISIBLE);
                        adapter.setBkList(response.body().getData());
                    }
                } else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Gagal mengambil data Bahan Kajian.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BahanKajian>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("Koneksi Error: " + t.getMessage());
            }
        });
    }
}
