package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Kelas ApiResponse adalah kelas model (POJO) generik yang digunakan untuk memetakan
 * struktur respons dari API (backend).
 * 
 * Kelas ini biasanya membungkus data utama yang dikembalikan oleh API dalam bentuk list,
 * sehingga memudahkan proses parsing menggunakan library Retrofit dan GSON.
 *
 * @param <T> Tipe data objek yang ada di dalam list (misalnya Asesmen, MataKuliah, dll.)
 */
public class ApiResponse<T> {
    
    // Anotasi @SerializedName digunakan oleh GSON untuk memetakan kunci "data" dari JSON 
    // respons API langsung ke variabel 'data' dalam kelas ini.
    @SerializedName("data")
    private List<T> data; // List yang menampung sekumpulan objek dengan tipe T

    /**
     * Metode getter untuk mengambil list data yang telah diparsing dari JSON.
     * 
     * @return List yang berisi objek dengan tipe T.
     */
    public List<T> getData() {
        return data; // Mengembalikan data untuk digunakan di bagian lain dari aplikasi, seperti RecyclerView
    }
}
