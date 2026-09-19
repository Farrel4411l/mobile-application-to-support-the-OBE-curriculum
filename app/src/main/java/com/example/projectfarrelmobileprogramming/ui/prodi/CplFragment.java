package com.example.projectfarrelmobileprogramming.ui.prodi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfarrelmobileprogramming.R;
import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.Cpl;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CplFragment extends Fragment {

    private String idProdi;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private CplAdapter adapter;

    public static CplFragment newInstance(String idProdi) {
        CplFragment fragment = new CplFragment();
        Bundle args = new Bundle();
        args.putString("ID_PRODI", idProdi);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idProdi = getArguments().getString("ID_PRODI");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        
        rv = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CplAdapter();
        rv.setAdapter(adapter);

        loadData();
        return view;
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getCpl(idProdi, 100).enqueue(new Callback<ApiResponse<Cpl>>() {
            @Override
            public void onResponse(Call<ApiResponse<Cpl>> call, Response<ApiResponse<Cpl>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    if (response.body().getData().isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText("Tidak ada CPL untuk prodi ini.");
                    } else {
                        rv.setVisibility(View.VISIBLE);
                        adapter.setCplList(response.body().getData());
                    }
                } else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Gagal mengambil data CPL.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Cpl>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("Koneksi Error: " + t.getMessage());
            }
        });
    }
}
