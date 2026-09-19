package com.example.projectfarrelmobileprogramming.ui.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projectfarrelmobileprogramming.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

/**
 * LocationActivity adalah kelas aktivitas yang bertugas untuk mendapatkan posisi 
 * geografis pengguna (lokasi/koordinat) menggunakan layanan lokasi Fused Location API dari Google.
 * Fitur ini mencakup penanganan perizinan dan eksekusi pengambilan lokasi pengguna (latitude & longitude).
 */
public class LocationActivity extends AppCompatActivity {

    // Konstanta ini digunakan sebagai kode permintaan khusus saat kita meminta izin sistem (Location Permission)
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    // Mendeklarasikan instance FusedLocationProviderClient sebagai objek utama untuk mengakses lokasi API Google
    private FusedLocationProviderClient fusedLocationClient;
    // Mendeklarasikan TextView untuk menampilkan hasil latitude dan longitude yang didapat kepada pengguna
    private TextView tvCoordinate;
    // Mendeklarasikan MaterialButton sebagai tombol interaktif untuk memulai pencarian lokasi
    private MaterialButton btnGetLocation;

    /**
     * Siklus hidup onCrate dipanggil saat aktivitas diinisialisasi pertama kali
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Memanggil fungsi dari kelas induk
        super.onCreate(savedInstanceState);
        // Mengatur layout activity_location sebagai UI untuk aktivitas ini
        setContentView(R.layout.activity_location);

        // Mengambil referensi dari toolbar dari dalam file XML layout
        MaterialToolbar toolbar = findViewById(R.id.toolbarLocation);
        // Mengatur toolbar kustom menjadi action bar aplikasi
        setSupportActionBar(toolbar);
        // Memeriksa jika Action bar berhasil dibuat
        if (getSupportActionBar() != null) {
            // Menampilkan tombol panah kembali (up button)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Menambahkan interaksi saat panah kembali ditekan, memicu simulasi menekan tombol kembali di perangkat
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Mengambil TextView koordinat berdasarkan id pada UI
        tvCoordinate = findViewById(R.id.tvCoordinate);
        // Mengambil Button berdasarkan id pada UI
        btnGetLocation = findViewById(R.id.btnGetLocation);

        // Menginisialisasi komponen pencari lokasi (Fused Location Provider) dari Google Play Services
        // 🔥 INTI: Inisialisasi FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Mengatur agar ketika tombol ditekan, fungsi checkPermissionAndGetLocation akan berjalan 
        btnGetLocation.setOnClickListener(v -> checkPermissionAndGetLocation());
    }

    /**
     * Memeriksa apakah pengguna telah mengizinkan aplikasi ini untuk mengakses lokasi.
     * Bila tidak, meminta sistem menampilkan dialog (prompt) perizinan (permission dialog).
     */
    private void checkPermissionAndGetLocation() {
        // Menggunakan ContextCompat untuk mengecek apakah izin (ACCESS_FINE_LOCATION) sebelumnya sudah pernah disetujui (GRANTED)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Jika izin belum diberikan atau ditolak sebelumnya, aplikasi akan meminta secara eksplisit pada layar
            // Variabel LOCATION_PERMISSION_REQUEST_CODE disertakan untuk mengenali hasil request nanti
            // 🔥 INTI: Meminta izin lokasi secara runtime
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Jika izin lokasi telah ada / sudah disetujui, panggil metode langsung untuk mulai melacak (get location)
            getLocation();
        }
    }

    /**
     * Melakukan eksekusi pengambilan koordinat terakhir yang diketahui (last known location) perangkat.
     */
    private void getLocation() {
        // Mengecek ulang apakah izin tetap tersedia sebelum benar-benar mengambil data (aturan dari compiler/sistem keamanan)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Jika ternyata izin hilang (misal ditolak manual), keluar dari fungsi tanpa berbuat apa-apa
            return;
        }
        
        // Mematikan fungsionalitas klik tombol untuk mencegah spam klik dari user selagi lokasi sedang dihitung/diambil
        btnGetLocation.setEnabled(false);
        // Mengubah tulisan pada tombol agar user tahu aplikasi sedang memproses lokasi (UX improvement)
        btnGetLocation.setText("Mencari Lokasi...");

        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR LBS (LOCATION BASED SERVICES) 🔥
        // =========================================================================
        // Penjelasan: FusedLocationProviderClient adalah API modern dan paling akurat dari Google untuk mengambil lokasi.
        // Fitur ini tidak akan bekerja tanpa permission di Manifest dan runtime request (requestPermissions).
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // 1. Inisialisasi: fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // 2. Cek/Request Izin: ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        // 3. Ambil Lokasi: fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() { ... });
        // =========================================================================
        
        // Memanggil provider lokasi untuk meminta letak geografis terbaru yang tercatat
        // 🔥 INTI: Mengambil last known location dan memberikan callback onSuccess
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            // Callback apabila permintaan lokasi ke sistem berhasil dilakukan tanpa error
            @Override
            public void onSuccess(Location location) {
                // Menyalakan kembali fungsionalitas tombol sehingga bisa diklik lagi nanti
                btnGetLocation.setEnabled(true);
                // Mengembalikan teks tombol ke tulisan semula
                btnGetLocation.setText("Dapatkan Lokasi Terkini");

                // Memeriksa jika objek lokasi tidak null (artinya GPS aktif dan ada informasi lokasi terbaru yang terekam)
                if (location != null) {
                    // Mengambil nilai koordinat garis lintang (latitude)
                    double latitude = location.getLatitude();
                    // Mengambil nilai koordinat garis bujur (longitude)
                    double longitude = location.getLongitude();
                    // Menggabungkan nilai tersebut dan memperbarui teks komponen TextView dengan informasi lokasi terbaru
                    tvCoordinate.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                    // Menampilkan pesan pop-up singkat (Toast) yang memberi tahu pengguna bahwa lokasi ditemukan
                    Toast.makeText(LocationActivity.this, "Lokasi berhasil didapatkan!", Toast.LENGTH_SHORT).show();
                } else {
                    // Jika lokasi didapat namun nilainya null, ini biasanya berarti GPS belum pernah melacak (seperti saat fitur lokasi HP mati)
                    // Maka kita tampilkan pemberitahuan instruksi bagi pengguna
                    tvCoordinate.setText("Lokasi tidak ditemukan. Pastikan GPS HP menyala.");
                }
            }
        });
    }

    /**
     * Merupakan callback (fungsi pemanggil bawaan) yang merespons setelah pengguna
     * memilih pilihan izinkan atau tolak pada dialog perizinan (permission prompt) di sistem Android.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Memanggil metode super untuk kelengkapan standar lifecycle Android
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Mengecek apakah respons ini spesifik untuk request kode dari izin lokasi yang kita definisikan (100)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Memeriksa apakah ada hasil yang didapatkan dan index hasil pertama menandakan bahwa akses 'diizinkan'
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Jika izin diberikan oleh pengguna (Allow/Izinkan), munculkan Toast peringatan sukses
                Toast.makeText(this, "Izin Lokasi Diberikan!", Toast.LENGTH_SHORT).show();
                // Secara otomatis langsung panggil pencarian lokasi agar user tidak usah mengklik ulang tombol
                getLocation();
            } else {
                // Jika pengguna menekan "Tolak" (Deny), beritahukan bahwa aksi pengambilan lokasi tidak bisa dilanjutkan
                Toast.makeText(this, "Izin Lokasi Ditolak. Tidak dapat mengambil lokasi.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
