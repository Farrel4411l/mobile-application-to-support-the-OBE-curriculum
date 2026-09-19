package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Prodi;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Kelas ProdiDetailActivity
 * <p>
 * Kelas ini bertanggung jawab untuk menampilkan detail dari suatu Program Studi (Prodi).
 * Activity ini menggunakan ViewPager2 dan TabLayout untuk membagi detail menjadi dua bagian (Tab):
 * 1. Profil Lulusan (ProfilLulusanFragment)
 * 2. CPL / Capaian Pembelajaran Lulusan (CplFragment)
 * 
 * Interaksi dengan fitur utama: Saat pengguna mengklik salah satu item Prodi pada daftar,
 * aplikasi akan berpindah ke Activity ini dengan membawa objek Prodi yang dipilih.
 */
public class ProdiDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengaktifkan fitur Edge-To-Edge agar tampilan aplikasi membentang hingga batas layar
        EdgeToEdge.enable(this);
        // Menetapkan layout XML activity_prodi_detail sebagai antarmuka utama kelas ini
        setContentView(R.layout.activity_prodi_detail);

        // Menghubungkan komponen toolbar dari XML
        MaterialToolbar toolbar = findViewById(R.id.toolbarProdiDetail);
        // Menetapkan toolbar yang diambil sebagai ActionBar (untuk navigasi dan judul)
        setSupportActionBar(toolbar);
        // Jika ActionBar tidak bernilai null, kita atur tombol 'kembali'
        if (getSupportActionBar() != null) {
            // Mengaktifkan tombol panah kembali (HomeAsUp)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Menambahkan listener agar ketika tombol kembali di toolbar ditekan, Activity ini akan ditutup (finish)
        toolbar.setNavigationOnClickListener(v -> finish());

        // Menghubungkan TextView untuk menampilkan nama Prodi
        TextView tvNama = findViewById(R.id.tvDetailNamaProdi);
        // Menghubungkan TabLayout untuk navigasi antar fragment
        TabLayout tabLayout = findViewById(R.id.tabLayoutProdi);
        // Menghubungkan ViewPager2 yang akan menampung fragment
        ViewPager2 viewPager = findViewById(R.id.viewPagerProdi);

        // Mengambil objek Prodi yang dikirim dari Activity sebelumnya melalui intent
        Prodi prodi = (Prodi) getIntent().getSerializableExtra("EXTRA_PRODI");
        // Inisialisasi variabel untuk menampung ID Prodi
        String idProdi = "";

        // Jika objek prodi berhasil diterima
        if (prodi != null) {
            // Set teks pada TextView dengan nama Prodi
            tvNama.setText(prodi.getNamaProdi());
            // Menyimpan ID Prodi untuk dikirimkan ke dalam adapter fragment
            idProdi = prodi.getIdProdi();
        }

        // Membuat instance adapter untuk ViewPager2, meneruskan idProdi agar fragment dapat mengambil data secara spesifik
        ProdiPagerAdapter pagerAdapter = new ProdiPagerAdapter(this, idProdi);
        // Memasang adapter ke ViewPager2
        viewPager.setAdapter(pagerAdapter);

        // TabLayoutMediator berfungsi menghubungkan TabLayout dengan ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Jika posisi tab adalah 0, atur judulnya menjadi "Profil Lulusan"
            if (position == 0) tab.setText("Profil Lulusan");
            // Jika posisi tab adalah 1, atur judulnya menjadi "CPL"
            else tab.setText("CPL");
        }).attach(); // .attach() untuk mulai menyinkronkan data antar keduanya

        // Menyesuaikan padding toolbar agar tidak tertutup oleh system bar (status bar pada bagian atas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbarProdiDetail), (v, insets) -> {
            // Mengambil insets system bars (ukuran area yang menutupi bagian atas layar)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Mengubah padding top pada toolbar sebesar insets top
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
    }
}
