package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.Cpl;

import java.util.ArrayList;
import java.util.List;

public class CplAdapter extends RecyclerView.Adapter<CplAdapter.CplViewHolder> {

    private List<Cpl> cplList = new ArrayList<>();

    public void setCplList(List<Cpl> cplList) {
        this.cplList = cplList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CplViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cpl, parent, false);
        return new CplViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CplViewHolder holder, int position) {
        Cpl cpl = cplList.get(position);
        holder.tvIdCpl.setText("CPL-" + cpl.getIdCpl());
        holder.tvDeskripsiCpl.setText(cpl.getDeskripsiCpl());
    }

    @Override
    public int getItemCount() {
        return cplList != null ? cplList.size() : 0;
    }

    static class CplViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdCpl, tvDeskripsiCpl;

        public CplViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdCpl = itemView.findViewById(R.id.tvIdCpl);
            tvDeskripsiCpl = itemView.findViewById(R.id.tvDeskripsiCpl);
        }
    }
}
