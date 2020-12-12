package com.example.foodhygienescores.controller;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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

    public void delete(Favourite favourite) {
        new deleteAsyncTask(mFavouriteDao).execute(favourite);
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mFavouriteDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Favourite, Void, Void> {

        private FavouriteDao mFavouriteDao;

        insertAsyncTask(FavouriteDao favouriteDao) {
            mFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(final Favourite... favourites) {
            mFavouriteDao.insert(favourites[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Favourite, Void, Void> {
        private FavouriteDao mFavouriteDao;

        deleteAsyncTask(FavouriteDao favouriteDao) {
            mFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(Favourite... favourites) {
            mFavouriteDao.deleteFavourite(favourites[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavouriteDao mFavouriteDao;

        deleteAllAsyncTask(FavouriteDao favouriteDao) {
            mFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mFavouriteDao.deleteAllFavourites();
            return null;
        }
    }

}
