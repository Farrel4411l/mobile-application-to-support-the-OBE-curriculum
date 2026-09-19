package com.example.projectfarrelmobileprogramming.ui.mk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectfarrelmobileprogramming.data.model.MataKuliah;
import com.example.projectfarrelmobileprogramming.data.repository.MkRepository;

import java.util.List;

public class MkViewModel extends ViewModel {

    private MkRepository repository;
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public MkViewModel() {
        repository = new MkRepository();
    }

    public LiveData<List<MataKuliah>> getMkList() {
        return repository.getMkList(30, errorLiveData, loadingLiveData);
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return loadingLiveData;
    }
}
