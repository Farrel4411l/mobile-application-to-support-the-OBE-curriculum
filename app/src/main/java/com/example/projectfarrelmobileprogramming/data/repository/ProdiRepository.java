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

/**
 * Kelas ProdiRepository menerapkan Repository Pattern khusus untuk entitas Program Studi.
 * Tujuan kelas ini adalah untuk memisahkan logika pengambilan data (data fetching)
 * dari komponen UI (Activity/Fragment) dan ViewModel. Repository ini mengambil data
 * menggunakan Retrofit melalui ApiService.
 */
public class ProdiRepository {

    // Objek ApiService yang menyimpan rute endpoint API
    private ApiService apiService;

    /**
     * Konstruktor ProdiRepository.
     * Menginisialisasi ApiService yang akan digunakan untuk memanggil endpoint Prodi.
     */
    public ProdiRepository() {
        // Membuat instance ApiService melalui factory method yang disediakan oleh Retrofit
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    /**
     * Metode untuk melakukan fetch daftar Program Studi dari backend.
     *
     * @param perPage Jumlah limit data yang diminta dalam satu permintaan.
     * @param errorLiveData LiveData eksternal untuk melempar pesan error ke UI jika terjadi masalah.
     * @param loadingLiveData LiveData eksternal untuk mengatur progress bar atau loading indicator di UI.
     * @return LiveData berisi List Prodi. UI dapat meng-observe LiveData ini.
     */
    public LiveData<List<Prodi>> getProdiList(int perPage, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        // Inisialisasi wadah penampung data (MutableLiveData) untuk menyimpan daftar Prodi
        MutableLiveData<List<Prodi>> prodiData = new MutableLiveData<>();
        
        // Memasang state loading menjadi true (UI biasanya akan menampilkan progress spinner)
        loadingLiveData.setValue(true);

        // Memanggil endpoint 'prodi' secara asinkron menggunakan .enqueue()
        apiService.getProdi(perPage).enqueue(new Callback<ApiResponse<Prodi>>() {
            
            // Dipanggil secara otomatis ketika server merespons, terlepas apakah status kodenya sukses (2xx) atau gagal (4xx/5xx)
            @Override
            public void onResponse(Call<ApiResponse<Prodi>> call, Response<ApiResponse<Prodi>> response) {
                // Sembunyikan indikator loading
                loadingLiveData.setValue(false);
                
                // Jika statusnya 200-299 dan respons bodinya tidak null, maka data dianggap valid
                if (response.isSuccessful() && response.body() != null) {
                    // Update prodiData dengan data (List) yang didapat dari API
                    prodiData.setValue(response.body().getData());
                } else {
                    // Blok ini dijalankan jika server mengembalikan error (misal: 400 Bad Request, 404 Not Found, dsb)
                    String errorBody = "";
                    try {
                        // Mencoba mengekstrak string dari errorBody jika tidak kosong
                        if (response.errorBody() != null) errorBody = response.errorBody().string();
                    } catch (Exception e) {
                        // Abaikan jika terjadi masalah saat membaca errorBody
                    }
                    // Mengirimkan error lengkap berupa kode HTTP, pesan, dan body error untuk memudahkan debugging
                    errorLiveData.setValue("Gagal mengambil data: Code " + response.code() + ", Message: " + response.message() + ", Body: " + errorBody);
                }
            }

            // Dipanggil jika pemanggilan ke jaringan sama sekali gagal (seperti tidak ada koneksi internet, server mati, atau timeout)
            @Override
            public void onFailure(Call<ApiResponse<Prodi>> call, Throwable t) {
                // Sembunyikan indikator loading
                loadingLiveData.setValue(false);
                // Mengirimkan pesan failure agar bisa ditampilkan kepada user (contoh: "Koneksi Bermasalah")
                errorLiveData.setValue("Koneksi Bermasalah: " + t.getMessage());
            }
        });

        // Segera mengembalikan LiveData meskipun datanya belum tiba,
        // UI dapat menempelkan observer pada objek yang sama untuk menerima data nanti.
        return prodiData;
    }
}
