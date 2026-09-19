package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Kelas Prodi (Program Studi) merupakan data model (POJO) yang berisi
 * rincian tentang suatu program studi di universitas.
 * 
 * Serializable diterapkan pada kelas ini agar instance Prodi dapat
 * ditransfer antar komponen (seperti ke DetailActivity) lewat sistem Intent di Android.
 * Kelas ini menerima data dari backend melalui mapping Gson.
 */
public class Prodi implements Serializable {
    
    // Menghubungkan variabel ini dengan properti "id_prodi" dalam struktur JSON API.
    @SerializedName("id_prodi")
    private String idProdi; // Pengenal unik program studi (ID)

    // Menghubungkan ke "nama_prodi".
    @SerializedName("nama_prodi")
    private String namaProdi; // Menyimpan nama lengkap program studi (contoh: Teknik Informatika)

    // Menghubungkan ke "jenjang".
    @SerializedName("jenjang")
    private String jenjang; // Menyimpan jenis jenjang (S1, S2, D3, dll.)

    // Menghubungkan ke "id_fakultas" yang merupakan relasi data.
    @SerializedName("id_fakultas")
    private int idFakultas; // Menyimpat ID fakultas induk dari program studi ini (dalam integer)

    /**
     * Method untuk mendapatkan ID program studi.
     * @return String id_prodi
     */
    public String getIdProdi() {
        return idProdi; // Memberikan nilai pengenal program studi
    }

    /**
     * Method untuk mendapatkan nama program studi.
     * @return String nama_prodi
     */
    public String getNamaProdi() {
        return namaProdi; // Memberikan nama program studi untuk ditampilkan di list/view
    }

    /**
     * Method untuk mendapatkan jenjang dari prodi tersebut.
     * @return String jenjang
     */
    public String getJenjang() {
        return jenjang; // Memberikan nilai jenjang (contoh "S1")
    }

    /**
     * Method untuk mendapatkan ID fakultas (Foreign Key).
     * @return Integer id_fakultas
     */
    public int getIdFakultas() {
        return idFakultas; // Mengembalikan reference ID dari fakultas prodi ini
    }
}
