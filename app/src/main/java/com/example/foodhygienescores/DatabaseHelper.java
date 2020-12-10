package com.example.foodhygienescores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "food_hygiene_db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "favourites";
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    "(FHRSID INT PRIMARY KEY, " +
                    "business_name VARCHAR NOT NULL, " +
                    "address_line1 VARCHAR, " +
                    "address_line2 VARCHAR, " +
                    "address_line3 VARCHAR, " +
                    "address_line4 VARCHAR, " +
                    "postcode VARCHAR(8) NOT NULL, " +
                    "rating_value VARCHAR NOT NULL, " +
                    "local_authority_name VARCHAR NOT NULL, " +
                    "local_authority_website VARCHAR NOT NULL, " +
                    "local_authority_email VARCHAR NOT NULL, " +
                    "hygiene_score TINYINT NOT NULL, " +
                    "structural_score TINYINT NOT NULL, " +
                    "con_in_man_score TINYINT NOT NULL, " +
                    "long VARCHAR NOT NULL, " +
                    "lat VARCHAR NOT NULL);";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
