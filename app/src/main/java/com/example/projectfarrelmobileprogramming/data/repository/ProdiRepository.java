package com.example.projectfarrelmobileprogramming.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectfarrelmobileprogramming.data.model.ApiResponse;
import com.example.projectfarrelmobileprogramming.data.model.Prodi;
import com.example.projectfarrelmobileprogramming.data.network.ApiClient;
import com.example.projectfarrelmobileprogramming.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class ProdiRepository {

    private ApiService apiService;

    public ProdiRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<Prodi>> getProdiList(int perPage, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        MutableLiveData<List<Prodi>> prodiData = new MutableLiveData<>();
        loadingLiveData.setValue(true);

        apiService.getProdi(perPage).enqueue(new Callback<ApiResponse<Prodi>>() {
            @Override
            public void onResponse(Call<ApiResponse<Prodi>> call, Response<ApiResponse<Prodi>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    prodiData.setValue(response.body().getData());
                } else {
                    String errorBody = "";
                    try { if(response.errorBody() != null) errorBody = response.errorBody().string(); } catch(Exception e){}
                    errorLiveData.setValue("Gagal mengambil data: Code " + response.code() + ", Message: " + response.message() + ", Body: " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Prodi>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Koneksi Bermasalah: " + t.getMessage());
            }
        });

        return prodiData;
    }
}
