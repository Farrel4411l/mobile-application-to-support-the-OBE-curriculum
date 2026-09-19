package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Kelas Rps (Rencana Pembelajaran Semester) adalah model data (POJO) untuk
 * menyimpan kerangka rencana pengajaran selama satu semester dari mata kuliah.
 *
 * Implements Serializable digunakan agar objek RPS ini dapat dikemas dalam Intent 
 * saat berpindah antar Activity di sistem UI Android. Data RPS direquest melalui API
 * lalu GSON menerjemahkan JSON menjadi instansi objek kelas ini.
 */
public class Rps implements Serializable {
    
    // SerializedName yang memberitahu GSON untuk menautkan variabel ke field "id_rps" dari API.
    @SerializedName("id_rps")
    private int idRps; // Integer untuk ID unik dari RPS ini

    // SerializedName yang menghubungkan JSON "id_mk" dengan atribut ini.
    @SerializedName("id_mk")
    private String idMk; // ID mata kuliah terkait sebagai penghubung (foreign key)

    // SerializedName yang menghubungkan JSON "nama_dosen" dengan atribut ini.
    @SerializedName("nama_dosen")
    private String namaDosen; // Nama dosen pengampu untuk mata kuliah ini

    // SerializedName yang menghubungkan JSON "deskripsi_mk" dengan atribut ini.
    @SerializedName("deskripsi_mk")
    private String deskripsiMk; // Menyimpan deskripsi / tujuan umum dari mata kuliah

    // SerializedName yang menghubungkan JSON "tanggal_penyusunan" dengan atribut ini.
    @SerializedName("tanggal_penyusunan")
    private String tanggalPenyusunan; // Tanggal ketika RPS ini disusun, berformat string

    // SerializedName yang menghubungkan JSON "status" dengan atribut ini.
    @SerializedName("status")
    private String status; // Status keberlakuan RPS (misal: aktif, draf, dll)

    /**
     * Method untuk mengambil ID unik RPS.
     * @return int idRps.
     */
    public int getIdRps() {
        return idRps; // Mengembalikan identitas integer dari RPS
    }

    /**
     * Method untuk mengambil ID mata kuliah yang terkait dengan RPS ini.
     * @return String idMk.
     */
    public String getIdMk() {
        return idMk; // Mengembalikan kode / ID mata kuliah tersebut
    }

    /**
     * Method untuk memanggil nama dosen.
     * @return String namaDosen.
     */
    public String getNamaDosen() {
        return namaDosen; // Mengembalikan nama dari pengampu RPS
    }

    /**
     * Method untuk mengambil deskripsi mata kuliah pada RPS.
     * @return String deskripsiMk.
     */
    public String getDeskripsiMk() {
        return deskripsiMk; // Mengembalikan teks mengenai detail mata kuliah di RPS ini
    }

    /**
     * Method untuk mendapatkan tanggal penyusunan.
     * @return String tanggalPenyusunan.
     */
    public String getTanggalPenyusunan() {
        return tanggalPenyusunan; // Mengembalikan format tanggal string
    }

    /**
     * Method untuk mendapatkan status.
     * @return String status.
     */
    public String getStatus() {
        return status; // Mengembalikan string status RPS
    }
}
