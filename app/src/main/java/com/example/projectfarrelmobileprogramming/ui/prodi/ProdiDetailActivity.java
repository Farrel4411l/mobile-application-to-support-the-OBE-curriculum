package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Prodi;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProdiDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prodi_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbarProdiDetail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView tvNama = findViewById(R.id.tvDetailNamaProdi);
        TabLayout tabLayout = findViewById(R.id.tabLayoutProdi);
        ViewPager2 viewPager = findViewById(R.id.viewPagerProdi);

        Prodi prodi = (Prodi) getIntent().getSerializableExtra("EXTRA_PRODI");
        String idProdi = "";

        if (prodi != null) {
            tvNama.setText(prodi.getNamaProdi());
            idProdi = prodi.getIdProdi();
        }

        ProdiPagerAdapter pagerAdapter = new ProdiPagerAdapter(this, idProdi);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Profil Lulusan");
            else tab.setText("CPL");
        }).attach();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbarProdiDetail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
    }
}
