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

public class PlAdapter extends RecyclerView.Adapter<PlAdapter.PlViewHolder> {

    private List<ProfilLulusan> plList = new ArrayList<>();

    public void setPlList(List<ProfilLulusan> plList) {
        this.plList = plList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_card, parent, false);
        return new PlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlViewHolder holder, int position) {
        ProfilLulusan pl = plList.get(position);
        holder.tvId.setText("PL-" + pl.getIdPl());
        holder.tvDesc.setText(pl.getDeskripsiPl());
    }

    @Override
    public int getItemCount() {
        return plList != null ? plList.size() : 0;
    }

    static class PlViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDesc;

        public PlViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
