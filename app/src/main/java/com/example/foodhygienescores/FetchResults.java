package com.example.foodhygienescores;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FetchResults extends AsyncTask<String, Void, ArrayList<APIResultsModel>> {
    // Use WeakReference to prevent memory leaks for GC collection
    private WeakReference<ProgressBar> mProgressBar;
    private WeakReference<TextView> mBusinessName, mAddress1, mAddress2, mAddress3, mRatingValue;

    FetchResults(ProgressBar progressBar, TextView businessName, TextView address1,
                 TextView address2, TextView address3, TextView ratingValue) {

        this.mProgressBar = new WeakReference<>(progressBar);
        this.mBusinessName = new WeakReference<>(businessName);
        this.mAddress1 = new WeakReference<>(address1);
        this.mAddress2 = new WeakReference<>(address2);
        this.mAddress3 = new WeakReference<>(address3);
        this.mRatingValue = new WeakReference<>(ratingValue);

    }

    @Override
    protected ArrayList<APIResultsModel> doInBackground(String... strings) {
        return APIUtils.getFoodHygieneData(strings[0]);
    }

    // Executes on the UI Thread
    @Override
    protected void onPostExecute(ArrayList s) {
        super.onPostExecute(s);
        try {
            if (s.size() != 0) {
                String resultSize = String.valueOf(s.size());
                Log.d("RES", resultSize);
                //mIntroText.get().setText("Results retrieved: " + resultSize);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
