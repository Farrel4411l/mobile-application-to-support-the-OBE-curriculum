package com.example.projectfarrelmobileprogramming.ui.mk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.BahanKajian;
import java.util.ArrayList;
import java.util.List;

public class BkAdapter extends RecyclerView.Adapter<BkAdapter.BkViewHolder> {

    private List<BahanKajian> bkList = new ArrayList<>();

    public void setBkList(List<BahanKajian> bkList) {
        this.bkList = bkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic_card, parent, false);
        return new BkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BkViewHolder holder, int position) {
        BahanKajian bk = bkList.get(position);
        holder.tvId.setText("BK-" + bk.getIdBahanKajian());
        holder.tvDesc.setText(bk.getUraianBahanKajian());
    }

    @Override
    public int getItemCount() {
        return bkList != null ? bkList.size() : 0;
    }

    static class BkViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDesc;

        public BkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
