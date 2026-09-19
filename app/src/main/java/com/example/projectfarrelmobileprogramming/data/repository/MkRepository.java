package com.example.projectfarrelmobileprogramming.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MkRepository {

    private ApiService apiService;

    public MkRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<MataKuliah>> getMkList(int perPage, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        MutableLiveData<List<MataKuliah>> mkData = new MutableLiveData<>();
        loadingLiveData.setValue(true);

        apiService.getMataKuliah(perPage).enqueue(new Callback<ApiResponse<MataKuliah>>() {
            @Override
            public void onResponse(Call<ApiResponse<MataKuliah>> call, Response<ApiResponse<MataKuliah>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    mkData.setValue(response.body().getData());
                } else {
                    errorLiveData.setValue("Gagal mengambil data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<MataKuliah>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Koneksi Bermasalah: " + t.getMessage());
            }
        });

        return mkData;
    }
}
