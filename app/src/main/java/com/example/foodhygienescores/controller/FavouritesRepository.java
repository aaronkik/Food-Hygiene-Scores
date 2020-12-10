package com.example.foodhygienescores.controller;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.foodhygienescores.APIResultsModel;
import com.example.foodhygienescores.db.Favourite;
import com.example.foodhygienescores.db.FavouriteDao;
import com.example.foodhygienescores.db.FavouriteRoomDatabase;

import java.util.List;

public class FavouritesRepository {
    private FavouriteDao mFavouriteDao;
    private LiveData<List<Favourite>> mResultsList;

    public FavouritesRepository(Application application) {
        FavouriteRoomDatabase db = FavouriteRoomDatabase.getDatabase(application);
        mFavouriteDao = db.favouriteDao();
        mResultsList = mFavouriteDao.getAllFavourites();
    }

    public LiveData<List<Favourite>> getAllFavourites() {
        return mResultsList;
    }

    public void insert(Favourite favourite) {
        new insertAsyncTask(mFavouriteDao).execute(favourite);
    }

    private static class insertAsyncTask extends AsyncTask<Favourite, Void, Void> {

        private FavouriteDao mAsyncTaskDao;

        insertAsyncTask(FavouriteDao favouriteDao) {
            mAsyncTaskDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(final Favourite... favourites) {
            mAsyncTaskDao.insert(favourites[0]);
            return null;
        }
    }

}
