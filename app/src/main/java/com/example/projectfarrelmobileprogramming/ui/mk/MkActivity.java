package com.example.projectfarrelmobileprogramming.ui.mk;

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

public class MkActivity extends AppCompatActivity {

    private MkViewModel mkViewModel;
    private MkAdapter mkAdapter;
    private RecyclerView rvMk;
    private ProgressBar progressBar;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mk);

        MaterialToolbar toolbar = findViewById(R.id.toolbarMk);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvMk = findViewById(R.id.rvMk);
        progressBar = findViewById(R.id.progressBarMk);
        tvError = findViewById(R.id.tvErrorMk);

        rvMk.setLayoutManager(new LinearLayoutManager(this));
        mkAdapter = new MkAdapter();
        rvMk.setAdapter(mkAdapter);

        findViewById(R.id.btnLihatBk).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, BkActivity.class));
        });

        mkViewModel = new ViewModelProvider(this).get(MkViewModel.class);

        observeViewModel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvMk), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
    }

    private void observeViewModel() {
        mkViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    tvError.setVisibility(View.GONE);
                }
            }
        });

        mkViewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(errorMsg);
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        mkViewModel.getMkList().observe(this, mks -> {
            if (mks != null && !mks.isEmpty()) {
                mkAdapter.setMkList(mks);
                rvMk.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
            } else if (mks != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Data Mata Kuliah Kosong");
            }
        });
    }
}
