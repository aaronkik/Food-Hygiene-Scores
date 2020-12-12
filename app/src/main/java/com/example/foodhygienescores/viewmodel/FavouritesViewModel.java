package com.example.foodhygienescores.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodhygienescores.controller.FavouritesRepository;
import com.example.foodhygienescores.db.Favourite;

import java.util.List;

public class FavouritesViewModel extends AndroidViewModel {
    private final FavouritesRepository mRepository;
    private final LiveData<List<Favourite>> mResultsList;

    public FavouritesViewModel(Application application) {
        super(application);
        mRepository = new FavouritesRepository(application);
        mResultsList = mRepository.getAllFavourites();
    }

    public LiveData<List<Favourite>> getAllFavourites() {
        return mResultsList;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(Favourite favourite) {
        mRepository.insert(favourite);
    }
}
