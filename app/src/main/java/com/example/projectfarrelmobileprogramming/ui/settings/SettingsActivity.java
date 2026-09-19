package com.example.projectfarrelmobileprogramming.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfarrelmobileprogramming.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Kelas SettingsActivity
 * <p>
 * Activity yang bertugas sebagai fitur profil/pengaturan aplikasi lokal.
 * Pengguna (mahasiswa) dapat mengisikan Nama dan NIM mereka.
 * Data diActivity ini akan disimpan secara persisten di penyimpanan perangkat (SharedPreferences), 
 * sehingga tidak hilang meski aplikasinya tertutup (closed) lalu dibuka kembali.
 */
public class SettingsActivity extends AppCompatActivity {

    // Kolom input teks untuk mengisikan Nama dan NIM
    private TextInputEditText etName, etNim;
    // Tombol untuk aksi menyimpan (Save) profil ke local storage
    private MaterialButton btnSaveSettings;
    // TextView sebagai display data nama dan NIM yang sedang tersimpan
    private TextView tvCurrentProfile;
    
    // Antarmuka SharedPreferences bawaan Android, untuk menyimpan variabel key-value sederhana di file device
    private SharedPreferences sharedPreferences;
    
    // Nama konstan file storage lokal SharedPreferences untuk pengaturan profil
    private static final String PREF_NAME = "UserPrefs";
    // Konstan identifier key/kata-kunci untuk nama pengguna
    private static final String KEY_NAME = "user_name";
    // Konstan identifier key/kata-kunci untuk NIM pengguna
    private static final String KEY_NIM = "user_nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Memilih layout XML settings untuk tampilan ini
        setContentView(R.layout.activity_settings);

        // Memasang toolbar atas yang juga berisi tombol panah "Back"
        MaterialToolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // Minta agar tombol back ditampilkan
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Atur agar ketika back ditekan, maka perilaku persis seperti menekan tombol back device
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Mengaitkan setiap referensi lokal variabel dengan ID komponen di xml
        etName = findViewById(R.id.etName);
        etNim = findViewById(R.id.etNim);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        tvCurrentProfile = findViewById(R.id.tvCurrentProfile);

        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR SHARED PREFERENCES 🔥
        // =========================================================================
        // Penjelasan: Inisialisasi file SharedPreferences. Parameter pertama adalah nama file, 
        // parameter kedua Context.MODE_PRIVATE (hanya aplikasi ini yang bisa akses).
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // SharedPreferences prefs = getSharedPreferences("NamaFile", Context.MODE_PRIVATE);
        // =========================================================================
        // Mempersiapkan file referensi untuk SharedPreferences dalam mode privat
        // Mode Private artinya file setting ini eksklusif dan tak bisa diubah oleh aplikasi lain
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); // 🔥 INTI:

        // Memanggil fungsi loadProfile untuk menayangkan data yang telah di-save sebelumnya di layar,
        // pada saat activity pertama kali dirender.
        loadProfile();

        // Mengatur pendengar (listener) untuk tombol simpan
        btnSaveSettings.setOnClickListener(v -> saveProfile());
    }

    /**
     * Membaca string yang dimasukkan dari kolom Name dan NIM,
     * kemudian menyimpannya ke dalam file XML lokal (SharedPreferences).
     */
    private void saveProfile() {
        // Ambil masukan dan hapus spasi berlebih dengan trim()
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String nim = etNim.getText() != null ? etNim.getText().toString().trim() : "";

        // Validasi input: bila salah satu kosong, blokir proses penyimpanan
        if (name.isEmpty() || nim.isEmpty()) {
            Toast.makeText(this, "Nama dan NIM tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR SHARED PREFERENCES (SIMPAN DATA) 🔥
        // =========================================================================
        // Penjelasan: Untuk menyimpan/mengubah data, kita WAJIB memanggil .edit() dari 
        // SharedPreferences yang menghasilkan SharedPreferences.Editor.
        // Setelah itu, gunakan putString(), putInt(), putBoolean(), dll sesuai tipe data.
        // Terakhir, WAJIB memanggil .apply() (asinkron) atau .commit() (sinkron) agar tersimpan.
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // SharedPreferences.Editor editor = prefs.edit();
        // editor.putString("KEY", "VALUE");
        // editor.apply();
        // =========================================================================
        // Untuk menulis atau mengubah data, harus melalui class Editor
        SharedPreferences.Editor editor = sharedPreferences.edit(); // 🔥 INTI:
        
        // Memasukkan input Name ke kata-kunci KEY_NAME
        editor.putString(KEY_NAME, name); // 🔥 INTI:
        // Memasukkan input NIM ke kata-kunci KEY_NIM
        editor.putString(KEY_NIM, nim); // 🔥 INTI:
        
        // Simpan secara asinkron (apply), tidak memblokir laju memori jika dibandingkan dengan .commit()
        editor.apply(); // 🔥 INTI:

        // Notifikasi popup toast bahwa data tersimpan sempurna
        Toast.makeText(this, "Profil Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        
        // Menampilkan ulang data ke text view dengan info terbaru
        loadProfile();
    }

    /**
     * Mengambil dan memunculkan isi string Name dan NIM dari file di dalam memori 
     * ke Text View display (tvCurrentProfile) yang berada di layout.
     */
    private void loadProfile() {
        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR SHARED PREFERENCES (BACA DATA) 🔥
        // =========================================================================
        // Penjelasan: Untuk membaca data, cukup panggil fungsi get tipe datanya dari 
        // objek SharedPreferences (misal getString, getInt). 
        // Parameter pertama adalah KEY, parameter kedua adalah nilai DEFAULT jika data tidak ditemukan.
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // String value = prefs.getString("KEY", "DefaultValue");
        // =========================================================================
        // Mengambil data berdasarkan kata-kunci. Jika kosong(belum pernah disave), maka gunakan fallback blank string ""
        String name = sharedPreferences.getString(KEY_NAME, ""); // 🔥 INTI:
        String nim = sharedPreferences.getString(KEY_NIM, ""); // 🔥 INTI:

        // Periksa apakah Name dan NIM di memory valid tidak kosong
        if (!name.isEmpty() && !nim.isEmpty()) {
            // Tampilkan string formattannya pada TextView
            tvCurrentProfile.setText("Nama: " + name + "\nNIM: " + nim);
            // Optional: Mengisikan nilai data kembali ke form kolom TextInput (jika user mau mengedit kembali)
            etName.setText(name);
            etNim.setText(nim);
        } else {
            // Default teks kalau profil masih baru pertama dipakai / default nol data
            tvCurrentProfile.setText("Belum ada data profil.");
        }
    }
}
