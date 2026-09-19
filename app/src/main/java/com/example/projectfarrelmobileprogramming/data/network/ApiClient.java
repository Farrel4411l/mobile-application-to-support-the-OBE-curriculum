package com.example.projectfarrelmobileprogramming.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Kelas ApiClient bertanggung jawab untuk mengatur dan menyediakan instance Retrofit.
 * Retrofit digunakan sebagai HTTP client di Android untuk melakukan komunikasi dengan REST API.
 * Kelas ini mengimplementasikan pola desain Singleton, memastikan hanya ada satu instance
 * Retrofit yang dibuat selama siklus hidup aplikasi.
 */
public class ApiClient {
    // URL dasar dari REST API yang akan diakses oleh aplikasi.
    // Base URL harus selalu diakhiri dengan garis miring (/).
    private static final String BASE_URL = "https://backend-obe-api.vercel.app/api/";
    
    // Variabel statis untuk menyimpan instance Retrofit agar dapat digunakan ulang.
    private static Retrofit retrofit = null;

    /**
     * Metode statis untuk mendapatkan instance Retrofit.
     * Jika instance belum ada, maka akan dibuatkan yang baru.
     *
     * @return Objek Retrofit yang sudah dikonfigurasi.
     */
    // =========================================================================
    // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR API (Retrofit) 🔥
    // =========================================================================
    // Penjelasan: ApiClient bertugas membuat SATU instance Retrofit (Singleton) yang siap
    // menghubungkan aplikasi dengan server.
    // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
    // 1. `new Retrofit.Builder()` untuk mulai membuat object Retrofit
    // 2. `.baseUrl(...)` menentukan alamat utama server
    // 3. `.addConverterFactory(...)` agar JSON otomatis diubah jadi Object Java
    // 4. `.build()` untuk mengakhiri dan menghasilkan instance Retrofit.
    // =========================================================================
    public static Retrofit getClient() {
        // Pengecekan apakah instance retrofit belum pernah dibuat
        if (retrofit == null) {
            // Membangun instance Retrofit menggunakan pola Builder
            retrofit = new Retrofit.Builder() // 🔥 INTI: Inisialisasi Builder Retrofit
                    // Mengatur URL dasar untuk semua permintaan HTTP
                    .baseUrl(BASE_URL) // 🔥 INTI: Wajib ada URL utama API
                    // Menambahkan konverter Gson untuk memetakan respons JSON secara otomatis
                    // menjadi objek Java (model) yang telah didefinisikan.
                    .addConverterFactory(GsonConverterFactory.create()) // 🔥 INTI: Wajib ditambahkan agar respons JSON dari server bisa dibaca
                    // Membangun dan mengembalikan instance Retrofit
                    .build(); // 🔥 INTI: Build instance Retrofit
        }
        // Mengembalikan instance Retrofit yang sudah ada
        return retrofit;
    }
}
