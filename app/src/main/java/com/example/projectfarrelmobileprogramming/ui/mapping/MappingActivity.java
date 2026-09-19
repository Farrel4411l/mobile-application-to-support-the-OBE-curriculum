package com.example.projectfarrelmobileprogramming.ui.mapping;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MappingActivity extends AppCompatActivity {

    private Spinner spinnerMappingType;
    private MaterialButton btnLoadMapping;
    private ProgressBar progressBarMapping;
    private TextView tvMappingResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mapping);

        MaterialToolbar toolbar = findViewById(R.id.toolbarMapping);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        spinnerMappingType = findViewById(R.id.spinnerMappingType);
        btnLoadMapping = findViewById(R.id.btnLoadMapping);
        progressBarMapping = findViewById(R.id.progressBarMapping);
        tvMappingResult = findViewById(R.id.tvMappingResult);

        String[] mappingOptions = {"Pemetaan BK-MK", "Pemetaan BK-CPL", "Pemetaan CPMK-MK", "Pemetaan CPL-SubCPMK"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mappingOptions);
        spinnerMappingType.setAdapter(adapter);

        btnLoadMapping.setOnClickListener(v -> loadMappingData(spinnerMappingType.getSelectedItemPosition()));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbarMapping), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
    }

    private void loadMappingData(int position) {
        progressBarMapping.setVisibility(View.VISIBLE);
        tvMappingResult.setText("");
        
        androidx.recyclerview.widget.RecyclerView rvMapping = findViewById(R.id.rvMapping);
        rvMapping.setVisibility(View.GONE);
        rvMapping.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        MappingAdapter adapter = new MappingAdapter();
        rvMapping.setAdapter(adapter);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse<Object>> call;
        
        String srcKey, tgtKey, srcLabel, tgtLabel;

        switch (position) {
            case 0: 
                call = apiService.getPemetaanBkMk(100); 
                srcKey = "id_bk"; tgtKey = "id_mk"; srcLabel = "Bahan Kajian"; tgtLabel = "Mata Kuliah";
                break;
            case 1: 
                call = apiService.getPemetaanBkCpl(100); 
                srcKey = "id_bk"; tgtKey = "id_cpl"; srcLabel = "Bahan Kajian"; tgtLabel = "CPL";
                break;
            case 2: 
                call = apiService.getPemetaanCpmkMk(100); 
                srcKey = "id_cpmk"; tgtKey = "id_mk"; srcLabel = "CPMK"; tgtLabel = "Mata Kuliah";
                break;
            case 3: 
                call = apiService.getPemetaanCplSubCpmk(100); 
                srcKey = "id_cpl"; tgtKey = "id_subcpmk"; srcLabel = "CPL"; tgtLabel = "Sub-CPMK";
                break;
            default: 
                call = apiService.getPemetaanBkMk(100);
                srcKey = "id_bk"; tgtKey = "id_mk"; srcLabel = "Bahan Kajian"; tgtLabel = "Mata Kuliah";
        }

        call.enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                progressBarMapping.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    java.util.List<Object> dataList = response.body().getData();
                    if (dataList.isEmpty()) {
                        tvMappingResult.setText("Tidak ada data pemetaan untuk filter ini.");
                    } else {
                        rvMapping.setVisibility(View.VISIBLE);
                        adapter.setMappingData(dataList, srcKey, tgtKey, srcLabel, tgtLabel);
                        tvMappingResult.setText("Menampilkan " + dataList.size() + " data pemetaan:");
                    }
                } else {
                    tvMappingResult.setText("Gagal memuat data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                progressBarMapping.setVisibility(View.GONE);
                tvMappingResult.setText("Koneksi Error: " + t.getMessage());
            }
        });
    }
}
