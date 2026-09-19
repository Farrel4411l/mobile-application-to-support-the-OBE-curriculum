package com.example.projectfarrelmobileprogramming.ui.mapping;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MappingActivity adalah kelas untuk menampilkan berbagai jenis pemetaan akademik 
 * seperti relasi Bahan Kajian ke Mata Kuliah (BK-MK), Bahan Kajian ke Capaian Pembelajaran (BK-CPL), dsb.
 * Kegiatan ini mengambil datanya secara dinamis melalui antarmuka Retrofit dari server API.
 */
public class MappingActivity extends AppCompatActivity {

    // Spinner adalah semacam dropdown menu tempat pengguna bisa memilih jenis filter pemetaan.
    private Spinner spinnerMappingType;
    // MaterialButton untuk men-trigger atau memulai eksekusi permintaan pengambilan data
    private MaterialButton btnLoadMapping;
    // ProgressBar untuk menunjukkan putaran loading selama aplikasi menunggu respon dari API server
    private ProgressBar progressBarMapping;
    // TextView digunakan untuk menampilkan rangkuman atau status pengambilan data seperti sukses/gagal
    private TextView tvMappingResult;

    /**
     * Metode ini otomatis dipanggil pertama kali saat aktivitas MappingActivity dibuat (dimulai).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Melanjutkan lifecycle standar Android untuk activity pembuatan.
        super.onCreate(savedInstanceState);
        // Menyalakan fitur edge-to-edge UI sehingga layout terlihat luas mencakup keseluruhan layar
        EdgeToEdge.enable(this);
        // Mengarahkan class ini menggunakan tampilan desain (UI) dari XML "activity_mapping"
        setContentView(R.layout.activity_mapping);

        // Menghubungkan objek Toolbar pada class dengan elemen Toolbar di layar
        MaterialToolbar toolbar = findViewById(R.id.toolbarMapping);
        // Memberi tahu Android bahwa toolbar ini harus bertindak sebagai sistem default Action Bar di Activity ini
        setSupportActionBar(toolbar);
        // Mengamankan kode dengan melakukan null-check pada object supportActionBar
        if (getSupportActionBar() != null) {
            // Memunculkan tombol panah mundur pada action bar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Menampilkan ikon Home agar bisa ditekan
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Membuat event aksi menutup halaman (finish) saat ikon back/kembali ditekan, 
        // sehingga pengguna kembali ke menu sebelumnya.
        toolbar.setNavigationOnClickListener(v -> finish());

        // Menyambungkan deklarasi class di atas ke komponen dalam file layout melalui ID masing-masing
        spinnerMappingType = findViewById(R.id.spinnerMappingType);
        btnLoadMapping = findViewById(R.id.btnLoadMapping);
        progressBarMapping = findViewById(R.id.progressBarMapping);
        tvMappingResult = findViewById(R.id.tvMappingResult);

        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR DROPDOWN (SPINNER) 🔥
        // =========================================================================
        // Penjelasan: Spinner membutuhkan Adapter (ArrayAdapter) untuk menghubungkan kumpulan data dengan komponen UI dropdown.
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // 1. Data: String[] options = {"A", "B"};
        // 2. Adapter: ArrayAdapter<String> adapter = new ArrayAdapter<>(context, layout, options);
        // 3. Pasang Adapter: spinner.setAdapter(adapter);
        // 4. Ambil Nilai: spinner.getSelectedItemPosition() atau gunakan setOnItemSelectedListener.
        // =========================================================================
        // Mendeklarasikan sebuah list pilihan menggunakan Array String statis untuk tipe-tipe pemetaan.
        String[] mappingOptions = {"Pemetaan BK-MK", "Pemetaan BK-CPL", "Pemetaan CPMK-MK", "Pemetaan CPL-SubCPMK"}; // 🔥 INTI: Data untuk Spinner
        // Membuat Adapter bawaan untuk mencocokkan Array tadi menjadi komponen list untuk dropdown Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mappingOptions); // 🔥 INTI: Inisialisasi ArrayAdapter
        // Mengaplikasikan adapter yang telah dibuat tersebut ke dalam spinnerMappingType sehingga muncul item dropdown
        spinnerMappingType.setAdapter(adapter); // 🔥 INTI: Memasang adapter ke Spinner

        // Memberikan klik listener ke tombol agar setiap kali dipencet, metode pemuatan (loadMappingData) dieksekusi 
        // parameter yang diberikan ke dalam fungsi tersebut adalah index item yang saat ini dipilih oleh user di spinner
        btnLoadMapping.setOnClickListener(v -> loadMappingData(spinnerMappingType.getSelectedItemPosition())); // 🔥 INTI: Mengambil posisi item yang dipilih dari Spinner

        // Mengatur padding tampilan agar Toolbar tidak tumpang tindih (overlap) dengan Status Bar pada OS (seperti jam & sinyal).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbarMapping), (v, insets) -> {
            // Membaca jarak tepi spesifik ke barisan sistem Android (WindowInsets).
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Mengeset margin atas menggunakan ukuran dari sistem (systemBars.top), sehingga konten tampil aman dibawah status bar
            v.setPadding(0, systemBars.top, 0, 0);
            // Mengembalikan nilai kembalian insets original
            return insets;
        });
    }

    /**
     * Meminta dan mengambil data relasi (pemetaan) spesifik dari web service (API) sesuai index opsi filter
     * @param position merupakan indeks menu dropdown pemetaan yang dipilih user (0-3).
     */
    private void loadMappingData(int position) {
        // Menyalakan (menampilkan) animasi progress loading karena aplikasi akan mulai mengambil data dari API
        progressBarMapping.setVisibility(View.VISIBLE);
        // Mengosongkan text error maupun success log yang mungkin sebelumnya sedang ditampilkan
        tvMappingResult.setText("");
        
        // Inisialisasi RecyclerView dari UI yang akan menampung hasil rentetan data dari server
        androidx.recyclerview.widget.RecyclerView rvMapping = findViewById(R.id.rvMapping);
        // Menyembunyikan RecyclerView untuk sementara ketika memuat data agar tidak terlihat aneh (blank UI)
        rvMapping.setVisibility(View.GONE);
        // Menentukan aturan orientasi (layoutManager) menjadi vertikal ke bawah (LinearLayoutManager)
        rvMapping.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        // Mendeklarasikan instance Adapter khusus mapping 
        MappingAdapter adapter = new MappingAdapter();
        // Mengikat Adapter tadi ke dalam RecyclerView
        rvMapping.setAdapter(adapter);

        // Membuat instance ApiService dari Retrofit via kelas bantu (helper) ApiClient.getClient() 
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // Deklarasi generic Call objek untuk penampung request Retrofit
        Call<ApiResponse<Object>> call;
        
        // Mempersiapkan variabel pembeda konfigurasi string dan tag, sebagai referensi identitas data pemetaan
        String srcKey, tgtKey, srcLabel, tgtLabel;

        // Struktur pencabangan berdasarkan posisi item dropdown (0 hingga 3)
        switch (position) {
            case 0: 
                // Jika posisi 0, memanggil endpoint Pemetaan BK-MK (Bahan Kajian vs Mata Kuliah) dengan limit=100
                call = apiService.getPemetaanBkMk(100); 
                // Menentukan key JSON yang sesuai dengan properti JSON server untuk endpoint ini
                srcKey = "id_bk"; tgtKey = "id_mk"; 
                // Menentukan Label UI yang bakal dibaca user secara visual
                srcLabel = "Bahan Kajian"; tgtLabel = "Mata Kuliah";
                break;
            case 1: 
                // Jika posisi 1, memanggil endpoint Pemetaan BK-CPL (Bahan Kajian vs CPL) dengan limit=100
                call = apiService.getPemetaanBkCpl(100); 
                srcKey = "id_bk"; tgtKey = "id_cpl"; 
                srcLabel = "Bahan Kajian"; tgtLabel = "CPL";
                break;
            case 2: 
                // Jika posisi 2, memanggil endpoint Pemetaan CPMK-MK (CPMK vs Mata Kuliah) dengan limit=100
                call = apiService.getPemetaanCpmkMk(100); 
                srcKey = "id_cpmk"; tgtKey = "id_mk"; 
                srcLabel = "CPMK"; tgtLabel = "Mata Kuliah";
                break;
            case 3: 
                // Jika posisi 3, memanggil endpoint Pemetaan CPL-SubCPMK dengan limit=100
                call = apiService.getPemetaanCplSubCpmk(100); 
                srcKey = "id_cpl"; tgtKey = "id_subcpmk"; 
                srcLabel = "CPL"; tgtLabel = "Sub-CPMK";
                break;
            default: 
                // Skema perlindungan dasar (default) ke skenario awal apabila terdeteksi anomali pada indeks.
                call = apiService.getPemetaanBkMk(100);
                srcKey = "id_bk"; tgtKey = "id_mk"; 
                srcLabel = "Bahan Kajian"; tgtLabel = "Mata Kuliah";
        }

        // Melaksanakan eksekusi call (Request Jaringan API) di latar belakang alias asynchronous 
        call.enqueue(new Callback<ApiResponse<Object>>() {
            // Callback otomatis dipanggil saat HTTP response / balasan dari server (meski berhasil maupun fail misal 404/500) kembali ke aplikasi
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                // Menghilangkan status loading spinner karena kita sudah mendapatkan balasan dari internet.
                progressBarMapping.setVisibility(View.GONE);
                
                // Kondisi verifikasi jika request HTTP sukses (200 OK) beserta balasan struktur Body yang normal tidak null dan data List pun tidak null
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    // Menyimpan bentuk List data tersebut ke dalam array generic Object List. 
                    // Menggunakan generic dikarenakan data mapping API tersebut bervariasi bergantung opsinya
                    java.util.List<Object> dataList = response.body().getData();
                    
                    // Kalau ternyata payload (jumlah elemen data) tidak ada atau kosong
                    if (dataList.isEmpty()) {
                        // Tampilkan pesan kesalahan di layar TextView agar pengguna maklum data yang mereka minta tidak ada dari server
                        tvMappingResult.setText("Tidak ada data pemetaan untuk filter ini.");
                    } else {
                        // Namun jika datanya terisi dan valid, kita tampilkan ulang list komponen RecyclerView yang tadi di-hide
                        rvMapping.setVisibility(View.VISIBLE);
                        // Melempar segala data logik (dataList, key, label) ke Adapter agar Adapter tahu cara menerjemahkannya di UI
                        adapter.setMappingData(dataList, srcKey, tgtKey, srcLabel, tgtLabel);
                        // Mengupdate teks penjelas ukuran jumlah data di bagian bawah layar bagi pengguna
                        tvMappingResult.setText("Menampilkan " + dataList.size() + " data pemetaan:");
                    }
                } else {
                    // Kondisi else ketika server mengirim error HTTP (misal 500 error server) yang bukan 200/Sukses
                    // Kita akan mencetak pesannya berdasar objek response error agar gampang di tracking.
                    tvMappingResult.setText("Gagal memuat data: " + response.message());
                }
            }

            // Callback apabila permintaan itu sendiri yang tidak berhasil terkirim atau ditanggapi 
            // misalnya saat internet HP mati, koneksi Timeout, host tidak ditemukan, kesalahan konversi JSON, dsb.
            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                // Mematikan visual loading
                progressBarMapping.setVisibility(View.GONE);
                // Menyampaikan keterangan spesifik terkait alasan gagal terhubung menggunakan Exception Messages (Throwable t)
                tvMappingResult.setText("Koneksi Error: " + t.getMessage());
            }
        });
    }
}
