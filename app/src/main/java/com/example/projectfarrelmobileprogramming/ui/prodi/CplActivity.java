package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.Cpl;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CplActivity extends AppCompatActivity {

    private CplAdapter cplAdapter;
    private RecyclerView rvCpl;
    private ProgressBar progressBar;
    private TextView tvError;
    private String idProdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cpl);

        idProdi = getIntent().getStringExtra("ID_PRODI");
        if (idProdi == null) idProdi = "SI"; // Default if not passed

        MaterialToolbar toolbar = findViewById(R.id.toolbarCpl);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("CPL Prodi " + idProdi);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvCpl = findViewById(R.id.rvCpl);
        progressBar = findViewById(R.id.progressBarCpl);
        tvError = findViewById(R.id.tvErrorCpl);

        rvCpl.setLayoutManager(new LinearLayoutManager(this));
        cplAdapter = new CplAdapter();
        rvCpl.setAdapter(cplAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvCpl), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });

        fetchCplData();
    }

    private void fetchCplData() {
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getCpl(idProdi, 50).enqueue(new Callback<ApiResponse<Cpl>>() {
            @Override
            public void onResponse(Call<ApiResponse<Cpl>> call, Response<ApiResponse<Cpl>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                        cplAdapter.setCplList(response.body().getData());
                        rvCpl.setVisibility(View.VISIBLE);
                    } else {
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText("Data CPL Kosong");
                    }
                } else {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Gagal mengambil data CPL");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Cpl>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Koneksi Bermasalah: " + t.getMessage());
            }
        });
    }
}
