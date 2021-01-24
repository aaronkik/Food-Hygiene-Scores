package com.example.foodhygienescores.controller;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.foodhygienescores.db.Favourite;
import com.example.foodhygienescores.db.FavouriteDao;
import com.example.foodhygienescores.db.FavouriteRoomDatabase;

import java.util.List;

public class FavouritesRepository {
    private final FavouriteDao mFavouriteDao;
    private final LiveData<List<Favourite>> mResultsList;

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

    public void insertFavourites(List<Favourite> favourites) {
        new insertFavouritesAsyncTask(mFavouriteDao).execute(favourites);
    }

    public void delete(Favourite favourite) {
        new deleteAsyncTask(mFavouriteDao).execute(favourite);
    }

    public void deleteByFhrsid(int fhrsid) {
        new deleteFhrsidAsyncTask(mFavouriteDao, fhrsid).execute();
    }

    public void deleteAll() {
        new deleteAllAsyncTask(mFavouriteDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Favourite, Void, Void> {
        private final FavouriteDao mFavouriteDao;

        insertAsyncTask(FavouriteDao favouriteDao) {
            mFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(@NonNull final Favourite... favourites) {
            mFavouriteDao.insert(favourites[0]);
            return null;
        }
    }

    private static class insertFavouritesAsyncTask extends AsyncTask<List<Favourite>, Void, Void> {
        private final FavouriteDao mFavouriteDao;

        insertFavouritesAsyncTask(FavouriteDao favouriteDao) {
            mFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(@NonNull final List<Favourite>... favourites) {
            mFavouriteDao.insertFavourites(favourites[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Favourite, Void, Void> {
        private final FavouriteDao mFavouriteDao;

        deleteAsyncTask(FavouriteDao favouriteDao) {
            mFavouriteDao = favouriteDao;
        }

        @Override
        protected Void doInBackground(@NonNull Favourite... favourites) {
            mFavouriteDao.deleteFavourite(favourites[0]);
            return null;
        }
    }

    private static class deleteFhrsidAsyncTask extends AsyncTask<Void, Void, Void> {
        private final FavouriteDao mFavouriteDao;
        private final int mFhrsid;

        deleteFhrsidAsyncTask(FavouriteDao favouriteDao, int fhrsid) {
            mFavouriteDao = favouriteDao;
            mFhrsid = fhrsid;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mFavouriteDao.deleteByFhrsid(mFhrsid);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final FavouriteDao mFavouriteDao;

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
