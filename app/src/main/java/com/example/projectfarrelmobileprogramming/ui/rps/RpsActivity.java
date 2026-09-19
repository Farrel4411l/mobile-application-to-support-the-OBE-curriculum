package com.example.projectfarrelmobileprogramming.ui.rps;

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
 * Kelas RpsActivity
 * <p>
 * Menangani tampilan halaman RPS (Rencana Pembelajaran Semester).
 * Berfungsi untuk mengambil daftar RPS dari API (backend) menggunakan Retrofit
 * dan menampilkannya kepada pengguna dalam bentuk list (RecyclerView).
 */
public class RpsActivity extends AppCompatActivity {

    // Indikator loading saat memuat data RPS
    private ProgressBar progressBar;
    // Teks yang akan ditampilkan jika daftar RPS kosong atau saat terjadi error
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengaktifkan mode Edge-To-Edge agar layar membentang penuh hingga system UI
        EdgeToEdge.enable(this);
        // Menentukan layout XML yang digunakan
        setContentView(R.layout.activity_rps);

        // Menghubungkan dan mengkonfigurasi toolbar sebagai ActionBar
        MaterialToolbar toolbar = findViewById(R.id.toolbarRps);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // Menampilkan tombol panah kembali (Back/Up button)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Listener saat panah kembali ditekan, memanggil finish() untuk menutup Activity ini
        toolbar.setNavigationOnClickListener(v -> finish());

        // Menyambungkan widget dengan ID di layout
        progressBar = findViewById(R.id.progressBarRps);
        tvContent = findViewById(R.id.tvContentRps);

        // Menambahkan listener padding otomatis untuk mengkompensasi bagian bawah layar
        // agar tidak tertutup navigation bar (System UI)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvContentRps), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(16, 16, 16, systemBars.bottom + 16);
            return insets;
        });

        // Memanggil fungsi untuk mulai melakukan request data ke API
        loadRpsData();
    }

    /**
     * Mengunduh data RPS dari server menggunakan Retrofit, dan menampilkannya di RecyclerView.
     */
    private void loadRpsData() {
        // Tampilkan indikator loading dan sembunyikan pesan teks sementara waktu
        progressBar.setVisibility(View.VISIBLE);
        tvContent.setVisibility(View.GONE);
        
        // Mempersiapkan RecyclerView untuk daftar RPS
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvRps);
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        
        // Membuat dan memasang Adapter pada RecyclerView
        RpsAdapter adapter = new RpsAdapter();
        rv.setAdapter(adapter);
        
        // Membuat instance antarmuka Retrofit untuk memanggil endpoint RPS
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // Mengeksekusi request secara asinkron (enqueue) untuk batas limit 100 data
        apiService.getRps(100).enqueue(new Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>>() {
            
            // Callback ketika response HTTP telah diterima dari server
            @Override
            public void onResponse(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> call, Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> response) {
                // Sembunyikan loading karena proses memuat selesai
                progressBar.setVisibility(View.GONE);
                
                // Pastikan response sukses dan data tidak bernilai null
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    
                    // Jika data kosong, tampilkan pesan informasi tidak ada data
                    if (response.body().getData().isEmpty()) {
                        tvContent.setVisibility(View.VISIBLE);
                        tvContent.setText("Tidak ada data RPS.");
                    } else {
                        // Jika ada data, masukkan ke adapter agar UI diperbarui
                        adapter.setRpsList(response.body().getData());
                    }
                } else {
                    // Jika terjadi kegagalan (misalnya response code error), tampilkan teks pesan error
                    tvContent.setVisibility(View.VISIBLE);
                    tvContent.setText("Gagal mengambil data RPS.");
                }
            }

            // Callback ketika request gagal dilakukan, biasanya masalah jaringan (tidak ada koneksi) atau timeout
            @Override
            public void onFailure(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> call, Throwable t) {
                // Sembunyikan progress bar dan tampilkan pesan kesalahan
                progressBar.setVisibility(View.GONE);
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText("Error Koneksi: " + t.getMessage());
            }
        });
    }
}
