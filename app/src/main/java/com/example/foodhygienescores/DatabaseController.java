package com.example.foodhygienescores;

import android.content.Context;

public class DatabaseController {

    private DatabaseHelper databaseHelper;

    public DatabaseController(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

}
