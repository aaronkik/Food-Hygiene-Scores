package com.example.foodhygienescores.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    LiveData<List<Favourite>> getAllFavourites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favourite favourite);

    @Query("DELETE FROM favourites")
    void deleteAllFavourites();
}
