package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Kelas BahanKajian adalah kelas model (POJO) yang berfungsi untuk merepresentasikan
 * entitas bahan kajian dari mata kuliah.
 *
 * Mengimplementasikan antarmuka Serializable agar instansiasi kelas ini bisa disisipkan
 * ke dalam Intent (misalnya sebagai ekstra data) saat berpindah Activity di Android.
 * Kelas ini sangat penting untuk menampilkan rincian materi yang diambil melalui API backend.
 */
public class BahanKajian implements Serializable {
    
    // Anotasi GSON @SerializedName memastikan kecocokan antara kunci JSON "id_bahan_kajian" 
    // dengan variabel Java 'idBahanKajian'.
    @SerializedName("id_bahan_kajian")
    private int idBahanKajian; // Variabel integer untuk menyimpan ID unik dari bahan kajian

    // Memetakan kunci "uraian_bahan_kajian" dari respons API ke variabel 'uraianBahanKajian'.
    @SerializedName("uraian_bahan_kajian")
    private String uraianBahanKajian; // Variabel string yang menyimpan teks deskripsi bahan kajian

    /**
     * Mengambil ID dari bahan kajian ini.
     * @return nilai integer berupa ID bahan kajian.
     */
    public int getIdBahanKajian() {
        return idBahanKajian; // Mengembalikan ID unik bahan kajian ke pemanggil fungsi
    }

    /**
     * Mengambil deskripsi atau uraian dari bahan kajian.
     * @return nilai String berupa uraian bahan kajian.
     */
    public String getUraianBahanKajian() {
        return uraianBahanKajian; // Mengembalikan teks penjelasan mengenai bahan kajian
    }
}
