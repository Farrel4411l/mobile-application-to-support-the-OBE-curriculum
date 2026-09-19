package com.example.projectfarrelmobileprogramming.ui.note;

/**
 * Kelas Note adalah sebuah Model atau Plain Old Java Object (POJO) yang digunakan untuk merepresentasikan
 * satu entitas data Catatan. Kelas ini memegang struktur data sederhana dan berfungsi sebagai 
 * wadah untuk membawa data antara lapisan Database SQLite dan lapisan Antarmuka Pengguna (UI) seperti RecyclerView.
 */
public class Note {
    // Properti id menyimpan nilai ID unik (Primary Key) dari database untuk setiap catatan
    private int id;
    // Properti text menyimpan isi pesan atau teks dari catatan
    private String text;
    // Properti timestamp menyimpan waktu pembuatan catatan sebagai string
    private String timestamp;

    /**
     * Konstruktor untuk membuat instansiasi objek Note dengan data lengkap.
     * Biasanya dipanggil saat membaca data dari database Cursor.
     * 
     * @param id        ID dari catatan di dalam database
     * @param text      Isi teks dari catatan
     * @param timestamp Waktu catatan tersebut dibuat
     */
    public Note(int id, String text, String timestamp) {
        // Menugaskan nilai parameter id ke variabel instance (properti kelas) this.id
        this.id = id;
        // Menugaskan nilai parameter text ke variabel instance this.text
        this.text = text;
        // Menugaskan nilai parameter timestamp ke variabel instance this.timestamp
        this.timestamp = timestamp;
    }

    /**
     * Getter untuk mendapatkan nilai ID catatan.
     * @return Nilai integer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter untuk mendapatkan isi teks catatan.
     * @return String berupa teks catatan.
     */
    public String getText() {
        return text;
    }

    /**
     * Getter untuk mendapatkan waktu pembuatan catatan.
     * @return String waktu (timestamp).
     */
    public String getTimestamp() {
        return timestamp;
    }
}
