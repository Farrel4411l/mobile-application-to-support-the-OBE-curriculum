package com.example.projectfarrelmobileprogramming.ui.rtm;

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
 * Kelas RtmActivity
 * <p>
 * Berfungsi untuk menampilkan layar RTM (Rencana Tugas Mandiri).
 * Activity ini melakukan panggilan ke backend melalui library Retrofit
 * untuk mendownload data RTM dan memindahkannya ke antarmuka pengguna
 * menggunakan daftar berbasis RecyclerView.
 */
public class RtmActivity extends AppCompatActivity {

    // Menampung UI untuk loading spinner
    private ProgressBar progressBar;
    // Menampung UI berupa tulisan jika data list kosong atau terjadi masalah jaringan
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengaktifkan layar penuh sampai edge system (status bar transparan)
        EdgeToEdge.enable(this);
        // Menetapkan desain/struktur XML dari res/layout/activity_rtm.xml
        setContentView(R.layout.activity_rtm);

        // Pengaturan toolbar: mencocokkan widget toolbar dengan komponen supportActionBar
        MaterialToolbar toolbar = findViewById(R.id.toolbarRtm);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // Memunculkan tombol panah back (kembali) di kiri atas
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Listener, pada saat ikon kembali ditekan, hapus (tutup) Activity ini (kembali ke sebelumnya)
        toolbar.setNavigationOnClickListener(v -> finish());

        // Inisialisasi binding widget dari XML ke variabel Java
        progressBar = findViewById(R.id.progressBarRtm);
        tvContent = findViewById(R.id.tvContentRtm);

        // Menambahkan padding dari insets window agar teks/layout bawah tidak bertumpuk dengan Navbar hp
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvContentRtm), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(16, 16, 16, systemBars.bottom + 16);
            return insets;
        });

        // Menarik data RTM dari server API
        loadRtmData();
    }

    /**
     * Memanggil antarmuka API menggunakan Retrofit dan mengisi RecyclerView 
     * dengan balasan (respons) berisi data dari Endpoint RTM.
     */
    private void loadRtmData() {
        // Tampilkan indikator proses dan sembunyikan tulisan empty state
        progressBar.setVisibility(View.VISIBLE);
        tvContent.setVisibility(View.GONE);
        
        // Mempersiapkan struktur RecyclerView di layout menjadi linier (satu kolom, ke bawah)
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvRtm);
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        
        // Membuat dan menyetel Adapter RtmAdapter
        RtmAdapter adapter = new RtmAdapter();
        rv.setAdapter(adapter);

        // Membuat instance class ApiService dari klien Retrofit untuk mulai request
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        
        // Memanggil request getRtm(limit=100) dan dieksekusi secara asinkron di thread terpisah (enqueue)
        apiService.getRtm(100).enqueue(new Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>>() {
            
            // Dipanggil secara otomatis ketika response HTTP balik dari server API
            @Override
            public void onResponse(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> call, Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> response) {
                // Sembunyikan lingkaran progress
                progressBar.setVisibility(View.GONE);
                
                // Jika request berhasil, code balasan 200/2xx, dan data list di body tidak null
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    
                    // Kalau list RTM ternyata kosong
                    if (response.body().getData().isEmpty()) {
                        tvContent.setVisibility(View.VISIBLE);
                        tvContent.setText("Tidak ada data Rencana Tugas Mandiri.");
                    } else {
                        // Kalau list berisi data, berikan kepada Adapter supaya di-update tampilannya
                        adapter.setRtmList(response.body().getData());
                    }
                } else {
                    // Masuk kondisi ini jika balasan HTTP menunjukkan error (400, 500, dll)
                    tvContent.setVisibility(View.VISIBLE);
                    String errBody = "";
                    // Berusaha membaca detail alasan dari errorBody json (jika ada) untuk debugging
                    try { if(response.errorBody() != null) errBody = response.errorBody().string(); } catch(Exception e){}
                    // Tampilkan pesan kegagalan beserta code HTTP-nya
                    tvContent.setText("Gagal mengambil RTM. Code: " + response.code() + " " + errBody);
                }
            }

            // Dipanggil otomatis apabila tidak dapat menghubungi server karena masalah jaringan atau timeout
            @Override
            public void onFailure(Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> call, Throwable t) {
                // Matikan spinner loading dan tampilkan penyebab (Exception error message)
                progressBar.setVisibility(View.GONE);
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText("Error Koneksi: " + t.getMessage());
            }
        });
    }
}
