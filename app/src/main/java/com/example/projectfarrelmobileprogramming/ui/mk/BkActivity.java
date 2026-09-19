package com.example.projectfarrelmobileprogramming.ui.mk;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.BahanKajian;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;
import com.google.android.material.appbar.MaterialToolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Kelas BkActivity.
 * Activity ini bertanggung jawab untuk menampilkan daftar Bahan Kajian (BK).
 * Terhubung dengan backend/API untuk mengambil data Bahan Kajian melalui Retrofit.
 * Menggunakan RecyclerView untuk menampilkan data dalam bentuk list.
 */
public class BkActivity extends AppCompatActivity {

    // Mendeklarasikan ProgressBar untuk indikator proses memuat data dari API
    private ProgressBar progressBar;
    // Mendeklarasikan TextView untuk menampilkan pesan jika data kosong atau terjadi error
    private TextView tvEmpty;
    // Mendeklarasikan RecyclerView untuk menampilkan list data bahan kajian
    private RecyclerView rvBk;
    // Mendeklarasikan BkAdapter untuk menghubungkan data dengan tampilan item RecyclerView
    private BkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Memanggil implementasi onCreate dari superclass (AppCompatActivity)
        super.onCreate(savedInstanceState);
        
        // Mengaktifkan fitur Edge-to-Edge agar tampilan aplikasi memenuhi layar hingga area sistem (status bar/navigation bar)
        EdgeToEdge.enable(this);
        
        // Menetapkan layout activity_bk.xml sebagai antarmuka (UI) untuk Activity ini
        setContentView(R.layout.activity_bk);

        // Menghubungkan komponen MaterialToolbar dari layout dengan ID toolbarBk
        MaterialToolbar toolbar = findViewById(R.id.toolbarBk);
        // Mengatur toolbar yang telah dibuat sebagai ActionBar utama Activity ini
        setSupportActionBar(toolbar);
        // Mengecek apakah ActionBar berhasil disetel, lalu mengaktifkan tombol "kembali" (Up button)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Menambahkan listener agar ketika tombol kembali ditekan, activity ini ditutup (finish)
        toolbar.setNavigationOnClickListener(v -> finish());

        // Menginisialisasi komponen UI dari layout berdasarkan ID masing-masing
        progressBar = findViewById(R.id.progressBarBk);
        tvEmpty = findViewById(R.id.tvEmptyBk);
        rvBk = findViewById(R.id.rvBk);

        // Mengatur LayoutManager pada RecyclerView menggunakan LinearLayoutManager agar data ditampilkan secara vertikal
        rvBk.setLayoutManager(new LinearLayoutManager(this));
        // Menginisialisasi objek adapter untuk RecyclerView
        adapter = new BkAdapter();
        // Memasang adapter ke RecyclerView agar dapat merender data bahan kajian
        rvBk.setAdapter(adapter);

        // Memanggil fungsi untuk mulai memuat data dari API
        loadData();

        // Menerapkan WindowInsetsListener pada layout utama untuk menyesuaikan padding
        // agar konten tidak tertutup oleh system bars (seperti status bar di atas atau navigasi di bawah)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainBk), (v, insets) -> {
            // Mengambil ukuran dari system bars
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Menetapkan padding atas dan bawah sesuai dengan ukuran system bars
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            // Mengembalikan insets yang sudah diterapkan
            return insets;
        });
    }

    /**
     * Fungsi untuk memuat data Bahan Kajian dari API backend.
     */
    private void loadData() {
        // Menampilkan ProgressBar saat proses memuat data dimulai
        progressBar.setVisibility(View.VISIBLE);
        // Menyembunyikan TextView pesan kosong/error
        tvEmpty.setVisibility(View.GONE);
        // Menyembunyikan RecyclerView selama data belum siap
        rvBk.setVisibility(View.GONE);

        // Membuat instance ApiService menggunakan Retrofit (melalui ApiClient) untuk melakukan pemanggilan jaringan
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        
        // Memanggil endpoint untuk mengambil data Bahan Kajian dengan limit 100 secara asinkron
        apiService.getBahanKajian(100).enqueue(new Callback<ApiResponse<BahanKajian>>() {
            @Override
            public void onResponse(Call<ApiResponse<BahanKajian>> call, Response<ApiResponse<BahanKajian>> response) {
                // Menyembunyikan ProgressBar setelah mendapat respons dari server
                progressBar.setVisibility(View.GONE);
                
                // Memeriksa apakah request HTTP berhasil dan response body (beserta datanya) tidak bernilai null
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    // Jika list data dari server kosong
                    if (response.body().getData().isEmpty()) {
                        // Tampilkan pesan kosong pada UI
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText("Tidak ada data Bahan Kajian.");
                    } else {
                        // Jika ada data, tampilkan RecyclerView
                        rvBk.setVisibility(View.VISIBLE);
                        // Mengirimkan list data ke adapter untuk ditampilkan
                        adapter.setBkList(response.body().getData());
                    }
                } else {
                    // Jika response gagal atau body null, tampilkan pesan error
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Gagal mengambil data Bahan Kajian.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BahanKajian>> call, Throwable t) {
                // Menyembunyikan ProgressBar saat terjadi error/kegagalan koneksi
                progressBar.setVisibility(View.GONE);
                // Menampilkan TextView untuk menunjukkan pesan error
                tvEmpty.setVisibility(View.VISIBLE);
                // Menetapkan teks pesan error yang didapat dari exception
                tvEmpty.setText("Koneksi Error: " + t.getMessage());
            }
        });
    }
}
