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

public class AsesmenAdapter extends RecyclerView.Adapter<AsesmenAdapter.AsesmenViewHolder> {

    private List<Asesmen> asesmenList = new ArrayList<>();

    public void setAsesmenList(List<Asesmen> asesmenList) {
        this.asesmenList = asesmenList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AsesmenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asesmen, parent, false);
        return new AsesmenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsesmenViewHolder holder, int position) {
        Asesmen asesmen = asesmenList.get(position);
        holder.tvAsesmenNim.setText(asesmen.getNim());
        holder.tvAsesmenJenis.setText(asesmen.getJenisAsesmen());
        holder.tvAsesmenNama.setText(asesmen.getNama());
        holder.tvAsesmenBobot.setText("Bobot CPL: " + asesmen.getBobotCpl() + "%");
    }

    @Override
    public int getItemCount() {
        return asesmenList != null ? asesmenList.size() : 0;
    }

    static class AsesmenViewHolder extends RecyclerView.ViewHolder {
        TextView tvAsesmenNim, tvAsesmenJenis, tvAsesmenNama, tvAsesmenBobot;

        public AsesmenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAsesmenNim = itemView.findViewById(R.id.tvAsesmenNim);
            tvAsesmenJenis = itemView.findViewById(R.id.tvAsesmenJenis);
            tvAsesmenNama = itemView.findViewById(R.id.tvAsesmenNama);
            tvAsesmenBobot = itemView.findViewById(R.id.tvAsesmenBobot);
        }
    }
}
