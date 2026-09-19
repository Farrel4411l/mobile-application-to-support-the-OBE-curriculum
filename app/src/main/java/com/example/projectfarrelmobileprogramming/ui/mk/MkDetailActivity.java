package com.example.projectfarrelmobileprogramming.ui.mk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;
import com.google.android.material.appbar.MaterialToolbar;

public class MkDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mk_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbarMkDetail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView tvNama = findViewById(R.id.tvDetailNamaMk);
        TextView tvKode = findViewById(R.id.tvDetailKodeMk);
        TextView tvSks = findViewById(R.id.tvDetailSks);
        TextView tvSemester = findViewById(R.id.tvDetailSemester);

        MataKuliah mk = (MataKuliah) getIntent().getSerializableExtra("EXTRA_MK");

        if (mk != null) {
            tvNama.setText(mk.getNamaMk());
            tvKode.setText(mk.getIdMk());
            tvSks.setText(String.valueOf(mk.getSks()));
            tvSemester.setText(mk.getSemester());
            
            loadCpmk(mk.getIdMk());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbarMkDetail), (v, insets) -> {
            Insets systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
    }

    private void loadCpmk(String idMk) {
        android.widget.ProgressBar progressBar = findViewById(R.id.progressBarCpmk);
        TextView tvKosong = findViewById(R.id.tvCpmkKosong);
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvCpmk);
        
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        CpmkAdapter adapter = new CpmkAdapter();
        rv.setAdapter(adapter);
        
        progressBar.setVisibility(android.view.View.VISIBLE);
        tvKosong.setVisibility(android.view.View.GONE);
        rv.setVisibility(android.view.View.GONE);

        com.example.projectfarrelmobileprogramming.data.network.ApiService apiService = 
                com.example.projectfarrelmobileprogramming.data.network.ApiClient.getClient().create(com.example.projectfarrelmobileprogramming.data.network.ApiService.class);
                
        // Sesuai endpoint API, kita ambil daftar CPMK dan filter lokal berdasarkan id_mk
        apiService.getCpmk(null, 100).enqueue(new retrofit2.Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>>() {
            @Override
            public void onResponse(retrofit2.Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> call, retrofit2.Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> response) {
                progressBar.setVisibility(android.view.View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    java.util.List<com.example.projectfarrelmobileprogramming.data.model.Cpmk> allCpmk = response.body().getData();
                    java.util.List<com.example.projectfarrelmobileprogramming.data.model.Cpmk> filteredCpmk = new java.util.ArrayList<>();
                    
                    for (com.example.projectfarrelmobileprogramming.data.model.Cpmk c : allCpmk) {
                        if (idMk.equals(c.getIdMk())) {
                            filteredCpmk.add(c);
                        }
                    }
                    
                    if (filteredCpmk.isEmpty()) {
                        tvKosong.setVisibility(android.view.View.VISIBLE);
                    } else {
                        rv.setVisibility(android.view.View.VISIBLE);
                        adapter.setCpmkList(filteredCpmk);
                    }
                } else {
                    tvKosong.setVisibility(android.view.View.VISIBLE);
                    tvKosong.setText("Gagal memuat CPMK");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> call, Throwable t) {
                progressBar.setVisibility(android.view.View.GONE);
                tvKosong.setVisibility(android.view.View.VISIBLE);
                tvKosong.setText("Error koneksi: " + t.getMessage());
            }
        });
    }
}
