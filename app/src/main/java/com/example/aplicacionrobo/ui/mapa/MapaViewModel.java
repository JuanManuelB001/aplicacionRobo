package com.example.aplicacionrobo.ui.mapa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is <Mapa> fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}