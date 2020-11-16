package com.example.foodhygienescores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class ResultLoader extends AsyncTaskLoader<ArrayList<APIResultsModel>> {
    private String mQueryString;

    public ResultLoader(@NonNull Context context, String queryString) {
        super(context);
        this.mQueryString = queryString;
    }

    @Nullable
    @Override
    public ArrayList<APIResultsModel> loadInBackground() {
        return APIUtils.getFoodHygieneData(mQueryString);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // Executes loadInBackground
        forceLoad();
    }

}
