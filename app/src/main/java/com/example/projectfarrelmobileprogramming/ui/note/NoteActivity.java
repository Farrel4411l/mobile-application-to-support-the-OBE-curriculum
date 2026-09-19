package com.example.projectfarrelmobileprogramming.ui.note;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * NoteActivity adalah antarmuka utama untuk fitur Catatan pada aplikasi ini.
 * Activity ini bertugas untuk menangani input teks catatan dari pengguna, menyimpannya ke database lokal 
 * menggunakan DatabaseHelper, serta menampilkan daftar catatan melalui RecyclerView dengan bantuan NoteAdapter.
 * Ini menghubungkan tampilan (UI) dengan logika data lokal (SQLite).
 */
public class NoteActivity extends AppCompatActivity {

    // Deklarasi variabel helper database untuk interaksi dengan SQLite
    private DatabaseHelper databaseHelper;
    // Deklarasi adapter yang akan menjembatani data List<Note> ke komponen RecyclerView
    private NoteAdapter noteAdapter;
    // Deklarasi komponen UI RecyclerView untuk menampilkan daftar catatan dalam bentuk list
    private RecyclerView rvNotes;
    // Deklarasi komponen input teks (edit text) untuk mengetik catatan baru
    private TextInputEditText etNote;
    // Deklarasi tombol untuk menyimpan catatan ke database
    private MaterialButton btnSaveNote;

    /**
     * Metode onCreate dipanggil saat aktivitas ini pertama kali dibuat oleh sistem Android.
     * Semua inisialisasi awal UI dan objek dilakukan di sini.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menghubungkan layout XML 'activity_note' ke activity ini
        setContentView(R.layout.activity_note);

        // Menghubungkan komponen MaterialToolbar dari XML berdasarkan ID
        MaterialToolbar toolbar = findViewById(R.id.toolbarNote);
        // Mengatur toolbar kustom ini sebagai ActionBar utama untuk activity
        setSupportActionBar(toolbar);
        // Memastikan Action Bar memiliki tombol 'Kembali' (Up Button) di sebelah kiri
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Memberikan aksi saat tombol 'Kembali' di toolbar ditekan (akan menutup activity ini dan kembali)
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Mengaitkan variabel UI ke elemen-elemen tampilan (View) berdasarkan ID masing-masing dari XML
        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR UI DASAR (EditText, Button) 🔥
        // =========================================================================
        // Penjelasan: Mengikat variabel Java dengan elemen di layout XML menggunakan findViewById.
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // EditText et = findViewById(R.id.id_edit_text); Button btn = findViewById(R.id.id_button);
        // =========================================================================
        // 🔥 INTI:
        etNote = findViewById(R.id.etNote);
        // 🔥 INTI:
        btnSaveNote = findViewById(R.id.btnSaveNote);
        rvNotes = findViewById(R.id.rvNotes);

        // Menginisialisasi kelas DatabaseHelper dengan memberikan 'Context' activity saat ini
        databaseHelper = new DatabaseHelper(this);
        
        // Mengatur LayoutManager pada RecyclerView sebagai LinearLayoutManager agar daftar ditampilkan vertikal
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        
        // Menginisialisasi adapter untuk RecyclerView. Pada saat awal, list data diisi 'null' atau kosong.
        // Kita juga mendefinisikan callback (listener) menggunakan lambda ketika ikon hapus pada catatan ditekan.
        noteAdapter = new NoteAdapter(null, note -> {
            // Menghapus data catatan dari SQLite melalui databaseHelper berdasarkan ID-nya
            databaseHelper.deleteNote(note.getId());
            // Menampilkan notifikasi Toast (pesan mengambang singkat) bahwa catatan berhasil dihapus
            Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show();
            // Memuat ulang daftar catatan setelah ada penghapusan agar UI (RecyclerView) sinkron
            loadNotes();
        });
        // Memasang adapter ke komponen RecyclerView sehingga ia siap menampilkan daftar
        rvNotes.setAdapter(noteAdapter);

        // Memberikan pendengar klik (click listener) pada tombol simpan
        // Saat diklik, metode saveNote() akan dipanggil.
        // =========================================================================
        // 🔥 JAWABAN DOSEN KILLER: INTI DARI FITUR UI DASAR (Interaksi Button) 🔥
        // =========================================================================
        // Penjelasan: Menambahkan event listener agar aplikasi bereaksi saat tombol diklik.
        // Jika diminta coding ulang, inilah sintaks wajib yang TIDAK BOLEH LUPA:
        // button.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { ... } });
        // =========================================================================
        // 🔥 INTI:
        btnSaveNote.setOnClickListener(v -> saveNote());
        
        // Memanggil fungsi untuk mengambil data catatan dari database dan menampilkannya di awal (saat Activity dibuka)
        loadNotes();
    }

    /**
     * Metode untuk menyimpan catatan yang diketik pengguna ke dalam database SQLite.
     */
    private void saveNote() {
        // Mengambil teks dari TextInputEditText, mengonversinya ke String, dan menghapus spasi awal/akhir (trim). 
        // Jika input null, gunakan string kosong.
        String text = etNote.getText() != null ? etNote.getText().toString().trim() : "";
        
        // Validasi input: memastikan bahwa teks catatan tidak kosong sebelum disimpan.
        if (text.isEmpty()) {
            // Jika kosong, tampilkan peringatan Toast dan hentikan eksekusi metode dengan perintah 'return'
            Toast.makeText(this, "Catatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Memanggil metode insertNote pada databaseHelper untuk memasukkan teks ke SQLite
        databaseHelper.insertNote(text);
        // Mengosongkan kolom input teks (EditText) setelah catatan berhasil disimpan
        etNote.setText(""); 
        // Menampilkan pesan sukses menggunakan Toast
        Toast.makeText(this, "Catatan disimpan!", Toast.LENGTH_SHORT).show();
        // Memuat ulang daftar catatan agar RecyclerView terbarui (memunculkan catatan yang baru saja ditambah)
        loadNotes();
    }

    /**
     * Metode untuk memuat/mengambil semua catatan dari SQLite dan memperbaruinya ke Adapter (UI).
     */
    private void loadNotes() {
        // Menggunakan helper untuk mendapatkan kumpulan seluruh entitas Note dalam bentuk List
        List<Note> notes = databaseHelper.getAllNotes();
        // Memberikan List catatan yang baru didapat dari database ke adapter untuk diperbarui di tampilan RecyclerView
        noteAdapter.setNotes(notes);
    }
}
