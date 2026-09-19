package com.example.projectfarrelmobileprogramming;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.cardview.widget.CardView;
import android.widget.Toast;

/**
 * MainActivity adalah halaman beranda utama (Home Screen) aplikasi yang berisi
 * menu atau dashboard berupa Card-card navigasi.
 * Class ini berfungsi menangani aksi klik dari pengguna pada setiap CardView,
 * untuk mengarahkan pengguna ke Activity yang sesuai (seperti halaman Prodi, Mata Kuliah, dsb).
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Method onCreate adalah method yang pertama kali dipanggil saat Activity dibuat.
     * Seluruh inisialisasi layout (UI) dan aksi antarmuka pengguna ditempatkan di sini.
     */
    // =========================================================================
    // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR ACTIVITY 🔥
    // =========================================================================
    // Penjelasan: Activity adalah komponen penyusun UI utama di Android. Harus ada `onCreate` untuk
    // titik awal inisialisasi, `setContentView` untuk memasang layout XML ke Activity, dan
    // `Intent` untuk berpindah/navigasi ke Activity lain.
    // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
    // 1. @Override protected void onCreate(Bundle savedInstanceState)
    // 2. setContentView(R.layout.nama_layout);
    // 3. Intent intent = new Intent(ContextAsal.this, ActivityTujuan.class); startActivity(intent);
    // =========================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 🔥 INTI: Method pertama yang dipanggil saat Activity jalan
        super.onCreate(savedInstanceState);
        
        // Mengaktifkan fitur Edge-to-Edge agar layout bisa merentang ke bawah status bar dan navigation bar perangkat.
        EdgeToEdge.enable(this);
        
        // Menetapkan layout XML 'activity_main' sebagai tampilan antarmuka (View) dari Activity ini.
        setContentView(R.layout.activity_main); // 🔥 INTI: Menghubungkan layout XML dengan Activity
        
        // Mencari dan mengikat elemen UI (CardView) dari XML ke variabel Java 
        // dengan menggunakan ID (R.id.xxx) dari setiap elemen tersebut.
        CardView cardProdi = findViewById(R.id.cardProdi);
        CardView cardMk = findViewById(R.id.cardMk);
        CardView cardMapping = findViewById(R.id.cardMapping);
        CardView cardRps = findViewById(R.id.cardRps);
        CardView cardRtm = findViewById(R.id.cardRtm);
        CardView cardAsesmen = findViewById(R.id.cardAsesmen);
        CardView cardSettings = findViewById(R.id.cardSettings);
        CardView cardLocation = findViewById(R.id.cardLocation);
        CardView cardNote = findViewById(R.id.cardNote);

        // Menambahkan listener klik (OnClickListener) untuk masing-masing kartu.
        // Jika pengguna menyentuh/klik sebuah kartu, maka baris di dalamnya akan dijalankan.

        // Listener untuk menu Program Studi
        cardProdi.setOnClickListener(v -> {
            // Intent digunakan untuk berpindah dari MainActivity ke ProdiActivity
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.prodi.ProdiActivity.class); // 🔥 INTI: Membuat Intent untuk pindah Activity
            // Memulai proses perpindahan Activity
            startActivity(intent); // 🔥 INTI: Mengeksekusi Intent
        });
        
        // Listener untuk menu Mata Kuliah
        cardMk.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.mk.MkActivity.class);
            startActivity(intent);
        });
        
        // Listener untuk menu Pemetaan (Mapping)
        cardMapping.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.mapping.MappingActivity.class);
            startActivity(intent);
        });
        
        // Listener untuk menu RPS
        cardRps.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.rps.RpsActivity.class);
            startActivity(intent);
        });
        
        // Listener untuk menu RTM
        cardRtm.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.rtm.RtmActivity.class);
            startActivity(intent);
        });
        
        // Listener untuk menu Asesmen
        cardAsesmen.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.asesmen.AsesmenActivity.class);
            startActivity(intent);
        });
        
        // Listener untuk menu Settings (Pengaturan)
        cardSettings.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.settings.SettingsActivity.class);
            startActivity(intent);
        });
        
        // Listener untuk menu Lokasi (Peta/Geolokasi)
        cardLocation.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.location.LocationActivity.class);
            startActivity(intent);
        });
        
        // Listener untuk menu Catatan (Note)
        cardNote.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, com.example.projectfarrelmobileprogramming.ui.note.NoteActivity.class);
            startActivity(intent);
        });

        // Listener ini untuk menangani insets jendela sistem (seperti padding status bar & navigation bar)
        // Hal ini berguna saat menggunakan EdgeToEdge agar komponen kita tidak tertutup oleh tombol navigasi hp.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Mendapatkan margin/padding dari bar sistem (misalnya: bilah navigasi bawah, status bar atas)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Menerapkan padding hanya pada bagian bawah (bottom) agar tidak menutupi tampilan dasar 
            // tetapi membiarkan header tetap sampai ujung atas layar (EdgeToEdge).
            v.setPadding(0, 0, 0, systemBars.bottom);
            // Mengembalikan insets tersebut
            return insets;
        });
    }

    /**
     * Method tambahan/utilitas untuk menampilkan notifikasi singkat (Toast) di layar.
     * Saat ini belum dipakai secara spesifik di onCreate, namun bisa digunakan untuk debug
     * atau peringatan bagi pengguna (misal: "Fitur belum tersedia").
     *
     * @param message Pesan singkat berbentuk String yang ingin ditampilkan di layar.
     */
    private void showToast(String message) {
        // Membuat Toast dengan teks message lalu langsung menampilkannya (.show())
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}