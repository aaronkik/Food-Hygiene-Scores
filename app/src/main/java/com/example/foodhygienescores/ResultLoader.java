package com.example.foodhygienescores;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class ResultLoader extends AsyncTaskLoader<List<APIResultsModel>> {
    private final Bundle bundle;

    public ResultLoader(@NonNull Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Nullable
    @Override
    public List<APIResultsModel> loadInBackground() {
        return APIUtils.getFoodHygieneData(bundle);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // Executes loadInBackground
        forceLoad();
    }

}
