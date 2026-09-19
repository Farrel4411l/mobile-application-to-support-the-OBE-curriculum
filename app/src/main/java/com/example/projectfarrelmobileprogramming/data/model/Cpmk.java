package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Kelas Cpmk (Capaian Pembelajaran Mata Kuliah) adalah kelas model (POJO) untuk
 * mempresentasikan data capaian spesifik per mata kuliah.
 *
 * Kelas ini bertindak sebagai penerima (container) untuk data JSON dari backend (REST API),
 * sehingga data yang diterima bisa diolah dan ditampilkan di sisi frontend Android.
 */
public class Cpmk {
    
    // Anotasi GSON untuk memetakan key "id_cpmk" dari JSON API menjadi variabel idCpmk.
    @SerializedName("id_cpmk")
    private String idCpmk; // Identifikasi unik (Primary/Foreign Key) untuk entitas CPMK

    // Memetakan key "kode_cpmk" ke variabel kodeCpmk.
    @SerializedName("kode_cpmk")
    private String kodeCpmk; // Menyimpan kode spesifik untuk sebuah CPMK (contoh: CPMK-1)

    // Memetakan key "deskripsi_cpmk" ke variabel deskripsiCpmk.
    @SerializedName("deskripsi_cpmk")
    private String deskripsiCpmk; // Menyimpan penjelasan rinci tentang CPMK

    // Memetakan key "id_cpl" ke variabel idCpl.
    @SerializedName("id_cpl")
    private String idCpl; // Merupakan Foreign Key yang menunjuk ke CPL yang menaungi CPMK ini

    // Memetakan key "id_mk" ke variabel idMk.
    @SerializedName("id_mk")
    private String idMk; // Foreign Key yang menghubungkan CPMK dengan suatu Mata Kuliah tertentu

    /**
     * Mengambil ID unik CPMK.
     * @return String yang berisi ID CPMK
     */
    public String getIdCpmk() {
        return idCpmk; // Mengembalikan nilai dari variabel idCpmk
    }

    /**
     * Mengambil kode dari CPMK.
     * @return String berupa kode CPMK
     */
    public String getKodeCpmk() {
        return kodeCpmk; // Mengembalikan string kode unik CPMK
    }

    /**
     * Mengambil teks deskripsi CPMK.
     * @return String yang menjelaskan CPMK
     */
    public String getDeskripsiCpmk() {
        return deskripsiCpmk; // Mengembalikan uraian panjang mengenai CPMK terkait
    }

    /**
     * Mengambil ID CPL (Capaian Pembelajaran Lulusan) yang terkait.
     * @return String berisi ID CPL
     */
    public String getIdCpl() {
        return idCpl; // Mengembalikan nilai ID relasi CPL
    }

    /**
     * Mengambil ID Mata Kuliah yang terkait dengan CPMK.
     * @return String berisi ID Mata Kuliah
     */
    public String getIdMk() {
        return idMk; // Mengembalikan nilai ID relasi mata kuliah
    }
}
