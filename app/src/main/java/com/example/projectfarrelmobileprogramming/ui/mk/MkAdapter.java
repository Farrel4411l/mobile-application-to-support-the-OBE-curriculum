package com.example.projectfarrelmobileprogramming.ui.mk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas MkAdapter.
 * Adapter ini berfungsi mengelola daftar Mata Kuliah (MK) untuk ditampilkan pada antarmuka RecyclerView.
 * Juga menangani interaksi klik dari tiap item, dan meneruskan data Mata Kuliah yang diklik ke Activity Detail.
 */
public class MkAdapter extends RecyclerView.Adapter<MkAdapter.MkViewHolder> {

    // Menyimpan daftar mata kuliah dari ViewModel / Repository
    private List<MataKuliah> mkList = new ArrayList<>();

    /**
     * Memasukkan atau memperbarui list data dalam adapter ini.
     * @param mkList List objek MataKuliah
     */
    public void setMkList(List<MataKuliah> mkList) {
        // Mereplace data sebelumnya
        this.mkList = mkList;
        // Memberi peringatan pada RecyclerView bahwa datanya telah berubah (refresh)
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Melakukan Inflasi XML layout dari setiap baris item yaitu item_mk.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mk, parent, false);
        // Mengembalikan MkViewHolder untuk mengikat sub-komponen UI dari view tersebut
        return new MkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MkViewHolder holder, int position) {
        // Mengambil objek MataKuliah berdasarkan posisi pada List
        MataKuliah mk = mkList.get(position);
        
        // Memasukkan nama Mata Kuliah
        holder.tvNamaMk.setText(mk.getNamaMk());
        // Memasukkan jumlah SKS ditambah dengan string " SKS"
        holder.tvSks.setText(mk.getSks() + " SKS");
        // Memasukkan teks yang mengindikasikan letak semester
        holder.tvSemester.setText("Semester " + mk.getSemester());
        // Memasukkan kode/ID unik untuk Mata Kuliah tersebut
        holder.tvKodeMk.setText(mk.getIdMk());
        
        // Menentukan aksi saat sebuah item (baris) ditekan oleh pengguna
        holder.itemView.setOnClickListener(v -> {
            // Membuat Intent untuk berpindah ke layar Detail (MkDetailActivity)
            android.content.Intent intent = new android.content.Intent(v.getContext(), MkDetailActivity.class);
            // Menitipkan objek Mata Kuliah saat ini ke dalam Intent (perlu Serialize/Parcelable)
            intent.putExtra("EXTRA_MK", mk);
            // Memulai proses perpindahan ke Activity yang dituju
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Menghitung panjang/jumlah data yang akan dirender dalam RecyclerView
        return mkList != null ? mkList.size() : 0;
    }

    /**
     * MkViewHolder adalah subclass RecyclerView.ViewHolder yang memegang referensi komponen view (TextView)
     * untuk setiap list item, sehingga tidak perlu mencari elemennya kembali (findViewById) saat list digulir (scrolled).
     */
    static class MkViewHolder extends RecyclerView.ViewHolder {
        // Komponen TextView untuk menampung teks dari data model
        TextView tvNamaMk, tvSks, tvSemester, tvKodeMk;

        /**
         * Konstruktor yang memetakan elemen UI (XML) ke variabel.
         */
        public MkViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inisialisasi text view nama MK
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            // Inisialisasi text view SKS MK
            tvSks = itemView.findViewById(R.id.tvSks);
            // Inisialisasi text view Semester MK
            tvSemester = itemView.findViewById(R.id.tvSemester);
            // Inisialisasi text view Kode/ID MK
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
        }
    }
}
