package com.example.projectfarrelmobileprogramming.ui.mk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Cpmk;

import java.util.ArrayList;
import java.util.List;

public class CpmkAdapter extends RecyclerView.Adapter<CpmkAdapter.CpmkViewHolder> {

    private List<Cpmk> cpmkList = new ArrayList<>();

    public void setCpmkList(List<Cpmk> cpmkList) {
        this.cpmkList = cpmkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CpmkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cpmk, parent, false);
        return new CpmkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CpmkViewHolder holder, int position) {
        Cpmk cpmk = cpmkList.get(position);
        holder.tvKodeCpmk.setText(cpmk.getKodeCpmk());
        holder.tvDeskripsiCpmk.setText(cpmk.getDeskripsiCpmk());
        holder.tvKorelasiCpl.setText("→ " + cpmk.getIdCpl());
    }

    @Override
    public int getItemCount() {
        return cpmkList != null ? cpmkList.size() : 0;
    }

    static class CpmkViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeCpmk;
        TextView tvDeskripsiCpmk;
        TextView tvKorelasiCpl;

        public CpmkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeCpmk = itemView.findViewById(R.id.tvKodeCpmk);
            tvDeskripsiCpmk = itemView.findViewById(R.id.tvDeskripsiCpmk);
            tvKorelasiCpl = itemView.findViewById(R.id.tvKorelasiCpl);
        }
    }
}
