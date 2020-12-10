package com.example.foodhygienescores.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodhygienescores.APIResultsModel;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    LiveData<List<Favourite>> getAllFavourites();

    @Insert
    void insert(Favourite favourite);

    @Query("DELETE FROM favourites")
    void deleteAllFavourites();
}
