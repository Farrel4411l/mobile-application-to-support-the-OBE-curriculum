package com.example.projectfarrelmobileprogramming.ui.mapping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class MappingAdapter extends RecyclerView.Adapter<MappingAdapter.MappingViewHolder> {

    private List<Object> mappingList = new ArrayList<>();
    private String sourceKey;
    private String targetKey;
    private String sourceLabel;
    private String targetLabel;

    public void setMappingData(List<Object> mappingList, String sourceKey, String targetKey, String sourceLabel, String targetLabel) {
        this.mappingList = mappingList;
        this.sourceKey = sourceKey;
        this.targetKey = targetKey;
        this.sourceLabel = sourceLabel;
        this.targetLabel = targetLabel;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MappingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mapping_card, parent, false);
        return new MappingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MappingViewHolder holder, int position) {
        Object item = mappingList.get(position);
        if (item instanceof LinkedTreeMap) {
            LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) item;
            
            Object sourceVal = map.get(sourceKey);
            Object targetVal = map.get(targetKey);
            
            holder.tvSourceLabel.setText(sourceLabel);
            holder.tvTargetLabel.setText(targetLabel);
            
            holder.tvSource.setText(sourceVal != null ? String.valueOf(sourceVal) : "-");
            holder.tvTarget.setText(targetVal != null ? String.valueOf(targetVal) : "-");
        }
    }

    @Override
    public int getItemCount() {
        return mappingList != null ? mappingList.size() : 0;
    }

    static class MappingViewHolder extends RecyclerView.ViewHolder {
        TextView tvSourceLabel, tvTargetLabel, tvSource, tvTarget;

        public MappingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSourceLabel = itemView.findViewById(R.id.tvMappingSourceLabel);
            tvTargetLabel = itemView.findViewById(R.id.tvMappingTargetLabel);
            tvSource = itemView.findViewById(R.id.tvMappingSource);
            tvTarget = itemView.findViewById(R.id.tvMappingTarget);
        }
    }
}
