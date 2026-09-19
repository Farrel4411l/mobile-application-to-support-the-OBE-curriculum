package com.example.projectfarrelmobileprogramming.ui.mk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;
import com.example.projectfarrelmobileprogramming.data.repository.MkRepository;

import java.util.List;

/**
 * Kelas MkViewModel.
 * Merupakan kelas ViewModel pada arsitektur MVVM (Model-View-ViewModel).
 * Bertugas mengelola state dan data terkait Mata Kuliah untuk dikonsumsi oleh MkActivity.
 * ViewModel dirancang agar datanya tetap hidup/aman (survive) meskipun terjadi perubahan 
 * orientasi layar (seperti layar berputar/rotation) pada Activity.
 */
public class MkViewModel extends ViewModel {

    // Instance dari kelas Repository, tempat yang melakukan penarikan data mentah dari jaringan (API) / database
    private MkRepository repository;
    
    // LiveData bersifat mutable (dapat diubah-ubah nilainya) untuk menyampaikan status/pesan error
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    
    // LiveData boolean yang menunjukkan apakah aplikasi sedang berada dalam proses "memuat data"
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    /**
     * Konstruktor default ViewModel.
     * Menginisialisasi objek dari MkRepository agar siap digunakan.
     */
    public MkViewModel() {
        // Membuat instansiasi baru MkRepository
        repository = new MkRepository();
    }

    /**
     * Mengambil daftar list Mata Kuliah dalam bentuk LiveData yang bisa diobservasi (diamati) oleh UI.
     * 
     * @return Objek LiveData yang membungkus kumpulan List MataKuliah.
     */
    public LiveData<List<MataKuliah>> getMkList() {
        // Meminta data dari repository dengan parameter jumlah data (limit 30) 
        // serta mem-passing live data penampung error dan status loading untuk di-update dari dalam repository
        return repository.getMkList(30, errorLiveData, loadingLiveData);
    }

    /**
     * Mendapatkan ekspos LiveData yang memuat string pesan error (jika ada).
     * @return LiveData dengan nilai String (pesan error)
     */
    public LiveData<String> getError() {
        // Mengembalikan LiveData Error
        return errorLiveData;
    }

    /**
     * Mendapatkan ekspos LiveData Boolean untuk mendeteksi apakah proses muat (loading) data sedang berjalan.
     * @return LiveData dengan nilai Boolean. Jika true, maka proses sedang loading.
     */
    public LiveData<Boolean> getLoading() {
        // Mengembalikan LiveData status loading
        return loadingLiveData;
    }
}
