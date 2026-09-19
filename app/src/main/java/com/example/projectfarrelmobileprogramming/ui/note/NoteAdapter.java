package com.example.projectfarrelmobileprogramming.ui.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;

import java.util.List;

/**
 * NoteAdapter adalah kelas yang berfungsi sebagai Adapter untuk RecyclerView pada tampilan catatan.
 * Kelas ini bertanggung jawab untuk mengubah data model (List<Note>) menjadi tampilan visual (View) untuk 
 * setiap baris catatan. Menggunakan pola ViewHolder untuk mengoptimalkan performa (mencegah pemanggilan 
 * findViewById secara berlebihan saat melakukan scrolling).
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    // Menyimpan daftar catatan (data) yang akan ditampilkan
    private List<Note> notes;
    // Menyimpan referensi interface (listener) untuk menangani kejadian saat tombol "hapus" ditekan
    private OnNoteDeleteListener deleteListener;

    /**
     * Interface untuk Callback/Listener yang akan diimplementasikan oleh pemanggil (misal: NoteActivity).
     * Hal ini bertujuan untuk memisahkan logika (penghapusan database) dari kelas Adapter.
     */
    public interface OnNoteDeleteListener {
        // Metode ini akan dipicu (di-trigger) dengan parameter Note yang diklik hapus
        void onDeleteClick(Note note);
    }

    /**
     * Konstruktor Adapter.
     * @param notes          Kumpulan data catatan awal (bisa null/kosong).
     * @param deleteListener Listener yang merespon klik tombol hapus.
     */
    public NoteAdapter(List<Note> notes, OnNoteDeleteListener deleteListener) {
        // Mengisi properti list catatan dari argumen konstruktor
        this.notes = notes;
        // Mengisi properti pendengar kejadian (listener) dari argumen konstruktor
        this.deleteListener = deleteListener;
    }

    /**
     * Metode untuk memperbarui dataset (daftar catatan) di adapter secara langsung 
     * dari luar adapter (misalnya dari activity setelah memuat ulang dari database).
     * @param notes Data list terbaru.
     */
    public void setNotes(List<Note> notes) {
        // Mengubah referensi list data lama ke data list yang baru
        this.notes = notes;
        // Memberitahu RecyclerView bahwa seluruh data telah berubah sehingga perlu dirender ulang (refresh UI)
        notifyDataSetChanged();
    }

    /**
     * Dipanggil saat RecyclerView membutuhkan sebuah ViewHolder baru untuk merepresentasikan baris item.
     * RecyclerView tidak akan memanggil ini untuk setiap item, melainkan cukup untuk membuat View sesuai ukuran layar.
     */
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout XML (item_note) menjadi sebuah objek View untuk satu baris catatan.
        // attachToRoot diset 'false' karena View ini akan di-attach (ditempel) secara otomatis oleh RecyclerView nanti.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        // Mengembalikan instance ViewHolder yang sudah membungkus layout View ini.
        return new NoteViewHolder(view);
    }

    /**
     * Dipanggil oleh RecyclerView untuk menampilkan data pada posisi tertentu di list.
     * Proses binding (pengaitan) antara data Note ke UI komponen di dalam ViewHolder terjadi di sini.
     */
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Mengambil objek Note dari list sesuai dengan posisinya.
        Note note = notes.get(position);
        // Menempatkan isi teks dari Note ke TextView untuk teks catatan.
        holder.tvNoteText.setText(note.getText());
        // Menempatkan string waktu (timestamp) ke TextView untuk waktu.
        holder.tvNoteTimestamp.setText(note.getTimestamp());
        
        // Memberikan listener aksi 'klik' pada tombol hapus (ImageButton)
        holder.btnDeleteNote.setOnClickListener(v -> {
            // Memastikan listener (Callback) sudah diatur (tidak null)
            if (deleteListener != null) {
                // Memanggil metode dari interface untuk diteruskan kembali ke Activity beserta objek Note yang dihapus
                deleteListener.onDeleteClick(note);
            }
        });
    }

    /**
     * Memberitahu RecyclerView berapa total jumlah item yang ada di dalam list data.
     */
    @Override
    public int getItemCount() {
        // Mengembalikan ukuran list. Jika list null, maka kembalikan 0 untuk menghindari NullPointerException.
        return notes != null ? notes.size() : 0;
    }

    /**
     * Kelas ViewHolder statis digunakan sebagai kontainer untuk elemen-elemen UI setiap baris catatan.
     * Ini mempertahankan referensi (cache) ke komponen view agar tidak dipanggil berkali-kali dengan findViewById.
     */
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        // Komponen teks untuk isi catatan dan untuk menampilkan waktu
        TextView tvNoteText, tvNoteTimestamp;
        // Komponen tombol (gambar) untuk menghapus catatan
        ImageButton btnDeleteNote;

        /**
         * Konstruktor dari ViewHolder. Menerima objek 'itemView' yaitu root View dari baris tunggal (item_note.xml).
         */
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Mencari dan menghubungkan komponen dari XML menggunakan referensi itemView berdasarkan ID-nya
            tvNoteText = itemView.findViewById(R.id.tvNoteText);
            tvNoteTimestamp = itemView.findViewById(R.id.tvNoteTimestamp);
            btnDeleteNote = itemView.findViewById(R.id.btnDeleteNote);
        }
    }
}
