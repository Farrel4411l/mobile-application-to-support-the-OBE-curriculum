package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Prodi;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas ProdiAdapter bertugas sebagai jembatan antara sekumpulan data objek Program Studi (Prodi)
 * dan antarmuka RecyclerView, dengan cara menyiapkan ViewHolder yang bertugas merender per-item layout.
 */
public class ProdiAdapter extends RecyclerView.Adapter<ProdiAdapter.ProdiViewHolder> {

    // Struktur data ArrayList internal untuk menyimpan daftar objek Prodi
    private List<Prodi> prodiList = new ArrayList<>();

    /**
     * Berfungsi untuk mengisi atau memperbarui data List Prodi saat ini 
     * lalu secara otomatis menyuruh adapter untuk merefresh komponen layar RecyclerView.
     * @param prodiList merupakan list referensi sumber objek Prodi.
     */
    public void setProdiList(List<Prodi> prodiList) {
        // Mengganti isi data lama dengan isi data baru
        this.prodiList = prodiList;
        // Memberi perintah penyegaran (rendering ulang) daftar list di UI
        notifyDataSetChanged();
    }

    /**
     * Membangun objek view per item dari layout xml ('item_prodi.xml').
     * Proses ini disebut dengan inflating.
     */
    @NonNull
    @Override
    public ProdiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout spesifik item list program studi dari XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodi, parent, false);
        // Mengirimkan View tersebut untuk diproses dan disimpan instance di dalam ViewHolder
        return new ProdiViewHolder(view);
    }

    /**
     * Melakukan bind data: mencocokkan data list (model Prodi) pada baris/indeks (position) 
     * tertentu ke ViewHolder dari tampilan.
     */
    @Override
    public void onBindViewHolder(@NonNull ProdiViewHolder holder, int position) {
        // Mendapatkan data Prodi pada posisi/indeks tertentu
        Prodi prodi = prodiList.get(position);
        
        // Memasukkan dan menyusun informasi nama, jenjang, dan ID ke dalam komponen TextView UI
        holder.tvNamaProdi.setText(prodi.getNamaProdi());
        holder.tvJenjang.setText("Jenjang: " + prodi.getJenjang());
        holder.tvKodeProdi.setText("Kode: " + prodi.getIdProdi());

        // Mengonfigurasi event onclick (klik sentuhan) pada tiap-tiap item daftar list
        holder.itemView.setOnClickListener(v -> {
            // Jika sebuah list ditekan, buat objek Intent dengan tujuan ke ProdiDetailActivity
            android.content.Intent intent = new android.content.Intent(v.getContext(), ProdiDetailActivity.class);
            // Menyisipkan / Passing objek model 'prodi' (memerlukan implementasi Serializable/Parcelable pada model Prodi)
            // Menggunakan key "EXTRA_PRODI".
            intent.putExtra("EXTRA_PRODI", prodi);
            // Memulai (start) activity ProdiDetailActivity 
            v.getContext().startActivity(intent);
        });
    }

    /**
     * Memberitahu Android System berapa total data dari list agar sistem tau seberapa panjang recyclerview ini.
     */
    @Override
    public int getItemCount() {
        return prodiList != null ? prodiList.size() : 0;
    }

    /**
     * Inner class atau ViewHolder ini menyimpan instans dari tampilan/elemen XML sehingga
     * tidak perlu dilakukan pencarian ID berulang kali. Ini adalah kunci performa RecyclerView.
     */
    static class ProdiViewHolder extends RecyclerView.ViewHolder {
        // Deklarasi field referensi untuk tulisan di UI layout (Nama, Jenjang, Kode prodi)
        TextView tvNamaProdi, tvJenjang, tvKodeProdi;

        /**
         * Konstruktor ProdiViewHolder.
         * @param itemView adalah pandangan/tampilan dari sepotong (sebaris) XML.
         */
        public ProdiViewHolder(@NonNull View itemView) {
            super(itemView);
            // Menautkan ID Layout dengan variabel ini
            tvNamaProdi = itemView.findViewById(R.id.tvNamaProdi);
            tvJenjang = itemView.findViewById(R.id.tvJenjang);
            tvKodeProdi = itemView.findViewById(R.id.tvKodeProdi);
        }
    }
}
