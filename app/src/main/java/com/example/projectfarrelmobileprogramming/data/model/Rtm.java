package com.example.projectfarrelmobileprogramming.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Kelas Rtm (Rencana Tugas Mahasiswa) digunakan untuk menyimpan data
 * terkait tugas-tugas yang diberikan kepada mahasiswa dalam sebuah mata kuliah.
 * 
 * Implementasi Serializable memungkinkan objek Rtm menjadi bagian dari Parcel/Bundle
 * (termasuk Intent extra) agar mempermudah pengiriman data spesifik tugas
 * antara berbagai Fragment atau Activity di dalam Android.
 */
public class Rtm implements Serializable {
    
    // GSON annotation yang memberi tahu parser untuk mencocokkan data "id_rtm" dari JSON API
    // ke variabel instance ini.
    @SerializedName("id_rtm")
    private int idRtm; // Menyimpan identifier integer untuk Rencana Tugas ini

    // GSON annotation mencocokkan "judul_tugas".
    @SerializedName("judul_tugas")
    private String judulTugas; // Variabel yang berisi judul utama dari tugas mahasiswa

    // GSON annotation mencocokkan "deskripsi_tugas".
    @SerializedName("deskripsi_tugas")
    private String deskripsiTugas; // Variabel yang berisi rincian/detail yang harus dikerjakan

    // GSON annotation mencocokkan "waktu_pengerjaan".
    @SerializedName("waktu_pengerjaan")
    private String waktuPengerjaan; // Menunjukkan estimasi atau deadline kapan tugas harus diselesaikan

    // GSON annotation mencocokkan "bentuk_tugas".
    @SerializedName("bentuk_tugas")
    private String bentukTugas; // Menunjukkan bagaimana bentuk tugas (misal: makalah, presentasi, dll.)

    /**
     * Getter untuk mendapatkan ID dari rencana tugas ini.
     * @return integer ID rtm.
     */
    public int getIdRtm() {
        return idRtm; // Mengembalikan identifikasi spesifik untuk tugas ini
    }

    /**
     * Getter untuk mengambil judul tugas.
     * @return string yang merepresentasikan nama/judul tugas.
     */
    public String getJudulTugas() {
        return judulTugas; // Mengembalikan judul dari penugasan
    }

    /**
     * Getter untuk mengambil rincian instruksi tugas mahasiswa.
     * @return string deskripsi tugas.
     */
    public String getDeskripsiTugas() {
        return deskripsiTugas; // Mengembalikan deskripsi tugas secara keseluruhan
    }

    /**
     * Getter untuk mendapat informasi tenggat waktu (deadline) / batas lama pengerjaan.
     * @return string waktu pengerjaan.
     */
    public String getWaktuPengerjaan() {
        return waktuPengerjaan; // Mengembalikan info durasi atau tenggat
    }

    /**
     * Getter untuk mengetahui klasifikasi format atau bentuk penyelesaian tugas.
     * @return string bentuk tugas.
     */
    public String getBentukTugas() {
        return bentukTugas; // Mengembalikan format output tugas yang diminta dosen
    }
}
