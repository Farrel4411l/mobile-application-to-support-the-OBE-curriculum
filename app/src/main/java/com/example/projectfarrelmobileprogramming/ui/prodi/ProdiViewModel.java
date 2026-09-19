package com.example.projectfarrelmobileprogramming.ui.prodi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectfarrelmobileprogramming.data.model.Prodi;
import com.example.projectfarrelmobileprogramming.data.repository.ProdiRepository;

import java.util.List;

/**
 * Kelas ProdiViewModel
 * <p>
 * Kelas ini mengimplementasikan konsep arsitektur MVVM (Model-View-ViewModel).
 * Bertujuan untuk memisahkan logika pengambilan data dengan UI.
 * ViewModel ini mengelola state UI seperti list prodi, loading, dan pesan error 
 * agar dapat diamati (observed) oleh Activity/Fragment.
 */
public class ProdiViewModel extends ViewModel {

    // Repository untuk mengatur pengambilan data prodi dari sumber data (API/Network)
    private ProdiRepository repository;
    
    // MutableLiveData untuk menyimpan status error, sehingga UI bisa bereaksi bila terjadi kesalahan
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    
    // MutableLiveData untuk status loading, berguna untuk memunculkan atau menyembunyikan ProgressBar di UI
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    /**
     * Konstruktor ProdiViewModel.
     * Akan dijalankan ketika objek ViewModel dibuat.
     */
    public ProdiViewModel() {
        // Inisialisasi ProdiRepository yang akan digunakan untuk request data
        repository = new ProdiRepository();
    }

    /**
     * Method untuk meminta daftar Prodi dari repository.
     * Mengembalikan objek LiveData yang nantinya diobservasi oleh View (Activity/Fragment).
     * 
     * @return LiveData berisi List dari objek Prodi
     */
    public LiveData<List<Prodi>> getProdiList() {
        // Memanggil getProdiList di repository, meminta batas jumlah (limit) 15 data.
        // Data loading dan error juga dipass agar repository dapat meng-update state tersebut.
        return repository.getProdiList(15, errorLiveData, loadingLiveData); // Mengambil 15 data
    }

    /**
     * Method ini digunakan oleh UI untuk memantau perubahan status error.
     * 
     * @return LiveData berupa pesan String error
     */
    public LiveData<String> getError() {
        return errorLiveData;
    }

    /**
     * Method ini digunakan oleh UI untuk memantau status proses pemuatan (loading).
     * 
     * @return LiveData boolean true (jika sedang loading) atau false (jika sudah selesai)
     */
    public LiveData<Boolean> getLoading() {
        return loadingLiveData;
    }
}
