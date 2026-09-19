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

public class MkAdapter extends RecyclerView.Adapter<MkAdapter.MkViewHolder> {

    private List<MataKuliah> mkList = new ArrayList<>();

    public void setMkList(List<MataKuliah> mkList) {
        this.mkList = mkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mk, parent, false);
        return new MkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MkViewHolder holder, int position) {
        MataKuliah mk = mkList.get(position);
        holder.tvNamaMk.setText(mk.getNamaMk());
        holder.tvSks.setText(mk.getSks() + " SKS");
        holder.tvSemester.setText("Semester " + mk.getSemester());
        holder.tvKodeMk.setText(mk.getIdMk());
        
        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(v.getContext(), MkDetailActivity.class);
            intent.putExtra("EXTRA_MK", mk);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mkList != null ? mkList.size() : 0;
    }

    static class MkViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaMk, tvSks, tvSemester, tvKodeMk;

        public MkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMk = itemView.findViewById(R.id.tvNamaMk);
            tvSks = itemView.findViewById(R.id.tvSks);
            tvSemester = itemView.findViewById(R.id.tvSemester);
            tvKodeMk = itemView.findViewById(R.id.tvKodeMk);
        }
    }
}
