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

public class RpsAdapter extends RecyclerView.Adapter<RpsAdapter.RpsViewHolder> {

    private List<Rps> rpsList = new ArrayList<>();

    public void setRpsList(List<Rps> rpsList) {
        this.rpsList = rpsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RpsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rps, parent, false);
        return new RpsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RpsViewHolder holder, int position) {
        Rps rps = rpsList.get(position);
        holder.tvRpsMkId.setText(rps.getIdMk());
        holder.tvRpsStatus.setText(rps.getStatus());
        holder.tvRpsDosen.setText(rps.getNamaDosen());
        holder.tvRpsTanggal.setText("Disusun: " + rps.getTanggalPenyusunan());
        holder.tvRpsDesc.setText(rps.getDeskripsiMk());
    }

    @Override
    public int getItemCount() {
        return rpsList != null ? rpsList.size() : 0;
    }

    static class RpsViewHolder extends RecyclerView.ViewHolder {
        TextView tvRpsMkId, tvRpsStatus, tvRpsDosen, tvRpsTanggal, tvRpsDesc;

        public RpsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRpsMkId = itemView.findViewById(R.id.tvRpsMkId);
            tvRpsStatus = itemView.findViewById(R.id.tvRpsStatus);
            tvRpsDosen = itemView.findViewById(R.id.tvRpsDosen);
            tvRpsTanggal = itemView.findViewById(R.id.tvRpsTanggal);
            tvRpsDesc = itemView.findViewById(R.id.tvRpsDesc);
        }
    }
}
