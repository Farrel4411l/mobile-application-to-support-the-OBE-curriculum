package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Kelas MataKuliah adalah model data (POJO) yang berfungsi untuk merepresentasikan
 * entitas "Mata Kuliah" dalam aplikasi.
 *
 * Kelas ini diubah (implements) menjadi Serializable agar kita dapat melewatkan objek ini
 * sebagai parameter saat berpindah halaman (Activity) menggunakan Intent di sistem Android.
 * Data di dalam kelas ini di-populate (diisi) secara otomatis dari JSON respons backend (API).
 */
public class MataKuliah implements Serializable {
    
    // GSON annotation yang memetakan key "id_mk" dari backend ke variabel lokal idMk.
    @SerializedName("id_mk")
    private String idMk; // ID unik untuk Mata Kuliah (sebagai Primary Key)

    // Memetakan atribut "nama_mk" dari JSON ke variabel namaMk.
    @SerializedName("nama_mk")
    private String namaMk; // Menyimpan nama lengkap mata kuliah

    // Memetakan atribut "sks" (Satuan Kredit Semester) dari JSON ke variabel sks yang bertipe integer.
    @SerializedName("sks")
    private int sks; // Menyimpan jumlah SKS dari mata kuliah tersebut

    // Memetakan atribut "semester" dari JSON ke variabel semester.
    @SerializedName("semester")
    private String semester; // Menyimpan tingkat semester saat mata kuliah ini diajarkan (contoh: "1", "Ganjil")

    /**
     * Fungsi getter untuk ID Mata Kuliah.
     * @return ID Mata Kuliah dalam bentuk string.
     */
    public String getIdMk() {
        return idMk; // Mengembalikan identifier mata kuliah
    }

    /**
     * Fungsi getter untuk nama Mata Kuliah.
     * @return Nama Mata Kuliah.
     */
    public String getNamaMk() {
        return namaMk; // Mengembalikan string nama mata kuliah yang tampil di UI
    }

    /**
     * Fungsi getter untuk SKS Mata Kuliah.
     * @return Jumlah SKS berupa tipe primitif int.
     */
    public int getSks() {
        return sks; // Mengembalikan nilai SKS
    }

    /**
     * Fungsi getter untuk Semester Mata Kuliah.
     * @return Nilai/nama semester dari mata kuliah ini.
     */
    public String getSemester() {
        return semester; // Mengembalikan informasi semester
    }
}
