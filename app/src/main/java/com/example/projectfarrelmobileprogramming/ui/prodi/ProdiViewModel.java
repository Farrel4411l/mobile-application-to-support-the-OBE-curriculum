package com.example.projectfarrelmobileprogramming.ui.prodi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectfarrelmobileprogramming.data.model.Prodi;
import com.example.projectfarrelmobileprogramming.data.repository.ProdiRepository;

import java.util.List;

public class ProdiViewModel extends ViewModel {

    private ProdiRepository repository;
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public ProdiViewModel() {
        repository = new ProdiRepository();
    }

    public LiveData<List<Prodi>> getProdiList() {
        return repository.getProdiList(15, errorLiveData, loadingLiveData); // Mengambil 15 data
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return loadingLiveData;
    }
}
