package com.example.projectfarrelmobileprogramming.ui.mk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.BahanKajian;
import java.util.ArrayList;
import java.util.List;

/**
 * Kelas BkAdapter.
 * Adapter ini berfungsi menjembatani list data Bahan Kajian (dari model) dengan komponen RecyclerView.
 * Bertugas membuat ViewHolder untuk tiap item dan mengikat (bind) data ke komponen tampilan yang sesuai.
 */
public class BkAdapter extends RecyclerView.Adapter<BkAdapter.BkViewHolder> {

    // List untuk menyimpan data BahanKajian yang akan ditampilkan di RecyclerView
    private List<BahanKajian> bkList = new ArrayList<>();

    /**
     * Fungsi untuk memperbarui data list dalam adapter.
     * @param bkList List baru yang berisi data Bahan Kajian.
     */
    public void setBkList(List<BahanKajian> bkList) {
        // Mengganti isi list lama dengan list baru dari parameter
        this.bkList = bkList;
        // Memberi tahu RecyclerView bahwa keseluruhan set data telah berubah sehingga perlu di-render ulang
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Me-inflate (mengubah) file XML item_generic_card.xml menjadi objek View nyata di memori
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_card, parent, false);
        // Membuat dan mengembalikan instance BkViewHolder dengan tampilan hasil inflate
        return new BkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BkViewHolder holder, int position) {
        // Mengambil objek BahanKajian pada posisi spesifik di list
        BahanKajian bk = bkList.get(position);
        
        // Mengatur teks ID Bahan Kajian dengan format "BK-[id]"
        holder.tvId.setText("BK-" + bk.getIdBahanKajian());
        // Mengatur teks uraian atau deskripsi Bahan Kajian ke TextView yang sesuai
        holder.tvDesc.setText(bk.getUraianBahanKajian());
    }

    @Override
    public int getItemCount() {
        // Mengembalikan jumlah item pada list data (atau 0 jika list bernilai null)
        return bkList != null ? bkList.size() : 0;
    }

    /**
     * Kelas ViewHolder untuk memegang referensi ke elemen-elemen UI dari item RecyclerView,
     * yang berguna agar fungsi pencarian View (findViewById) tidak terus dilakukan (meningkatkan performa).
     */
    static class BkViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi TextView untuk menampilkan ID dan deskripsi Bahan Kajian
        TextView tvId, tvDesc;

        /**
         * Konstruktor dari ViewHolder yang menerima referensi View item (itemView).
         */
        public BkViewHolder(@NonNull View itemView) {
            // Memanggil konstruktor superclass
            super(itemView);
            // Menghubungkan variabel TextView tvId dengan komponen di XML menggunakan ID tvId
            tvId = itemView.findViewById(R.id.tvId);
            // Menghubungkan variabel TextView tvDesc dengan komponen di XML menggunakan ID tvDesc
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
