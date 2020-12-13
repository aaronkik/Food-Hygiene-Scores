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

    public void insert(Favourite favourite) {
        mRepository.insert(favourite);
    }

    public void insertFavourites(List<Favourite> favourites) {
        mRepository.insertFavourites(favourites);
    }

    public void delete(Favourite favourite) {
        mRepository.delete(favourite);
    }

    public void deleteByFhrsid(int fhrsid) {
        mRepository.deleteByFhrsid(fhrsid);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
