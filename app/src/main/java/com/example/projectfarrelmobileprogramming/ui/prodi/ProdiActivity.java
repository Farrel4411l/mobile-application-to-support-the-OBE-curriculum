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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ProdiActivity extends AppCompatActivity {

    private ProdiViewModel prodiViewModel;
    private ProdiAdapter prodiAdapter;
    private RecyclerView rvProdi;
    private ProgressBar progressBar;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prodi);

        MaterialToolbar toolbar = findViewById(R.id.toolbarProdi);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvProdi = findViewById(R.id.rvProdi);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);

        rvProdi.setLayoutManager(new LinearLayoutManager(this));
        prodiAdapter = new ProdiAdapter();
        rvProdi.setAdapter(prodiAdapter);

        prodiViewModel = new ViewModelProvider(this).get(ProdiViewModel.class);

        observeViewModel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvProdi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
    }

    private void observeViewModel() {
        prodiViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    tvError.setVisibility(View.GONE);
                }
            }
        });

        prodiViewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        prodiViewModel.getProdiList().observe(this, prodis -> {
            if (prodis != null && !prodis.isEmpty()) {
                prodiAdapter.setProdiList(prodis);
                rvProdi.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
            } else if (prodis != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Data Prodi Kosong");
            }
        });
    }
}
