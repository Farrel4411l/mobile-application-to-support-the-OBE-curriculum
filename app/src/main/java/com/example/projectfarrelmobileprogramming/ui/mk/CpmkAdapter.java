package com.example.projectfarrelmobileprogramming.ui.mk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Cpmk;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas CpmkAdapter.
 * Bertanggung jawab untuk mengatur data Capaian Pembelajaran Mata Kuliah (CPMK)
 * agar dapat ditampilkan pada RecyclerView, khusus digunakan dalam layar Detail Mata Kuliah.
 */
public class CpmkAdapter extends RecyclerView.Adapter<CpmkAdapter.CpmkViewHolder> {

    // List lokal untuk menyimpan kumpulan data Cpmk
    private List<Cpmk> cpmkList = new ArrayList<>();

    /**
     * Memperbarui daftar CPMK yang ada di dalam adapter.
     * @param cpmkList list CPMK baru yang ingin ditampilkan.
     */
    public void setCpmkList(List<Cpmk> cpmkList) {
        // Mengisi ulang daftar data dengan list terbaru
        this.cpmkList = cpmkList;
        // Memberi tahu RecyclerView untuk me-render ulang tampilannya karena data berubah
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CpmkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Mengubah layout XML (item_cpmk.xml) menjadi objek View (inflating)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cpmk, parent, false);
        // Mengembalikan objek CpmkViewHolder baru yang menampung View hasil inflate
        return new CpmkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CpmkViewHolder holder, int position) {
        // Mengambil objek CPMK tunggal dari list berdasarkan indeks posisi saat ini
        Cpmk cpmk = cpmkList.get(position);
        
        // Memasukkan data Kode CPMK ke dalam elemen teks UI
        holder.tvKodeCpmk.setText(cpmk.getKodeCpmk());
        // Memasukkan data Deskripsi CPMK ke dalam elemen teks UI
        holder.tvDeskripsiCpmk.setText(cpmk.getDeskripsiCpmk());
        // Memasukkan data Korelasi CPL (Capaian Pembelajaran Lulusan) ke dalam elemen teks dengan panah "->"
        holder.tvKorelasiCpl.setText("→ " + cpmk.getIdCpl());
    }

    @Override
    public int getItemCount() {
        // Mengembalikan total elemen dalam list data CPMK
        return cpmkList != null ? cpmkList.size() : 0;
    }

    /**
     * Kelas ViewHolder untuk mem-cache referensi tampilan (View) pada tiap item list (RecyclerView).
     * Mencegah inefisiensi yang terjadi jika findViewById dipanggil terus-menerus.
     */
    static class CpmkViewHolder extends RecyclerView.ViewHolder {
        // TextView untuk Kode, Deskripsi, dan Korelasi CPL
        TextView tvKodeCpmk;
        TextView tvDeskripsiCpmk;
        TextView tvKorelasiCpl;

        /**
         * Konstruktor ViewHolder.
         * @param itemView Tampilan untuk satu item dalam list.
         */
        public CpmkViewHolder(@NonNull View itemView) {
            // Memanggil superclass RecyclerView.ViewHolder
            super(itemView);
            // Menginisialisasi TextView kode CPMK menggunakan ID dari layout
            tvKodeCpmk = itemView.findViewById(R.id.tvKodeCpmk);
            // Menginisialisasi TextView deskripsi CPMK
            tvDeskripsiCpmk = itemView.findViewById(R.id.tvDeskripsiCpmk);
            // Menginisialisasi TextView korelasi CPL
            tvKorelasiCpl = itemView.findViewById(R.id.tvKorelasiCpl);
        }
    }
}
