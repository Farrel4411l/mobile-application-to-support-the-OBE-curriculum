package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Kelas ProfilLulusan adalah Plain Old Java Object (POJO) yang berfungsi untuk 
 * menangkap data profil dari lulusan sebuah program studi.
 * 
 * Melalui koneksi HTTP yang dibuat dengan Retrofit, Gson secara otomatis 
 * mengonversi response JSON server menjadi objek ProfilLulusan ini, memfasilitasi 
 * akses tipe data yang aman (type-safe) dalam aplikasi Android.
 */
public class ProfilLulusan {
    
    // Anotasi SerializedName untuk mapping otomatis dengan key "id_pl" di JSON.
    @SerializedName("id_pl")
    private int idPl; // Menyimpan integer ID untuk Profil Lulusan

    // Mapping otomatis ke key "id_prodi".
    @SerializedName("id_prodi")
    private String idProdi; // Menyimpan reference (ID) Program Studi yang berhubungan dengan profil ini

    // Mapping otomatis ke key "deskripsi_pl".
    @SerializedName("deskripsi_pl")
    private String deskripsiPl; // Teks yang mendeskripsikan secara lengkap mengenai kompetensi atau profil lulusan

    /**
     * Method getter untuk ID Profil Lulusan.
     * @return int nilai ID.
     */
    public int getIdPl() {
        return idPl; // Mengembalikan identifikasi spesifik untuk entitas profil lulusan
    }

    /**
     * Method getter untuk mendapatkan ID Prodi.
     * @return String ID Prodi.
     */
    public String getIdProdi() {
        return idProdi; // Mengembalikan ID program studi untuk keperluan filtering atau relasi
    }

    /**
     * Method getter untuk memanggil deskripsi dari profil.
     * @return String penjelasan profil.
     */
    public String getDeskripsiPl() {
        return deskripsiPl; // Mengembalikan paragraf atau string dari deskripsi lulusan
    }
}
