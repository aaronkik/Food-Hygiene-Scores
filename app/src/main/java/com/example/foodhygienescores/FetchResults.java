package com.example.foodhygienescores;

import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FetchResults extends AsyncTask<String, Void, ArrayList> {
    //Use WeakReference to prevent memory leaks for GC collection
    private WeakReference<TextView> mIntroText;
    private WeakReference<TextView> mCardHeader;

    FetchResults(TextView introText, TextView mCardHeader) {
        this.mIntroText = new WeakReference<>(introText);
        this.mCardHeader = new WeakReference<>(mCardHeader);
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        return APIUtils.getFoodHygieneData(strings[0]);
    }

    //Executes on the UI Thread
    @Override
    protected void onPostExecute(ArrayList s) {
        try {

            if (s.size() != 0) {
                String resultSize = String.valueOf(s.size());
                mIntroText.get().setText("Results retrieved: " + resultSize);
                mCardHeader.get().setText("HEHEH");
            } else {
                mIntroText.get().setText("No Results");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mIntroText.get().setText("ERROR");
        }
        super.onPostExecute(s);
    }
}
