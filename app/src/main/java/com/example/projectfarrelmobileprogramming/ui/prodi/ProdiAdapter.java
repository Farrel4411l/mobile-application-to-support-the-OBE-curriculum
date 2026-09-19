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

public class ProdiAdapter extends RecyclerView.Adapter<ProdiAdapter.ProdiViewHolder> {

    private List<Prodi> prodiList = new ArrayList<>();

    public void setProdiList(List<Prodi> prodiList) {
        this.prodiList = prodiList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodi, parent, false);
        return new ProdiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdiViewHolder holder, int position) {
        Prodi prodi = prodiList.get(position);
        holder.tvNamaProdi.setText(prodi.getNamaProdi());
        holder.tvJenjang.setText("Jenjang: " + prodi.getJenjang());
        holder.tvKodeProdi.setText("Kode: " + prodi.getIdProdi());

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(v.getContext(), ProdiDetailActivity.class);
            intent.putExtra("EXTRA_PRODI", prodi);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return prodiList != null ? prodiList.size() : 0;
    }

    static class ProdiViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaProdi, tvJenjang, tvKodeProdi;

        public ProdiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaProdi = itemView.findViewById(R.id.tvNamaProdi);
            tvJenjang = itemView.findViewById(R.id.tvJenjang);
            tvKodeProdi = itemView.findViewById(R.id.tvKodeProdi);
        }
    }
}
