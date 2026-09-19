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

/**
 * Kelas MkRepository berfungsi sebagai lapisan perantara (Repository Pattern) antara
 * sumber data (dalam hal ini API/Network) dan komponen UI (seperti ViewModel).
 * Repository menyembunyikan detail dari mana data berasal dan bagaimana data diambil,
 * sehingga UI hanya perlu memanggil metode repository.
 */
public class MkRepository {

    // Menyimpan instance dari ApiService untuk melakukan pemanggilan jaringan
    private ApiService apiService;

    /**
     * Konstruktor MkRepository.
     * Menginisialisasi apiService menggunakan ApiClient yang merupakan client Retrofit Singleton.
     */
    public MkRepository() {
        // Membuat implementasi dari interface ApiService dengan Retrofit
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    /**
     * Metode untuk mengambil daftar mata kuliah dari API dan mengembalikannya
     * dalam bentuk LiveData. LiveData merupakan komponen lifecycle-aware yang akan
     * memberi tahu observer di UI ketika datanya berubah.
     *
     * @param perPage Jumlah data per halaman.
     * @param errorLiveData LiveData untuk mengirimkan pesan error ke UI.
     * @param loadingLiveData LiveData untuk mengontrol state pemuatan (loading) di UI.
     * @return LiveData berisi List dari objek MataKuliah.
     */
    public LiveData<List<MataKuliah>> getMkList(int perPage, MutableLiveData<String> errorLiveData, MutableLiveData<Boolean> loadingLiveData) {
        // Membuat wadah penampung data reaktif untuk hasil dari pemanggilan API
        MutableLiveData<List<MataKuliah>> mkData = new MutableLiveData<>();
        
        // Memberi tahu UI bahwa proses pengambilan data (loading) sedang dimulai
        loadingLiveData.setValue(true);

        // Memulai permintaan HTTP secara asinkron agar tidak memblokir thread utama (UI thread)
        apiService.getMataKuliah(perPage).enqueue(new Callback<ApiResponse<MataKuliah>>() {
            
            // Callback ini dipanggil saat ada balasan dari server (sukses/gagal secara HTTP status)
            @Override
            public void onResponse(Call<ApiResponse<MataKuliah>> call, Response<ApiResponse<MataKuliah>> response) {
                // Mematikan state loading karena respons telah diterima
                loadingLiveData.setValue(false);
                
                // Memeriksa apakah status HTTP sukses (200-299) dan isi respons tidak kosong
                if (response.isSuccessful() && response.body() != null) {
                    // Memperbarui nilai mkData dengan data yang diterima dari server
                    mkData.setValue(response.body().getData());
                } else {
                    // Jika gagal, kirimkan pesan error ke UI lewat errorLiveData
                    errorLiveData.setValue("Gagal mengambil data: " + response.message());
                }
            }

            // Callback ini dipanggil jika permintaan gagal sama sekali (misal: tidak ada internet atau timeout)
            @Override
            public void onFailure(Call<ApiResponse<MataKuliah>> call, Throwable t) {
                // Mematikan state loading
                loadingLiveData.setValue(false);
                // Mengirimkan pesan kesalahan dari exception ke UI
                errorLiveData.setValue("Koneksi Bermasalah: " + t.getMessage());
            }
        });

        // Mengembalikan instance LiveData. Observer di UI dapat memantau objek ini
        // dan bereaksi secara otomatis ketika data telah diisi (melalui setValue).
        return mkData;
    }
}
