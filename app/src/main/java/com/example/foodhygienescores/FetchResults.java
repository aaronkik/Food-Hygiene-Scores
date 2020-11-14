package com.example.foodhygienescores;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchResults extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mIntroText;

    FetchResults(TextView introText) {
        this.mIntroText = new WeakReference<>(introText);

    }

    @Override
    protected String doInBackground(String... strings) {
        return APIUtils.getFoodHygieneData(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("establishments");

            int i = 0;
            String name = null;
            String rating = null;

            while (i < jsonArray.length() &&
                    (rating == null && name == null)) {
                // Get the current item information.
                JSONObject establishment = jsonArray.getJSONObject(i);

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    name = establishment.getString("businessName");
                    rating = establishment.getString("RatingValue");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }

            if (name != null && rating != null) {
                mIntroText.get().setText(name);
            } else {
                mIntroText.get().setText("ERROR");
            }


        } catch (JSONException e) {
            e.printStackTrace();
            mIntroText.get().setText("ERROR");
        }
    }
}
