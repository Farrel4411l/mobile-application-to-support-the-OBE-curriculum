package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.ProfilLulusan;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas PlAdapter adalah adapter yang menangani pengolahan list data model "ProfilLulusan".
 * Konsepnya serupa dengan adapter list RecyclerView lainnya: menjembatani dan menampilkan data ke layar pengguna.
 */
public class PlAdapter extends RecyclerView.Adapter<PlAdapter.PlViewHolder> {

    // Menyediakan list kosong untuk diisi dari server. 
    private List<ProfilLulusan> plList = new ArrayList<>();

    /**
     * Memperbarui daftar Profil Lulusan yang dimuat di memori adapter ini dan me-refresh view di layar.
     * @param plList List yang menyimpan objek-objek ProfilLulusan.
     */
    public void setPlList(List<ProfilLulusan> plList) {
        // Simpan data di list internal kelas
        this.plList = plList;
        // Panggil notifyDataSetChanged agar RecyclerView me-render ulang tampilannya dengan data terkini
        notifyDataSetChanged();
    }

    /**
     * Metode yang dipanggil RecyclerView saat membuat ViewHolder baru ketika tidak ada ViewHolder 
     * yang bisa didaur ulang. Ini membangun instance View baru dari file layout XML (item_generic_card).
     */
    @NonNull
    @Override
    public PlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Melakukan proses inflasi layout dengan 'LayoutInflater'
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_card, parent, false);
        // Mengembalikan PlViewHolder yang siap dipakai 
        return new PlViewHolder(view);
    }

    /**
     * Metode ini dipanggil saat menghubungkan informasi (data model) ke komponen UI (TextView). 
     * Digunakan secara dinamis mengikuti posisi index RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull PlViewHolder holder, int position) {
        // Tarik data profil lulusan satu persatu sesuai posisinya di list
        ProfilLulusan pl = plList.get(position);
        // Masukkan ID Profil ke dalam tampilan tvId
        holder.tvId.setText("PL-" + pl.getIdPl());
        // Masukkan deksripsi/teks profil lulusan ke dalam tampilan tvDesc
        holder.tvDesc.setText(pl.getDeskripsiPl());
    }

    /**
     * Jumlah komponen ProfilLulusan di adapter ini. RecyclerView akan berhenti membangun baris ketika sampai di angka ini.
     * @return Nilai integer total dari list (ukuran list).
     */
    @Override
    public int getItemCount() {
        return plList != null ? plList.size() : 0;
    }

    /**
     * PlViewHolder adalah wadah tempat referensi ke tampilan dalam suatu layout item.
     * Dengan menahan ID UI, aplikasi mencegah panggilan findViewById secara berat dan berulang-ulang untuk list.
     */
    static class PlViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi field TextView di elemen item baris
        TextView tvId, tvDesc;

        /**
         * Konstruktor di mana referensi layout diikat dengan variabel kelas.
         * @param itemView Tampilan untuk direferensikan dalam wadah ini.
         */
        public PlViewHolder(@NonNull View itemView) {
            super(itemView);
            // Menghubungkan ID view di layout "item_generic_card.xml" dengan referensi objek
            tvId = itemView.findViewById(R.id.tvId);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
