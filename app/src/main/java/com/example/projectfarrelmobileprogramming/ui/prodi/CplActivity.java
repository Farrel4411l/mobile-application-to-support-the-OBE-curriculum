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

/**
 * Kelas CplActivity berfungsi sebagai halaman utama untuk menampilkan Capaian Pembelajaran Lulusan (CPL).
 * Kelas ini bertanggung jawab untuk mengambil data CPL dari server menggunakan Retrofit (melalui ApiService),
 * dan menampilkannya di dalam daftar (RecyclerView). Activity ini akan mengelola state saat memuat data (loading),
 * saat data berhasil diterima, dan jika terjadi kesalahan (error).
 */
public class CplActivity extends AppCompatActivity {

    // Deklarasi adapter untuk RecyclerView yang akan menampilkan daftar CPL
    private CplAdapter cplAdapter;
    // Deklarasi RecyclerView sebagai komponen antarmuka yang menampilkan daftar
    private RecyclerView rvCpl;
    // Deklarasi ProgressBar untuk menampilkan indikator proses pemuatan data dari API
    private ProgressBar progressBar;
    // Deklarasi TextView untuk menampilkan pesan error saat terjadi kegagalan pengambilan data
    private TextView tvError;
    // Menyimpan ID Program Studi (Prodi) yang datanya akan diambil dari server
    private String idProdi;

    /**
     * Metode ini dipanggil saat Activity pertama kali dibuat.
     * Metode ini menginisialisasi tampilan, pengaturan edge-to-edge, menerima data dari intent, 
     * mengonfigurasi Toolbar, RecyclerView, dan memicu pemanggilan data API.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengaktifkan fitur EdgeToEdge agar UI aplikasi bisa menyatu ke area status bar / navigation bar
        EdgeToEdge.enable(this);
        // Menentukan layout XML yang digunakan oleh Activity ini
        setContentView(R.layout.activity_cpl);

        // Mengambil data "ID_PRODI" yang dikirim melalui Intent dari Activity sebelumnya
        idProdi = getIntent().getStringExtra("ID_PRODI");
        // Jika data idProdi bernilai null (tidak dikirimkan), maka diatur ke nilai default, yaitu "SI"
        if (idProdi == null) idProdi = "SI"; 

        // Menghubungkan variabel toolbar dengan komponen UI Toolbar di XML
        MaterialToolbar toolbar = findViewById(R.id.toolbarCpl);
        // Mengatur toolbar ini agar berfungsi sebagai ActionBar bawaan Activity
        setSupportActionBar(toolbar);
        
        // Memeriksa apakah ActionBar berhasil dipasang
        if (getSupportActionBar() != null) {
            // Mengaktifkan tombol 'Kembali' (Back/Up button) pada ActionBar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Mengatur judul ActionBar sesuai dengan ID Prodi
            getSupportActionBar().setTitle("CPL Prodi " + idProdi);
        }
        
        // Menangani peristiwa saat tombol navigasi 'Kembali' pada toolbar ditekan, 
        // yaitu dengan menutup (finish) CplActivity
        toolbar.setNavigationOnClickListener(v -> finish());

        // Menghubungkan variabel komponen antarmuka dengan ID pada XML
        rvCpl = findViewById(R.id.rvCpl);
        progressBar = findViewById(R.id.progressBarCpl);
        tvError = findViewById(R.id.tvErrorCpl);

        // Mengatur layout manager pada RecyclerView sebagai LinearLayoutManager agar daftar berbentuk vertikal
        rvCpl.setLayoutManager(new LinearLayoutManager(this));
        // Menginisialisasi CplAdapter
        cplAdapter = new CplAdapter();
        // Memasang adapter ke RecyclerView agar dapat menampilkan data CPL
        rvCpl.setAdapter(cplAdapter);

        // Menambahkan listener WindowInsets pada RecyclerView untuk menangani padding saat fitur EdgeToEdge aktif
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rvCpl), (v, insets) -> {
            // Mendapatkan inset (batasan ruang) dari system bars
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Mengatur padding bawah RecyclerView sebesar tinggi navigation bar agar item tidak terpotong
            v.setPadding(0, 0, 0, systemBars.bottom);
            // Mengembalikan hasil pemrosesan insets
            return insets;
        });

        // Memanggil metode untuk mengambil data CPL dari server API
        fetchCplData();
    }

    /**
     * Metode ini bertanggung jawab untuk melakukan panggilan jaringan ke backend (API)
     * demi mendapatkan daftar Capaian Pembelajaran Lulusan (CPL) berdasarkan idProdi.
     */
    private void fetchCplData() {
        // Menampilkan ProgressBar sebagai tanda bahwa proses pengambilan data sedang berjalan
        progressBar.setVisibility(View.VISIBLE);
        // Menyembunyikan TextView error agar tidak muncul sebelum terjadi error
        tvError.setVisibility(View.GONE);

        // Membuat instance ApiService menggunakan Retrofit (ApiClient)
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        
        // Memulai permintaan data secara asynchronous dengan memanggil endpoint getCpl() dari ApiService
        // Mengirimkan parameter idProdi dan limit data sebanyak 50
        apiService.getCpl(idProdi, 50).enqueue(new Callback<ApiResponse<Cpl>>() {
            
            /**
             * Metode ini dipanggil saat balasan (response) dari API berhasil diterima (baik sukses maupun error dari server).
             */
            @Override
            public void onResponse(Call<ApiResponse<Cpl>> call, Response<ApiResponse<Cpl>> response) {
                // Menyembunyikan ProgressBar karena proses pemuatan selesai
                progressBar.setVisibility(View.GONE);
                
                // Memeriksa apakah response API berhasil (kode HTTP 2xx) dan tidak kosong (body bukan null)
                if (response.isSuccessful() && response.body() != null) {
                    
                    // Mengecek apakah list data di dalam response tidak null dan tidak kosong
                    if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                        // Memasukkan data list CPL ke dalam adapter RecyclerView
                        cplAdapter.setCplList(response.body().getData());
                        // Menampilkan RecyclerView yang telah terisi data
                        rvCpl.setVisibility(View.VISIBLE);
                    } else {
                        // Jika data list CPL kosong, maka tampilkan pesan bahwa data kosong
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText("Data CPL Kosong");
                    }
                } else {
                    // Jika terjadi respon HTTP bermasalah, tampilkan pesan bahwa gagal mengambil data
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Gagal mengambil data CPL");
                }
            }

            /**
             * Metode ini dipanggil saat permintaan ke API gagal (contoh: tidak ada koneksi internet, server mati, atau timeout).
             */
            @Override
            public void onFailure(Call<ApiResponse<Cpl>> call, Throwable t) {
                // Menyembunyikan ProgressBar karena proses dihentikan
                progressBar.setVisibility(View.GONE);
                // Menampilkan TextView error dan pesan error/kegagalan (t.getMessage())
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Koneksi Bermasalah: " + t.getMessage());
            }
        });
    }
}
