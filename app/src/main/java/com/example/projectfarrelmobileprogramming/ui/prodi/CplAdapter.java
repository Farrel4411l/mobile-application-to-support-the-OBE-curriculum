package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Cpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas CplAdapter merupakan kelas Adapter untuk RecyclerView yang bertugas
 * menghubungkan data Capaian Pembelajaran Lulusan (CPL) dengan antarmuka pengguna (UI).
 * Adapter ini akan mengelola item-item individual dari list data CPL untuk ditampilkan di layar.
 */
public class CplAdapter extends RecyclerView.Adapter<CplAdapter.CplViewHolder> {

    // Deklarasi list yang menyimpan objek Cpl. Diinisialisasi dengan ArrayList kosong.
    private List<Cpl> cplList = new ArrayList<>();

    /**
     * Metode ini digunakan untuk mengatur atau memperbarui daftar data CPL.
     * Setelah data baru dimasukkan, metode ini akan memberitahukan RecyclerView agar merender ulang daftar.
     * @param cplList Daftar objek Cpl yang baru dari server.
     */
    public void setCplList(List<Cpl> cplList) {
        // Mengganti daftar saat ini dengan daftar yang baru
        this.cplList = cplList;
        // Memberi tahu adapter bahwa ada perubahan data secara keseluruhan,
        // sehingga adapter bisa menggambar ulang item-item pada RecyclerView.
        notifyDataSetChanged();
    }

    /**
     * Metode ini dipanggil oleh RecyclerView ketika perlu membuat ViewHolder baru
     * untuk merepresentasikan sebuah item. Di sinilah layout XML (item_cpl) di-inflate ke dalam bentuk objek View.
     */
    @NonNull
    @Override
    public CplViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Melakukan proses inflasi layout (mengubah item_cpl.xml menjadi objek View)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cpl, parent, false);
        // Mengembalikan instance ViewHolder yang baru menggunakan View tersebut
        return new CplViewHolder(view);
    }

    /**
     * Metode ini dipanggil oleh RecyclerView untuk menampilkan data pada posisi tertentu.
     * Pada tahap ini, kita akan memasukkan data dari model Cpl ke komponen UI di dalam ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull CplViewHolder holder, int position) {
        // Mengambil objek Cpl dari daftar sesuai dengan posisinya (indeks)
        Cpl cpl = cplList.get(position);
        
        // Mengatur teks untuk komponen tvIdCpl menggunakan ID dari objek Cpl
        holder.tvIdCpl.setText("CPL-" + cpl.getIdCpl());
        // Mengatur teks untuk komponen tvDeskripsiCpl menggunakan deskripsi dari objek Cpl
        holder.tvDeskripsiCpl.setText(cpl.getDeskripsiCpl());
    }

    /**
     * Mengembalikan jumlah total item di dalam daftar data yang dikelola oleh adapter ini.
     * @return jumlah data di cplList. Jika null, kembalikan 0.
     */
    @Override
    public int getItemCount() {
        return cplList != null ? cplList.size() : 0;
    }

    /**
     * Kelas ViewHolder (inner class) digunakan untuk mendefinisikan komponen-komponen antarmuka pengguna
     * (UI) yang terdapat pada sebuah item list individual (item_cpl.xml).
     * ViewHolder ini menahan (holds) referensi ke view, agar tidak perlu mencari view dengan findViewById secara berulang.
     */
    static class CplViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi TextView untuk ID CPL dan Deskripsi CPL
        TextView tvIdCpl, tvDeskripsiCpl;

        /**
         * Konstruktor dari CplViewHolder yang dipanggil saat instansiasi di onCreateViewHolder.
         * @param itemView Objek view dari item layout yang di-inflate.
         */
        public CplViewHolder(@NonNull View itemView) {
            super(itemView);
            // Menghubungkan variabel tvIdCpl dengan komponen TextView di XML berdasarkan ID-nya
            tvIdCpl = itemView.findViewById(R.id.tvIdCpl);
            // Menghubungkan variabel tvDeskripsiCpl dengan komponen TextView di XML berdasarkan ID-nya
            tvDeskripsiCpl = itemView.findViewById(R.id.tvDeskripsiCpl);
        }
    }
}
