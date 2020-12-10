package com.example.foodhygienescores.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodhygienescores.APIResultsModel;

import java.util.ArrayList;

@Dao
public interface FavouriteDao {

    @Insert
    void insert(Favourite favourite);

    @Query("DELETE FROM favourites")
    void deleteAllFavourites();

    @Query("SELECT * FROM favourites")
    LiveData<ArrayList<APIResultsModel>> getAllFavourites();

}
