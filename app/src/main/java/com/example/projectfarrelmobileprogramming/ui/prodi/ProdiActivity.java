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

/**
 * Kelas ProdiActivity adalah activity yang berperan menampilkan daftar Program Studi (Prodi) di dalam RecyclerView.
 * Activity ini memanfaatkan komponen arsitektur modern yaitu ViewModel untuk memisahkan logika UI
 * dengan pengolahan dan penyimpanan data. Pengambilan data ditangani oleh ProdiViewModel.
 */
public class ProdiActivity extends AppCompatActivity {

    // ViewModel yang bertanggung jawab dalam mengurus dan menyimpan state data khusus untuk activity Prodi
    private ProdiViewModel prodiViewModel;
    // Adapter untuk RecyclerView dari list program studi
    private ProdiAdapter prodiAdapter;
    // Tampilan list
    private RecyclerView rvProdi;
    // Loading bar ketika fetching api
    private ProgressBar progressBar;
    // Tampilan pesan error / gagal / data kosong
    private TextView tvError;

    /**
     * Inisialisasi awal activity. Menyiapkan layout UI, mengonfigurasi EdgeToEdge,
     * Toolbar, dan juga inisialisasi RecyclerView beserta ViewModel yang dibutuhkan.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengatur Activity untuk memakai EdgeToEdge display, yaitu menyebar di sela-sela area status bar 
        EdgeToEdge.enable(this);
        // Memakai layout activity_prodi.xml
        setContentView(R.layout.activity_prodi);

        // Menghubungkan dan inisialisasi ActionBar menggunakan MaterialToolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbarProdi);
        setSupportActionBar(toolbar);
        // Memeriksa toolbar eksis dan tidak bernilai null
        if (getSupportActionBar() != null) {
            // Memberikan ikon panah 'kembali/up' di toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Menambahkan fungsi ke tombol 'kembali/up', dimana jika ditekan akan mengakhiri (finish) activity ini
        toolbar.setNavigationOnClickListener(v -> finish());

        // Mengambil referensi View dari file layout XML ke objek java
        rvProdi = findViewById(R.id.rvProdi);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);

        // Setup LayoutManager dan Adapter untuk RecyclerView Prodi
        rvProdi.setLayoutManager(new LinearLayoutManager(this));
        prodiAdapter = new ProdiAdapter();
        rvProdi.setAdapter(prodiAdapter);

        // Instansiasi view model melalui ViewModelProvider yang mengikat ViewModel tersebut 
        // dengan siklus hidup (lifecycle) Activity ini.
        prodiViewModel = new ViewModelProvider(this).get(ProdiViewModel.class);

        // Memanggil fungsi untuk mengawasi/mendengarkan LiveData yang ada di dalam ViewModel
        observeViewModel();

        // Menyisipkan listener insets agar view dalam aplikasi terhindar dari komponen bawaan device 
        // (contohnya gesture navigation bar) sehingga list tidak terpotong bagian bawahnya.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvProdi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Memantau (observe) semua LiveData (seperti indikator loading, data yang diperoleh, atau pesan error)
     * yang disediakan oleh ProdiViewModel.
     * Tiap kali LiveData di-update, callback di-trigger dan secara otomatis merender View yang relevan.
     */
    private void observeViewModel() {
        
        // Memantau state "Loading" dari ViewModel
        prodiViewModel.getLoading().observe(this, isLoading -> {
            // Apabila nilainya tidak null
            if (isLoading != null) {
                // Munculkan progressBar jika isLoading true, dan sebaliknya (GONE)
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    // Selama proses fetching loading berjalan, hilangkan teks error
                    tvError.setVisibility(View.GONE);
                }
            }
        });

        // Memantau apabila ada pesan error (Error State)
        prodiViewModel.getError().observe(this, errorMsg -> {
            // Apabila variabel pesan error tidak null
            if (errorMsg != null) {
                // Tampilkan text view
                tvError.setVisibility(View.VISIBLE);
                // Ubah konten text view sesuai dengan pesan kegagalan dari server/jaringan
                tvError.setText(errorMsg);
                // Munculkan popup kecil yang mengapung di bawah (Toast) dengan pesan error juga
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        // Memantau perubahan nilai daftar (list) data Prodi itu sendiri
        prodiViewModel.getProdiList().observe(this, prodis -> {
            // Jika datanya tidak null dan daftar program studinya ada (tidak kosong)
            if (prodis != null && !prodis.isEmpty()) {
                // Salurkan ke adapter untuk dirender
                prodiAdapter.setProdiList(prodis);
                // Pastikan list diperlihatkan 
                rvProdi.setVisibility(View.VISIBLE);
                // Hilangkan elemen text error 
                tvError.setVisibility(View.GONE);
            } else if (prodis != null) {
                // Jika datasetnya kosong tapi tidak bernilai null (request success namun data dari DB kosong)
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Data Prodi Kosong");
            }
        });
    }
}
