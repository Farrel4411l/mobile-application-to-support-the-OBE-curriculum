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

/**
 * Kelas MkActivity.
 * Activity ini menampilkan daftar Mata Kuliah (MK) dalam bentuk list interaktif.
 * Menggunakan arsitektur MVVM (Model-View-ViewModel), dengan memantau data (observe) dari MkViewModel.
 */
public class MkActivity extends AppCompatActivity {

    // ViewModel yang bertanggung jawab memanajemen state (loading, error, data list) untuk layar ini
    private MkViewModel mkViewModel;
    // Adapter untuk RecyclerView, bertugas merender data Mata Kuliah ke dalam layout item
    private MkAdapter mkAdapter;
    // Komponen RecyclerView yang menampilkan daftar Mata Kuliah
    private RecyclerView rvMk;
    // Komponen ProgressBar untuk indikator loading data
    private ProgressBar progressBar;
    // Komponen TextView untuk menampilkan pesan apabila terjadi error/kegagalan loading data
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Mengeksekusi pembuatan activity standar dari sistem operasi
        super.onCreate(savedInstanceState);
        // Mengaktifkan tampilan Edge-to-Edge (immersive mode, di belakang system bars)
        EdgeToEdge.enable(this);
        // Menyetel UI menggunakan file layout XML activity_mk.xml
        setContentView(R.layout.activity_mk);

        // Menghubungkan objek toolbar dengan MaterialToolbar di XML
        MaterialToolbar toolbar = findViewById(R.id.toolbarMk);
        // Menjadikan toolbar tersebut sebagai Action Bar utama
        setSupportActionBar(toolbar);
        // Apabila Action Bar berhasil terinisialisasi
        if (getSupportActionBar() != null) {
            // Aktifkan ikon panah "back" di Action Bar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Menambahkan event listener pada ikon back, untuk menutup activity saat diklik
        toolbar.setNavigationOnClickListener(v -> finish());

        // Mengaitkan variabel UI ke komponen XML berdasarkan ID-nya
        rvMk = findViewById(R.id.rvMk);
        progressBar = findViewById(R.id.progressBarMk);
        tvError = findViewById(R.id.tvErrorMk);

        // Memberikan RecyclerView sebuah LayoutManager secara vertikal
        rvMk.setLayoutManager(new LinearLayoutManager(this));
        // Menginstansiasi Adapter
        mkAdapter = new MkAdapter();
        // Memasangkan Adapter pada RecyclerView
        rvMk.setAdapter(mkAdapter);

        // Mengambil tombol "Lihat BK" dari layout dan memasang OnClickListener padanya
        findViewById(R.id.btnLihatBk).setOnClickListener(v -> {
            // Memulai (berpindah) ke BkActivity ketika tombol ditekan
            startActivity(new android.content.Intent(this, BkActivity.class));
        });

        // Menginstansiasi ViewModel dengan memanfaatkan ViewModelProvider (terikat lifecycle Activity ini)
        mkViewModel = new ViewModelProvider(this).get(MkViewModel.class);

        // Memanggil fungsi untuk mulai mengobservasi LiveData dari ViewModel
        observeViewModel();

        // Menerapkan WindowInsets pada RecyclerView untuk mengatasi letak layout yang tabrakan dengan Navigation Bar / System Bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvMk), (v, insets) -> {
            // Mengambil insets untuk ukuran bar sistem operasi bawah/atas
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Memberikan padding bawah sesuai tinggi sistem bar, sehingga list tidak tertutup navbar bawah
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Memantau (observe) perubahan data pada LiveData yang diekspos oleh ViewModel.
     * View akan secara reaktif merespons perubahan loading, error, maupun datangnya data Mata Kuliah baru.
     */
    private void observeViewModel() {
        // Mengobservasi LiveData status loading
        mkViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                // Menampilkan progress bar jika sedang loading, menyembunyikannya jika selesai
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    // Menyembunyikan pesan error saat proses memuat ulang dimulai
                    tvError.setVisibility(View.GONE);
                }
            }
        });

        // Mengobservasi LiveData pesan error
        mkViewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null) {
                // Tampilkan TextView yang berisi error dan set isi pesannya
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(errorMsg);
                // Juga tampilkan toast error agar langsung terlihat oleh user (pop-up)
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        // Mengobservasi LiveData list MataKuliah (data utama)
        mkViewModel.getMkList().observe(this, mks -> {
            // Jika data list berhasil didapatkan dan tidak kosong
            if (mks != null && !mks.isEmpty()) {
                // Serahkan data baru tersebut ke Adapter untuk ditampilkan di daftar list RecyclerView
                mkAdapter.setMkList(mks);
                // Tampilkan RecyclerView
                rvMk.setVisibility(View.VISIBLE);
                // Sembunyikan teks error jika ada data
                tvError.setVisibility(View.GONE);
            } else if (mks != null) {
                // Jika list tidak null namun datanya kosong (tidak ada element / array size 0)
                tvError.setVisibility(View.VISIBLE);
                // Setel pesan bahwa data kosong
                tvError.setText("Data Mata Kuliah Kosong");
            }
        });
    }
}
