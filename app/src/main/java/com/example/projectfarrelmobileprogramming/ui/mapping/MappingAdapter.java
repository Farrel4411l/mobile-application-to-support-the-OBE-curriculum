package com.example.projectfarrelmobileprogramming.ui.mapping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * MappingAdapter adalah kelas adapter dinamis untuk RecyclerView pada fitur Pemetaan (Mapping).
 * Karena tipe data pemetaan memiliki keys (id_bk, id_mk, id_cpl, dsb) yang berubah-ubah sesuai pilihan pengguna,
 * adapter ini dirancang menggunakan tipe general (Object & LinkedTreeMap) dan diinjeksi
 * kunci-kunci spesifik tersebut dari MappingActivity.
 */
public class MappingAdapter extends RecyclerView.Adapter<MappingAdapter.MappingViewHolder> {

    // Menampung list data hasil parsing dari Retrofit sebagai generalisasi Object ArrayList
    private List<Object> mappingList = new ArrayList<>();
    // Variabel kunci source (sumber) pada array JSON (misal "id_bk" atau "id_cpmk")
    private String sourceKey;
    // Variabel kunci target pada array JSON (misal "id_mk" atau "id_cpl")
    private String targetKey;
    // Variabel label teks yang ditampilkan ke pengguna sebagai header Source UI (misal "Bahan Kajian")
    private String sourceLabel;
    // Variabel label teks yang ditampilkan ke pengguna sebagai header Target UI (misal "Mata Kuliah")
    private String targetLabel;

    /**
     * Memasukkan data dan seluruh properti metadata (kunci dan label teks) ke dalam instance Adapter.
     * Metode ini dipanggil oleh Activity setiap kali terjadi pergantian pilihan pada spinner dropdown menu.
     */
    public void setMappingData(List<Object> mappingList, String sourceKey, String targetKey, String sourceLabel, String targetLabel) {
        // Mengganti daftar mapping yang saat ini menjadi yang baru didapatkan
        this.mappingList = mappingList;
        // Menyimpan pola kunci JSON yang diminta, misalnya akan mengambil data berdasarkan "id_bk"
        this.sourceKey = sourceKey;
        // Menyimpan pola target JSON yang diminta
        this.targetKey = targetKey;
        // Menyimpan label deskripsi ui untuk asal
        this.sourceLabel = sourceLabel;
        // Menyimpan label deskripsi ui untuk tujuan
        this.targetLabel = targetLabel;
        // Memberikan notifikasi sistem ke RecyclerView bahwa terdapat update total data secara keseluruhan
        notifyDataSetChanged();
    }

    /**
     * Fungsi dari kerangka RecyclerView saat merender UI dan butuh membentuk kotak tampilan kosong (View) 
     * sebelum dimasukkan kontennya masing-masing.
     */
    @NonNull
    @Override
    public MappingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menerjemahkan (inflate) file desain item_mapping_card.xml menjadi struktur objek View UI
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mapping_card, parent, false);
        // Membuat dan me-return objek class ViewHolder dengan parameter view yang dibentuk barusan
        return new MappingViewHolder(view);
    }

    /**
     * Fungsi yang akan diulangi RecyclerView setiap kali suatu indeks baris perlu diberikan atau diisi dengan informasinya masing-masing.
     */
    @Override
    public void onBindViewHolder(@NonNull MappingViewHolder holder, int position) {
        // Mengambil objek general dari list pada posisi / urutan saat ini
        Object item = mappingList.get(position);
        
        // Memeriksa dengan reflection / instance checking apakah item tersebut adalah tipe internal LinkedTreeMap milik library GSON,
        // yang di mana biasanya Gson akan meng-cast JSON Object secara generik (apabila kelas model tidak ditentukan atau general)
        // ke dalam tipe LinkedTreeMap<String, Object>.
        if (item instanceof LinkedTreeMap) {
            // Kita secara paksa konversikan / cast object itu ke tipe LinkedTreeMap<String, Object>
            LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) item;
            
            // Mendapatkan nilai pemetaan kolom dari koleksi berdasarkan target key (e.g. key "id_bk") yang dikirimkan Activity
            Object sourceVal = map.get(sourceKey);
            // Mendapatkan nilai pemetaan kolom target dari koleksi (e.g. key "id_mk") yang dikirimkan Activity
            Object targetVal = map.get(targetKey);
            
            // Menampilkan string label utama dari Activity ke dalam elemen TextView untuk Source ("Bahan Kajian:")
            holder.tvSourceLabel.setText(sourceLabel);
            // Menampilkan string label utama dari Activity ke dalam elemen TextView untuk Target ("Mata Kuliah:")
            holder.tvTargetLabel.setText(targetLabel);
            
            // Mengubah tipe objek source menjadi bentuk String, jika objek itu tidak null tampilkan angkanya, namun apabila data API cacat atau null, tampilkan "-"
            holder.tvSource.setText(sourceVal != null ? String.valueOf(sourceVal) : "-");
            // Sama halnya seperti yang source, memvalidasi dan mengubah objek value target untuk dipasang di TextView
            holder.tvTarget.setText(targetVal != null ? String.valueOf(targetVal) : "-");
        }
    }

    /**
     * Fungsi internal RecyclerView guna mengukur seberapa banyak batas index item baris pada saat melakukan scrolling
     */
    @Override
    public int getItemCount() {
        // Merupakan ekspresi ternary untuk mengembalikan ukuran dari array mappingList jika valid (tidak null)
        return mappingList != null ? mappingList.size() : 0;
    }

    /**
     * ViewHolder ini dipakai oleh RecyclerView untuk mendeklarasikan ID komponen UI (View)
     * sekali saja, guna mengatasi kelemahan memanggil findViewById untuk setiap scroll.
     */
    static class MappingViewHolder extends RecyclerView.ViewHolder {
        // Menyiapkan variabel lokal bertipe TextView untuk masing-masing id widget pada file card (item layout)
        TextView tvSourceLabel, tvTargetLabel, tvSource, tvTarget;

        /**
         * Konstruktor default pada waktu list sedang di-inflate.
         */
        public MappingViewHolder(@NonNull View itemView) {
            // Meneruskan variabel layout view root ke konstraktor master ViewHolder
            super(itemView);
            // Binding ID layout xml pada tampilan source label ke variabel tvSourceLabel
            tvSourceLabel = itemView.findViewById(R.id.tvMappingSourceLabel);
            // Binding ID layout xml pada tampilan target label ke variabel tvTargetLabel
            tvTargetLabel = itemView.findViewById(R.id.tvMappingTargetLabel);
            // Binding ID layout xml pada tampilan data asli (Source) ke tvSource
            tvSource = itemView.findViewById(R.id.tvMappingSource);
            // Binding ID layout xml pada tampilan data target ke tvTarget
            tvTarget = itemView.findViewById(R.id.tvMappingTarget);
        }
    }
}
