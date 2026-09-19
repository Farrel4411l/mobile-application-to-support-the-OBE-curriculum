package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Kelas Asesmen adalah kelas model (POJO) yang merepresentasikan data asesmen mahasiswa.
 * 
 * Kelas ini mengimplementasikan Serializable agar objek Asesmen dapat dikirimkan 
 * antar komponen Android (misalnya dari Activity ke Activity lain menggunakan Intent).
 * Kelas ini digunakan untuk memetakan respons JSON dari backend API terkait fitur asesmen.
 */
public class Asesmen implements Serializable {
    
    // Memetakan field "id_hasil_asesmen" dari format JSON ke variabel idHasilAsesmen
    @SerializedName("id_hasil_asesmen")
    private int idHasilAsesmen; // Menyimpan ID unik untuk hasil asesmen

    // Memetakan field "jenis_asesmen" dari JSON ke variabel jenisAsesmen
    @SerializedName("jenis_asesmen")
    private String jenisAsesmen; // Menyimpan jenis atau tipe asesmen yang dilakukan

    // Memetakan field "nim" dari JSON ke variabel nim
    @SerializedName("nim")
    private String nim; // Menyimpan Nomor Induk Mahasiswa

    // Memetakan field "nama" dari JSON ke variabel nama
    @SerializedName("nama")
    private String nama; // Menyimpan nama mahasiswa

    // Memetakan field "bobot_cpl" dari JSON ke variabel bobotCpl
    @SerializedName("bobot_cpl")
    private int bobotCpl; // Menyimpan nilai bobot Capaian Pembelajaran Lulusan (CPL)

    /**
     * Getter untuk mendapatkan nilai ID hasil asesmen.
     * @return int idHasilAsesmen
     */
    public int getIdHasilAsesmen() {
        return idHasilAsesmen; // Mengembalikan nilai ID hasil asesmen
    }

    /**
     * Getter untuk mendapatkan string jenis asesmen.
     * @return String jenisAsesmen
     */
    public String getJenisAsesmen() {
        return jenisAsesmen; // Mengembalikan teks jenis asesmen
    }

    /**
     * Getter untuk mendapatkan NIM mahasiswa.
     * @return String nim
     */
    public String getNim() {
        return nim; // Mengembalikan nilai NIM
    }

    /**
     * Getter untuk mendapatkan nama mahasiswa.
     * @return String nama
     */
    public String getNama() {
        return nama; // Mengembalikan nama mahasiswa
    }

    /**
     * Getter untuk mendapatkan nilai bobot CPL.
     * @return int bobotCpl
     */
    public int getBobotCpl() {
        return bobotCpl; // Mengembalikan angka bobot CPL
    }
}
