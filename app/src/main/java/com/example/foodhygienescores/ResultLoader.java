package com.example.foodhygienescores;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class ResultLoader extends AsyncTaskLoader<ArrayList<APIResultsModel>> {
    private Bundle bundle;

    public ResultLoader(@NonNull Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Nullable
    @Override
    public ArrayList<APIResultsModel> loadInBackground() {
        return APIUtils.getFoodHygieneData(bundle);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // Executes loadInBackground
        forceLoad();
    }

}
