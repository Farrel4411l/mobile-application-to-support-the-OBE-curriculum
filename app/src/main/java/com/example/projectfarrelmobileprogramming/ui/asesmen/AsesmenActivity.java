package com.example.projectfarrelmobileprogramming.ui.asesmen;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * AsesmenActivity adalah sebuah Activity yang digunakan untuk menampilkan daftar hasil asesmen.
 * Kelas ini mengambil data hasil asesmen dari API melalui kelas Retrofit (ApiService) 
 * dan menampilkannya kepada pengguna menggunakan antarmuka RecyclerView.
 */
public class AsesmenActivity extends AppCompatActivity {

    // Deklarasi variabel untuk ProgressBar yang menunjukkan status pemuatan data
    private ProgressBar progressBar;
    // Deklarasi variabel untuk TextView yang menampilkan pesan status atau teks fallback jika data kosong/gagal
    private TextView tvContent;

    /**
     * Metode ini dipanggil saat aktivitas pertama kali dibuat.
     * Ini digunakan untuk menginisialisasi UI dan memicu pengambilan data awal.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Memanggil implementasi superclass agar siklus hidup berjalan normal
        super.onCreate(savedInstanceState);
        // Mengaktifkan tampilan Edge-to-Edge agar aplikasi memenuhi layar, hingga ke belakang sistem bar (status/navigasi)
        EdgeToEdge.enable(this);
        // Mengatur layout XML activity_asesmen sebagai antarmuka untuk aktivitas ini
        setContentView(R.layout.activity_asesmen);

        // Menghubungkan variabel toolbar dengan komponen MaterialToolbar pada layout
        MaterialToolbar toolbar = findViewById(R.id.toolbarAsesmen);
        // Mengatur toolbar yang diambil sebagai ActionBar utama pada Activity ini
        setSupportActionBar(toolbar);
        // Memeriksa apakah action bar tidak bernilai null sebelum memanipulasinya
        if (getSupportActionBar() != null) {
            // Menampilkan tombol kembali (back button/up arrow) di action bar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Menampilkan ikon home di action bar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Memberikan listener atau aksi saat tombol navigasi (tombol kembali) ditekan
        // Saat diklik, activity ini akan ditutup (finish) dan pengguna akan kembali ke layar sebelumnya
        toolbar.setNavigationOnClickListener(v -> finish());

        // Menginisialisasi komponen ProgressBar berdasarkan ID dari layout
        progressBar = findViewById(R.id.progressBarAsesmen);
        // Menginisialisasi komponen TextView konten berdasarkan ID dari layout
        tvContent = findViewById(R.id.tvContentAsesmen);

        // Mengatur penyesuaian Insets (margin sistem bar seperti status bar dan navigation bar) untuk TextView 
        // Ini memastikan elemen tidak tertutup oleh bar sistem
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvContentAsesmen), (v, insets) -> {
            // Mendapatkan insets dari sistem bar (status bar, navigation bar)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Mengatur padding tampilan menggunakan informasi insets agar ada jarak yang aman di bagian bawah
            v.setPadding(16, 16, 16, systemBars.bottom + 16);
            // Mengembalikan insets setelah diaplikasikan
            return insets;
        });

        // Memanggil metode untuk memuat data asesmen dari internet (API)
        loadAsesmenData();
    }

    /**
     * Metode untuk mengambil dan memuat data Asesmen dari server (API) dan 
     * menghubungkannya ke komponen RecyclerView di layout.
     */
    private void loadAsesmenData() {
        // Menampilkan ProgressBar saat proses pengambilan data sedang berjalan (loading)
        progressBar.setVisibility(View.VISIBLE);
        // Menyembunyikan TextView konten sementara data sedang dimuat
        tvContent.setVisibility(View.GONE);
        
        // Mendeklarasikan dan menghubungkan komponen RecyclerView untuk daftar asesmen dari layout XML
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvAsesmen);
        // Mengatur LayoutManager menjadi LinearLayoutManager, agar daftar ditampilkan secara vertikal dari atas ke bawah
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        // Membuat instance adapter khusus untuk daftar asesmen (AsesmenAdapter)
        AsesmenAdapter adapter = new AsesmenAdapter();
        // Menghubungkan adapter yang baru dibuat ke komponen RecyclerView
        rv.setAdapter(adapter);

        // Membuat instance ApiService menggunakan Retrofit (dari konfigurasi ApiClient)
        // Ini memungkinkan aplikasi untuk melakukan panggilan jaringan ke endpoint API
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // Memanggil endpoint getHasilAsesmen dan memberikan batasan ukuran data (limit=100) secara asinkron (enqueue)
        apiService.getHasilAsesmen(100).enqueue(new Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>>() {
            // Callback ketika response diterima dari server API
            @Override
            public void onResponse(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> call, Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> response) {
                // Menyembunyikan ProgressBar karena proses pengambilan data (loading) telah selesai
                progressBar.setVisibility(View.GONE);
                // Mengecek apakah respons dari server berhasil (kode 200-299), body-nya tidak kosong, dan datanya tidak kosong
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    // Mengecek apakah data yang diterima berupa list atau daftar kosong
                    if (response.body().getData().isEmpty()) {
                        // Jika datanya kosong, tampilkan TextView untuk memberikan informasi kepada pengguna
                        tvContent.setVisibility(View.VISIBLE);
                        // Mengatur teks bahwa data hasil asesmen tidak ditemukan
                        tvContent.setText("Tidak ada data Hasil Asesmen.");
                    } else {
                        // Jika data ditemukan, masukkan (set) list data asesmen tersebut ke dalam adapter
                        // Adapter ini kemudian akan memperbarui tampilan RecyclerView
                        adapter.setAsesmenList(response.body().getData());
                    }
                } else {
                    // Jika ada kegagalan respons (seperti kode error HTTP selain 200, contoh 404/500)
                    // Tampilkan TextView pesan kesalahan
                    tvContent.setVisibility(View.VISIBLE);
                    String errBody = "";
                    // Mencoba membaca dan mengambil pesan detail error body dari server menggunakan blok try-catch
                    try { if(response.errorBody() != null) errBody = response.errorBody().string(); } catch(Exception e){}
                    // Mengatur teks pesan error pada tampilan agar pengguna tahu bahwa ada masalah pengambilan data beserta kode error
                    tvContent.setText("Gagal mengambil Asesmen. Code: " + response.code() + " " + errBody);
                }
            }

            // Callback ketika terjadi kegagalan jaringan atau koneksi ke server, bukan error respon HTTP (misalnya tidak ada internet)
            @Override
            public void onFailure(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> call, Throwable t) {
                // Menyembunyikan ProgressBar karena proses pemanggilan terhenti akibat kegagalan
                progressBar.setVisibility(View.GONE);
                // Menampilkan TextView konten untuk menunjukkan pesan kesalahan
                tvContent.setVisibility(View.VISIBLE);
                // Menampilkan pesan atau penyebab error tersebut ke dalam TextView
                tvContent.setText("Error Koneksi: " + t.getMessage());
            }
        });
    }
}
