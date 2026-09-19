package com.example.projectfarrelmobileprogramming.ui.mk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * Kelas MkDetailActivity.
 * Activity yang bertugas menampilkan informasi spesifik / detil dari sebuah Mata Kuliah (MK).
 * Activity ini akan menerima data MK yang dipassing lewat Intent (ekstra),
 * kemudian juga akan mengambil dan menampilkan list Capaian Pembelajaran Mata Kuliah (CPMK) yang berelasi.
 */
public class MkDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Memanggil siklus hidup standar (super) Activity saat dibuat
        super.onCreate(savedInstanceState);
        // Menggunakan gaya layar penuh agar UI mencapai tepi ujung HP (Edge-to-Edge)
        EdgeToEdge.enable(this);
        // Memasang UI XML layout (activity_mk_detail) pada Activity ini
        setContentView(R.layout.activity_mk_detail);

        // Inisialisasi Material Toolbar untuk header di bagian atas
        MaterialToolbar toolbar = findViewById(R.id.toolbarMkDetail);
        // Atur agar toolbar bertindak sebagai Action Bar
        setSupportActionBar(toolbar);
        // Apabila setup Action Bar berhasil
        if (getSupportActionBar() != null) {
            // Aktifkan ikon navigasi "kembali" (tombol panah di kiri)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Hapus teks judul (title) default dari toolbar
            getSupportActionBar().setTitle("");
        }
        // Atur action apabila tombol "kembali" ditekan, maka akhiri activity ini dan kembali ke Activity sebelumnya
        toolbar.setNavigationOnClickListener(v -> finish());

        // Deklarasi dan mapping komponen-komponen TextView bagian atas untuk detail MK
        TextView tvNama = findViewById(R.id.tvDetailNamaMk);
        TextView tvKode = findViewById(R.id.tvDetailKodeMk);
        TextView tvSks = findViewById(R.id.tvDetailSks);
        TextView tvSemester = findViewById(R.id.tvDetailSemester);

        // Mendapatkan data obyek MK yang dikirim dari Intent melalui tipe Serializable dengan kata kunci "EXTRA_MK"
        MataKuliah mk = (MataKuliah) getIntent().getSerializableExtra("EXTRA_MK");

        // Jika objek MK sukses didapatkan (tidak null)
        if (mk != null) {
            // Tampilkan properti nama MK ke TextView Nama
            tvNama.setText(mk.getNamaMk());
            // Tampilkan properti ID/Kode MK ke TextView Kode
            tvKode.setText(mk.getIdMk());
            // Tampilkan properti bobot SKS MK
            tvSks.setText(String.valueOf(mk.getSks()));
            // Tampilkan properti semester pada MK ini
            tvSemester.setText(mk.getSemester());
            
            // Selanjutnya, jalankan method ini untuk mengambil data CPMK dari API, menggunakan relasi idMk
            loadCpmk(mk.getIdMk());
        }

        // Tangani masalah padding agar komponen tidak tumpang tindih dengan Navigation Bar ponsel / Status Bar atas
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbarMkDetail), (v, insets) -> {
            // Mengambil margin inset khusus untuk bar sistem
            Insets systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
            // Terapkan padding di sisi atas toolbar sebanyak area status bar (jam, baterai hp)
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
    }

    /**
     * Memuat daftar CPMK berdasarkan ID dari Mata Kuliah saat ini.
     * @param idMk String yang mewakili ID Mata Kuliah referensi.
     */
    private void loadCpmk(String idMk) {
        // Deklarasi dan mapping loading indicator untuk bagian list CPMK
        android.widget.ProgressBar progressBar = findViewById(R.id.progressBarCpmk);
        // Deklarasi teks error atau pesan kosong untuk list CPMK
        TextView tvKosong = findViewById(R.id.tvCpmkKosong);
        // Deklarasi RecyclerView dari layout untuk memunculkan list CPMK
        androidx.recyclerview.widget.RecyclerView rv = findViewById(R.id.rvCpmk);
        
        // Memerintahkan list agar tampilannya berjalan turun/linear
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        // Menginstansiasi adapter khusus list CPMK
        CpmkAdapter adapter = new CpmkAdapter();
        // Memasangkan adapter ke RecyclerView
        rv.setAdapter(adapter);
        
        // Atur state awal: Tampilkan loading
        progressBar.setVisibility(android.view.View.VISIBLE);
        // Sembunyikan teks info
        tvKosong.setVisibility(android.view.View.GONE);
        // Sembunyikan RecyclerView
        rv.setVisibility(android.view.View.GONE);

        // Membuat pemanggil ApiService dengan retrofit
        com.example.projectfarrelmobileprogramming.data.network.ApiService apiService = 
                com.example.projectfarrelmobileprogramming.data.network.ApiClient.getClient().create(com.example.projectfarrelmobileprogramming.data.network.ApiService.class);
                
        // Lakukan pemanggilan jaringan asynchronous. Ambil daftar CPMK dengan limit 100
        // Karena endpoint (kemungkinan) memberikan seluruh daftar CPMK, maka kita menggunakan filter manual berdasar id_mk di sisi client.
        apiService.getCpmk(null, 100).enqueue(new retrofit2.Callback<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>>() {
            @Override
            public void onResponse(retrofit2.Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> call, retrofit2.Response<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> response) {
                // Sembunyikan layar loading karena sudah mendapat respon API
                progressBar.setVisibility(android.view.View.GONE);
                
                // Pastikan respon API sukses di-handle dan tidak memberikan body/data yang bernilai null
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    // Menyimpan daftar penuh (semua) data CPMK dari server
                    java.util.List<com.example.projectfarrelmobileprogramming.data.model.Cpmk> allCpmk = response.body().getData();
                    // Menyiapkan wadah kosong untuk CPMK hasil saringan berdasar mk_id saat ini
                    java.util.List<com.example.projectfarrelmobileprogramming.data.model.Cpmk> filteredCpmk = new java.util.ArrayList<>();
                    
                    // Lakukan pengecekan iterasi (looping) tiap elemen Cpmk
                    for (com.example.projectfarrelmobileprogramming.data.model.Cpmk c : allCpmk) {
                        // Cek apakah CPMK yang di-loop ini milik dari mata kuliah yang id-nya sama dengan `idMk`
                        if (idMk.equals(c.getIdMk())) {
                            // Jika ya, tambahkan kedalam list hasil filter
                            filteredCpmk.add(c);
                        }
                    }
                    
                    // Mengecek bila list hasil saringan ternyata kosong
                    if (filteredCpmk.isEmpty()) {
                        // Tampilkan pesan kosong pada layer
                        tvKosong.setVisibility(android.view.View.VISIBLE);
                    } else {
                        // Apabila terdapat data filter, perlihatkan RecyclerView
                        rv.setVisibility(android.view.View.VISIBLE);
                        // Melempar list filter ke adapter untuk segera dirender
                        adapter.setCpmkList(filteredCpmk);
                    }
                } else {
                    // Jika sukses API false (misal error 400 atau 500), tampilkan error gagal
                    tvKosong.setVisibility(android.view.View.VISIBLE);
                    tvKosong.setText("Gagal memuat CPMK");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.example.projectfarrelmobileprogramming.data.model.ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> call, Throwable t) {
                // Apabila terjadi error koneksi ke server, matikan loading progressbar
                progressBar.setVisibility(android.view.View.GONE);
                // Perlihatkan text info error ke pengguna
                tvKosong.setVisibility(android.view.View.VISIBLE);
                // Isi text info tersebut dengan pesan Exception yang bersangkutan (t.getMessage())
                tvKosong.setText("Error koneksi: " + t.getMessage());
            }
        });
    }
}
