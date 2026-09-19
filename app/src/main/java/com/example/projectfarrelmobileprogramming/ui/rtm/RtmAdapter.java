package com.example.projectfarrelmobileprogramming.ui.rtm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Rtm;
import java.util.ArrayList;
import java.util.List;

/**
 * Kelas RtmAdapter
 * <p>
 * Adapter khusus bagi RecyclerView di layar RtmActivity.
 * Bertanggung jawab menerjemahkan sekumpulan data model (Rtm) menjadi
 * item-item UI visual yang dapat di-scroll dengan mulus karena mendayagunakan
 * pola desain ViewHolder.
 */
public class RtmAdapter extends RecyclerView.Adapter<RtmAdapter.RtmViewHolder> {

    // Wadah sementara untuk list data RTM yang diambil dari server
    private List<Rtm> rtmList = new ArrayList<>();

    /**
     * Memperbarui daftar RTM yang akan dimunculkan ke tampilan.
     * 
     * @param rtmList List Rtm baru dari hasil unduh API
     */
    public void setRtmList(List<Rtm> rtmList) {
        this.rtmList = rtmList;
        // Memicu pemberitahuan bagi RecyclerView bahwa datanya telah ditimpa dengan list yang baru.
        notifyDataSetChanged();
    }

    /**
     * Membuat suatu form atau baris kosong (ViewHolder) dari template XML saat sedang dibutuhkan.
     * 
     * @param parent Container untuk View, yaitu RecyclerView itu sendiri.
     * @param viewType Konteks tipe, jika ada beraneka ragam bentuk layout list.
     * @return Komponen custom RtmViewHolder baru.
     */
    @NonNull
    @Override
    public RtmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Mengkonversi layout XML `item_rtm` ke wujud objek View melalui LayoutInflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rtm, parent, false);
        return new RtmViewHolder(view);
    }

    /**
     * Menyuntikkan/Memasukkan (Bind) informasi per elemen pada array list ke dalam layout UI (ViewHolder).
     * 
     * @param holder ViewHolder tempat elemen-elemen TextView diletakkan.
     * @param position Urutan (index) baris dari data yang akan ditampilkan.
     */
    @Override
    public void onBindViewHolder(@NonNull RtmViewHolder holder, int position) {
        // Ambil elemen data dengan posisi yang sama dari rtmList
        Rtm rtm = rtmList.get(position);
        
        // Memasukkan masing-masing data dari variabel Rtm ke masing-masing TextView UI-nya
        holder.tvRtmBentuk.setText(rtm.getBentukTugas());
        holder.tvRtmWaktu.setText(rtm.getWaktuPengerjaan());
        holder.tvRtmJudul.setText(rtm.getJudulTugas());
        holder.tvRtmDesc.setText(rtm.getDeskripsiTugas());
    }

    /**
     * Berapa banyak baris dari elemen di dalam list?
     * 
     * @return Banyaknya elemen data dalam rtmList. (Atau nol jika kosong).
     */
    @Override
    public int getItemCount() {
        return rtmList != null ? rtmList.size() : 0;
    }

    /**
     * Merupakan sub-class ViewHolder.
     * Bertujuan untuk "Cache" View atau widget, sehingga tidak perlu bolak-balik menggunakan 
     * `findViewById` setiap kali user scroll layar, yang mana sangat boros memori.
     */
    static class RtmViewHolder extends RecyclerView.ViewHolder {
        // Representasi widget Text
        TextView tvRtmBentuk, tvRtmWaktu, tvRtmJudul, tvRtmDesc;

        public RtmViewHolder(@NonNull View itemView) {
            super(itemView);
            // Menyambungkan tag ID XML textview ke variabel lokal
            tvRtmBentuk = itemView.findViewById(R.id.tvRtmBentuk);
            tvRtmWaktu = itemView.findViewById(R.id.tvRtmWaktu);
            tvRtmJudul = itemView.findViewById(R.id.tvRtmJudul);
            tvRtmDesc = itemView.findViewById(R.id.tvRtmDesc);
        }
    }
}
