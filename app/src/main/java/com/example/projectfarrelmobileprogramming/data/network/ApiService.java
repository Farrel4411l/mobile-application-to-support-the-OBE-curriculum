package com.example.projectfarrelmobileprogramming.data.network;

import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.Prodi;
import com.example.projectfarrelmobileprogramming.data.model.Cpl;
import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface ApiService digunakan untuk mendefinisikan endpoint-endpoint API (rute)
 * yang akan dipanggil oleh Retrofit. Setiap metode di dalam interface ini mewakili
 * satu permintaan HTTP (seperti GET, POST, dll).
 * Anotasi Retrofit seperti @GET dan @Query digunakan untuk mengkonfigurasi parameter
 * permintaan jaringan.
 */
public interface ApiService {

    // =========================================================================
    // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR API (Retrofit Service) 🔥
    // =========================================================================
    // Penjelasan: Di dalam interface ini, kita mendefinisikan rute (endpoint) ke server.
    // Anotasi @GET menandakan HTTP Method GET. Parameter seperti @Query disisipkan di fungsinya.
    // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
    // 1. `@GET("endpoint")` untuk menentukan rute relatif ke server.
    // 2. Tipe kembalian fungsi harus `Call<TipeModelRespon>`, yang artinya tugas ini akan dieksekusi oleh Retrofit.
    // =========================================================================
    
    // Mendefinisikan permintaan HTTP GET ke endpoint "prodi"
    // @Query("per_page") digunakan untuk menambahkan parameter query ke URL,
    // misalnya: /api/prodi?per_page=100
    @GET("prodi") // 🔥 INTI: Mendefinisikan HTTP Method dan rute path (endpoint)
    Call<ApiResponse<Prodi>> getProdi(@Query("per_page") int perPage); // 🔥 INTI: Call<> digunakan untuk menampung respon JSON menjadi Object Java

    // Mendefinisikan permintaan HTTP GET ke endpoint "profil-lulusan"
    // Endpoint ini membutuhkan dua parameter query: id_prodi dan per_page
    @GET("profil-lulusan")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.ProfilLulusan>> getProfilLulusan(@Query("id_prodi") String idProdi, @Query("per_page") int perPage);

    // Endpoint untuk mendapatkan data Capaian Pembelajaran Lulusan (CPL) berdasarkan ID Program Studi
    @GET("cpl")
    Call<ApiResponse<Cpl>> getCpl(@Query("id_prodi") String idProdi, @Query("per_page") int perPage);

    // Endpoint untuk mendapatkan data Capaian Pembelajaran Mata Kuliah (CPMK)
    @GET("cpmk")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Cpmk>> getCpmk(@Query("id_prodi") String idProdi, @Query("per_page") int perPage);

    // Endpoint untuk mendapatkan data Mata Kuliah secara umum
    @GET("mata-kuliah")
    Call<ApiResponse<MataKuliah>> getMataKuliah(@Query("per_page") int perPage);

    // Endpoint untuk mengambil data Bahan Kajian
    @GET("bahan-kajian")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.BahanKajian>> getBahanKajian(@Query("per_page") int perPage);

    // Endpoint untuk mengambil Rencana Tugas Mahasiswa (RTM)
    @GET("rtm")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rtm>> getRtm(@Query("per_page") int perPage);

    // Endpoint untuk mengambil Hasil Asesmen dari RPS
    @GET("rps-hasil-asesmen")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Asesmen>> getHasilAsesmen(@Query("per_page") int perPage);

    // Endpoint untuk pemetaan Bahan Kajian dengan Mata Kuliah. Menggunakan Object sebagai generic
    // karena mungkin strukturnya dinamis atau belum dibuatkan model secara spesifik.
    @GET("pemetaan-bk-mk")
    Call<ApiResponse<Object>> getPemetaanBkMk(@Query("per_page") int perPage);
    
    // Endpoint untuk pemetaan Bahan Kajian dengan CPL
    @GET("pemetaan-bk-cpl")
    Call<ApiResponse<Object>> getPemetaanBkCpl(@Query("per_page") int perPage);
    
    // Endpoint untuk pemetaan CPMK dengan Mata Kuliah
    @GET("pemetaan-cpmk-mk")
    Call<ApiResponse<Object>> getPemetaanCpmkMk(@Query("per_page") int perPage);
    
    // Endpoint untuk pemetaan CPL dengan Sub-CPMK dari RPS
    @GET("rps-pemetaan-cpl-subcpmk")
    Call<ApiResponse<Object>> getPemetaanCplSubCpmk(@Query("per_page") int perPage);

    // Endpoint untuk mengambil data keseluruhan RPS (Rencana Pembelajaran Semester)
    @GET("rps")
    Call<ApiResponse<com.example.projectfarrelmobileprogramming.data.model.Rps>> getRps(@Query("per_page") int perPage);

    // Endpoint untuk mengambil Sub-CPMK berdasarkan ID RPS tertentu
    @GET("rps-subcpmk")
    Call<ApiResponse<Object>> getSubCpmk(@Query("id_rps") int idRps, @Query("per_page") int perPage);

    // Endpoint untuk mengambil Rencana Asesmen berdasarkan ID Sub-CPMK tertentu
    @GET("rps-rencana-asesmen")
    Call<ApiResponse<Object>> getRencanaAsesmen(@Query("id_subcpmk") int idSubCpmk, @Query("per_page") int perPage);

}
