package com.example.projectfarrelmobileprogramming.ui.rps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Rps;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas RpsAdapter
 * <p>
 * Adapter untuk RecyclerView yang bertanggung jawab memanajemen, memformat, 
 * dan menampilkan setiap data dari daftar (list) RPS ke layar.
 * Menggunakan pola ViewHolder agar penggunaan resource lebih hemat karena View 
 * di-reuse saat menggulir list (scrolling).
 */
public class RpsAdapter extends RecyclerView.Adapter<RpsAdapter.RpsViewHolder> {

    // Menyimpan daftar RPS yang akan ditampilkan, diinisialisasi sebagai ArrayList kosong
    private List<Rps> rpsList = new ArrayList<>();

    /**
     * Memperbarui daftar RPS di adapter.
     * 
     * @param rpsList List RPS terbaru dari API
     */
    public void setRpsList(List<Rps> rpsList) {
        this.rpsList = rpsList;
        // Memberi tahu adapter bahwa data telah berubah agar RecyclerView me-render ulang tampilannya
        notifyDataSetChanged();
    }

    /**
     * Dipanggil RecyclerView ketika perlu membuat objek ViewHolder baru
     * 
     * @param parent Grup parent view
     * @param viewType tipe layout (bila ada lebih dari satu layout/item type)
     * @return instance dari RpsViewHolder dengan layout yang telah di-inflate
     */
    @NonNull
    @Override
    public RpsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Meng-inflate layout per item (item_rps.xml) ke sebuah objek View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rps, parent, false);
        return new RpsViewHolder(view);
    }

    /**
     * Dipanggil oleh RecyclerView untuk menampilkan data pada posisi tertentu di list.
     * Mengikat (binding) objek model Rps ke View (TextView, dll) di ViewHolder.
     * 
     * @param holder RpsViewHolder tempat komponen UI berada
     * @param position Posisi data yang ada di rpsList
     */
    @Override
    public void onBindViewHolder(@NonNull RpsViewHolder holder, int position) {
        // Mengambil objek Rps pada posisi index yang diminta
        Rps rps = rpsList.get(position);
        
        // Memasang teks dari data ke setiap TextView
        holder.tvRpsMkId.setText(rps.getIdMk());
        holder.tvRpsStatus.setText(rps.getStatus());
        holder.tvRpsDosen.setText(rps.getNamaDosen());
        holder.tvRpsTanggal.setText("Disusun: " + rps.getTanggalPenyusunan());
        holder.tvRpsDesc.setText(rps.getDeskripsiMk());
    }

    /**
     * Mendapatkan jumlah total baris/item yang tersedia untuk ditampilkan.
     * 
     * @return ukuran (size) dari list rpsList, atau 0 jika belum ada.
     */
    @Override
    public int getItemCount() {
        return rpsList != null ? rpsList.size() : 0;
    }

    /**
     * Kelas ViewHolder yang merepresentasikan layout item_rps.xml.
     * Bertugas "memegang" referensi widget-widget di layout untuk setiap baris daftar.
     */
    static class RpsViewHolder extends RecyclerView.ViewHolder {
        // Referensi ke tiap TextView yang terdapat pada satu baris Rps
        TextView tvRpsMkId, tvRpsStatus, tvRpsDosen, tvRpsTanggal, tvRpsDesc;

        public RpsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Menautkan tiap komponen teks dengan widget di XML menggunakan findViewById
            tvRpsMkId = itemView.findViewById(R.id.tvRpsMkId);
            tvRpsStatus = itemView.findViewById(R.id.tvRpsStatus);
            tvRpsDosen = itemView.findViewById(R.id.tvRpsDosen);
            tvRpsTanggal = itemView.findViewById(R.id.tvRpsTanggal);
            tvRpsDesc = itemView.findViewById(R.id.tvRpsDesc);
        }
    }
}
