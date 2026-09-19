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

public class RtmAdapter extends RecyclerView.Adapter<RtmAdapter.RtmViewHolder> {

    private List<Rtm> rtmList = new ArrayList<>();

    public void setRtmList(List<Rtm> rtmList) {
        this.rtmList = rtmList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RtmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rtm, parent, false);
        return new RtmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RtmViewHolder holder, int position) {
        Rtm rtm = rtmList.get(position);
        holder.tvRtmBentuk.setText(rtm.getBentukTugas());
        holder.tvRtmWaktu.setText(rtm.getWaktuPengerjaan());
        holder.tvRtmJudul.setText(rtm.getJudulTugas());
        holder.tvRtmDesc.setText(rtm.getDeskripsiTugas());
    }

    @Override
    public int getItemCount() {
        return rtmList != null ? rtmList.size() : 0;
    }

    static class RtmViewHolder extends RecyclerView.ViewHolder {
        TextView tvRtmBentuk, tvRtmWaktu, tvRtmJudul, tvRtmDesc;

        public RtmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRtmBentuk = itemView.findViewById(R.id.tvRtmBentuk);
            tvRtmWaktu = itemView.findViewById(R.id.tvRtmWaktu);
            tvRtmJudul = itemView.findViewById(R.id.tvRtmJudul);
            tvRtmDesc = itemView.findViewById(R.id.tvRtmDesc);
        }
    }
}
