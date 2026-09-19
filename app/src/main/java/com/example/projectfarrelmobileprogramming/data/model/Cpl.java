package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Kelas Cpl (Capaian Pembelajaran Lulusan) merupakan kelas model (POJO) yang
 * merepresentasikan data CPL dari program studi.
 * 
 * Kelas ini bertugas sebagai representasi objek lokal (di Android) untuk data 
 * yang dikirim oleh server backend dalam format JSON. GSON digunakan di latar belakang 
 * untuk mengonversi JSON menjadi objek Java ini secara otomatis.
 */
public class Cpl {
    
    // @SerializedName digunakan agar library GSON tahu bahwa variabel ini harus 
    // diisi dengan data dari kunci "id_cpl" pada format JSON.
    @SerializedName("id_cpl")
    private String idCpl; // Menyimpan identitas string unik untuk CPL

    // Memetakan kunci "id_prodi" dari API ke atribut idProdi dalam class ini.
    @SerializedName("id_prodi")
    private String idProdi; // Menyimpan referensi ke Program Studi yang berhubungan dengan CPL ini

    // Memetakan kunci "deskripsi_cpl" dari API ke atribut deskripsiCpl.
    @SerializedName("deskripsi_cpl")
    private String deskripsiCpl; // Menyimpan penjabaran teks mengenai detail CPL tersebut

    /**
     * Fungsi getter untuk mengambil ID CPL.
     * @return String idCpl
     */
    public String getIdCpl() {
        return idCpl; // Mengembalikan nilai ID CPL kepada pemanggil metode
    }

    /**
     * Fungsi getter untuk mengambil ID Program Studi.
     * @return String idProdi
     */
    public String getIdProdi() {
        return idProdi; // Mengembalikan ID program studi dari objek CPL ini
    }

    /**
     * Fungsi getter untuk mengambil detail/deskripsi dari CPL.
     * @return String deskripsiCpl
     */
    public String getDeskripsiCpl() {
        return deskripsiCpl; // Mengembalikan string yang berisi deskripsi CPL
    }
}
