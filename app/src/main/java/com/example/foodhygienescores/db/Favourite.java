package com.example.foodhygienescores.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites")
public class Favourite {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "fhrsid")
    private int mFHRSID;

    public Favourite(@NonNull int mFHRSID) {
        this.mFHRSID = mFHRSID;
    }

    public int getFHRSID() {
        return mFHRSID;
    }
}
