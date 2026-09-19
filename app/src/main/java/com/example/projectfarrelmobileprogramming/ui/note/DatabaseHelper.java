package com.example.projectfarrelmobileprogramming.ui.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas DatabaseHelper merupakan turunan dari SQLiteOpenHelper yang digunakan untuk mengelola 
 * pembuatan, peningkatan (upgrade), dan interaksi dengan database SQLite lokal di aplikasi Android.
 * Dalam fitur "Note" ini, kelas ini bertindak sebagai lapisan penyimpanan data persisten lokal.
 * Kelas ini menyediakan metode untuk menambah (insert), menghapus (delete), 
 * dan mengambil semua catatan (getAllNotes).
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nama database yang akan dibuat di sistem Android
    private static final String DATABASE_NAME = "notes_db";
    // Versi database, digunakan untuk mengontrol proses upgrade jika ada perubahan skema database
    private static final int DATABASE_VERSION = 1;

    // Nama tabel yang akan digunakan untuk menyimpan catatan
    public static final String TABLE_NOTES = "notes";
    // Nama kolom untuk ID catatan (sebagai primary key)
    public static final String COLUMN_ID = "id";
    // Nama kolom untuk teks isi catatan
    public static final String COLUMN_NOTE = "note";
    // Nama kolom untuk waktu pembuatan catatan
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Kueri SQL untuk membuat tabel catatan dengan kolom id, note, dan timestamp
    // id menggunakan tipe INTEGER PRIMARY KEY AUTOINCREMENT agar unik dan bertambah otomatis.
    // timestamp menggunakan DEFAULT CURRENT_TIMESTAMP agar terisi otomatis dengan waktu saat itu.
    // =========================================================================
    // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR DATABASE (SQLite) 🔥
    // =========================================================================
    // Penjelasan: Membuat struktur tabel dasar di SQLite. Harus mendefinisikan tipe data dan primary key.
    // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
    // String query = "CREATE TABLE nama_tabel (id INTEGER PRIMARY KEY AUTOINCREMENT, kolom_lain TEXT)";
    // =========================================================================
    // 🔥 INTI:
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOTE + " TEXT, " +
            COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ");";

    /**
     * Konstruktor untuk inisialisasi DatabaseHelper.
     * @param context Konteks aplikasi dari Activity/Fragment yang memanggil kelas ini.
     */
    public DatabaseHelper(Context context) {
        // Memanggil konstruktor kelas induk dengan melemparkan context, nama database, dan versinya.
        // Factory diisi null karena kita menggunakan kursor bawaan SQLite.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metode ini dipanggil saat database pertama kali dibuat.
     * Di sinilah eksekusi pembuatan tabel dilakukan.
     * @param db Objek database SQLite
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Mengeksekusi perintah SQL (DDL) untuk membuat tabel notes di database
        db.execSQL(TABLE_CREATE);
    }

    /**
     * Metode ini dipanggil jika versi database dinaikkan (misalnya dari 1 ke 2).
     * Biasanya digunakan untuk mengubah skema tabel (drop tabel lama dan buat baru).
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel lama jika sudah ada sebelumnya
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        // Memanggil ulang onCreate untuk membuat tabel dengan struktur yang baru
        onCreate(db);
    }

    /**
     * Menyimpan catatan baru ke dalam database.
     * @param noteText Isi teks catatan dari input pengguna.
     */
    public void insertNote(String noteText) {
        // Mengambil instance database dalam mode bisa ditulis (writable)
        SQLiteDatabase db = this.getWritableDatabase();
        // ContentValues berfungsi seperti Map untuk menyimpan pasangan kolom dan nilainya
        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR DATABASE (SQLite) 🔥
        // =========================================================================
        // Penjelasan: Cara menyimpan data baru ke tabel menggunakan ContentValues sebagai kontainer map (key-value).
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // ContentValues cv = new ContentValues(); cv.put(kolom, nilai); db.insert(tabel, null, cv);
        // =========================================================================
        // 🔥 INTI:
        ContentValues values = new ContentValues();
        // Memasukkan teks catatan ke dalam objek ContentValues sesuai nama kolomnya
        values.put(COLUMN_NOTE, noteText);
        // Mengeksekusi query INSERT ke dalam tabel notes. Parameter nullHack diisi null.
        // 🔥 INTI:
        db.insert(TABLE_NOTES, null, values);
        // Menutup koneksi database untuk mencegah kebocoran memori
        db.close();
    }

    /**
     * Menghapus catatan dari database berdasarkan ID.
     * @param id ID unik dari catatan yang akan dihapus.
     */
    public void deleteNote(int id) {
        // Mengambil instance database dalam mode bisa ditulis
        SQLiteDatabase db = this.getWritableDatabase();
        // Mengeksekusi kueri DELETE pada tabel notes di mana kolom id cocok dengan ID yang diberikan
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        // Menutup koneksi database
        db.close();
    }

    /**
     * Mengambil seluruh data catatan dari database untuk ditampilkan di RecyclerView.
     * @return List objek Note yang berisi semua catatan.
     */
    public List<Note> getAllNotes() {
        // Inisialisasi daftar kosong untuk menampung hasil pengambilan data
        List<Note> notes = new ArrayList<>();
        // Mengambil instance database dalam mode hanya baca (readable) karena hanya melakukan SELECT
        SQLiteDatabase db = this.getReadableDatabase();
        // Mengeksekusi query SELECT semua data dari tabel notes, dan diurutkan secara menurun (DESC) berdasarkan timestamp 
        // agar catatan terbaru muncul paling atas. Kursor menyimpan hasil kueri ini.
        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR DATABASE (SQLite) 🔥
        // =========================================================================
        // Penjelasan: Mengambil data menggunakan perintah SQL (SELECT) dan menampungnya dalam Cursor untuk diiterasi.
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // Cursor cursor = db.rawQuery("SELECT * FROM tabel", null);
        // =========================================================================
        // 🔥 INTI:
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_TIMESTAMP + " DESC", null);
        
        // Memeriksa apakah kursor memiliki data (bisa dipindah ke baris pertama)
        if (cursor.moveToFirst()) {
            // Looping melalui setiap baris data di kursor
            do {
                // Membuat objek Note baru dan mengisi atributnya dari data kolom kursor
                Note note = new Note(
                        // Mengambil nilai integer dari kolom ID
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        // Mengambil nilai string dari kolom catatan (note)
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE)),
                        // Mengambil nilai string dari kolom waktu (timestamp)
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                );
                // Menambahkan objek Note yang sudah dibuat ke dalam daftar (List)
                notes.add(note);
            // Melanjutkan loop hingga kursor mencapai akhir data
            } while (cursor.moveToNext());
        }
        // Menutup kursor setelah selesai digunakan untuk membebaskan sumber daya
        cursor.close();
        // Menutup koneksi database
        db.close();
        // Mengembalikan daftar catatan ke pemanggil metode (misalnya NoteActivity)
        return notes;
    }
}
