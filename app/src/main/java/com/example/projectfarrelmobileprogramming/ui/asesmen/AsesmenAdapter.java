package com.example.projectfarrelmobileprogramming.ui.asesmen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Asesmen;
import java.util.ArrayList;
import java.util.List;

/**
 * AsesmenAdapter adalah kelas adapter untuk komponen RecyclerView. 
 * Fungsi utama kelas ini adalah untuk menjadi jembatan (adapter) yang 
 * menghubungkan antara kumpulan data (list) Asesmen dengan elemen visual (layout/item) pada UI.
 */
public class AsesmenAdapter extends RecyclerView.Adapter<AsesmenAdapter.AsesmenViewHolder> {

    // Membuat variabel daftar asesmen internal dan menginisialisasinya sebagai ArrayList kosong
    // List ini yang akan menyimpan sumber data untuk dirender
    private List<Asesmen> asesmenList = new ArrayList<>();

    /**
     * Metode ini digunakan untuk mengatur atau memperbarui daftar data asesmen ke dalam adapter.
     * Biasanya dipanggil setelah data berhasil diambil dari server.
     */
    public void setAsesmenList(List<Asesmen> asesmenList) {
        // Menyalin daftar data yang baru masuk ke dalam variabel asesmenList milik adapter
        this.asesmenList = asesmenList;
        // Memberi tahu adapter bahwa struktur data telah berubah secara keseluruhan, sehingga daftar tampilan diperbarui secara langsung
        notifyDataSetChanged();
    }

    /**
     * Metode onCreateViewHolder dipanggil ketika RecyclerView membutuhkan tampilan daftar (ViewHolder) yang baru
     * untuk merepresentasikan sebuah item. Ini terjadi saat aplikasi baru mulai membangun item di layar.
     */
    @NonNull
    @Override
    public AsesmenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Melakukan inflating atau mengubah file desain XML (item_asesmen.xml) menjadi objek View pada memori
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asesmen, parent, false);
        // Mengembalikan objek AsesmenViewHolder yang sudah membungkus View (layout) yang di-inflate tersebut
        return new AsesmenViewHolder(view);
    }

    /**
     * Metode onBindViewHolder ini digunakan untuk mengaitkan dan mengatur data ke dalam tampilan (View) yang ada di ViewHolder.
     * Dipanggil setiap kali RecyclerView akan menampilkan sebuah baris berdasarkan posisinya.
     */
    @Override
    public void onBindViewHolder(@NonNull AsesmenViewHolder holder, int position) {
        // Mengambil objek Asesmen dari dalam list berdasarkan posisi (indeks) urutan data yang sedang ingin ditampilkan
        Asesmen asesmen = asesmenList.get(position);
        
        // Memasukkan nilai NIM dari objek asesmen ke dalam komponen teks (TextView) tvAsesmenNim
        holder.tvAsesmenNim.setText(asesmen.getNim());
        // Memasukkan nilai jenis asesmen ke dalam TextView tvAsesmenJenis
        holder.tvAsesmenJenis.setText(asesmen.getJenisAsesmen());
        // Memasukkan nilai nama pengguna ke dalam TextView tvAsesmenNama
        holder.tvAsesmenNama.setText(asesmen.getNama());
        // Memasukkan nilai bobot CPL yang disertai prefix "Bobot CPL: " dan persen ke dalam TextView tvAsesmenBobot
        holder.tvAsesmenBobot.setText("Bobot CPL: " + asesmen.getBobotCpl() + "%");
    }

    /**
     * Mengembalikan jumlah total item data yang akan ditampilkan dalam daftar (RecyclerView).
     * Ini digunakan oleh layout manager untuk menentukan seberapa banyak item yang bisa di-scroll.
     */
    @Override
    public int getItemCount() {
        // Mengembalikan ukuran (size) dari asesmenList jika tidak bernilai null, dan 0 jika sebaliknya (list kosong)
        return asesmenList != null ? asesmenList.size() : 0;
    }

    /**
     * AsesmenViewHolder adalah kelas yang menyimpan referensi komponen UI dari masing-masing item (baris).
     * Dengan menampung (cache) View menggunakan ViewHolder, aplikasi tidak perlu memanggil findViewById() 
     * berulang kali selama proses scroll, yang akan memakan banyak memori dan memperlambat kinerja.
     */
    static class AsesmenViewHolder extends RecyclerView.ViewHolder {
        // Mendeklarasikan objek-objek TextView yang merepresentasikan setiap informasi asesmen
        TextView tvAsesmenNim, tvAsesmenJenis, tvAsesmenNama, tvAsesmenBobot;

        /**
         * Konstruktor dari ViewHolder yang menerima parameter baris view hasil inflate layout.
         */
        public AsesmenViewHolder(@NonNull View itemView) {
            // Memanggil konstruktor super (kelas dasar dari ViewHolder)
            super(itemView);
            // Menautkan TextView NIM dengan ID tvAsesmenNim di XML layout (item_asesmen.xml)
            tvAsesmenNim = itemView.findViewById(R.id.tvAsesmenNim);
            // Menautkan TextView Jenis Asesmen dengan ID tvAsesmenJenis di XML layout
            tvAsesmenJenis = itemView.findViewById(R.id.tvAsesmenJenis);
            // Menautkan TextView Nama dengan ID tvAsesmenNama di XML layout
            tvAsesmenNama = itemView.findViewById(R.id.tvAsesmenNama);
            // Menautkan TextView Bobot dengan ID tvAsesmenBobot di XML layout
            tvAsesmenBobot = itemView.findViewById(R.id.tvAsesmenBobot);
        }
    }
}
