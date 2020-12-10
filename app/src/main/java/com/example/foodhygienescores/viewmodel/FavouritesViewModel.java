package com.example.foodhygienescores.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodhygienescores.APIResultsModel;
import com.example.foodhygienescores.controller.FavouritesRepository;
import com.example.foodhygienescores.db.Favourite;

import java.util.ArrayList;

public class FavouritesViewModel extends AndroidViewModel {
    private FavouritesRepository mRepository;
    private LiveData<ArrayList<APIResultsModel>> mResultsList;

    public FavouritesViewModel(Application application) {
        super(application);
        mRepository = new FavouritesRepository(application);
        mResultsList = mRepository.getAllFavourites();
    }

    LiveData<ArrayList<APIResultsModel>> getAllFavourites() {
        return mResultsList;
    }

    public void insert(Favourite favourite) {
        mRepository.insert(favourite);
    }
}
