package com.example.foodhygienescores;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIUtils {
    public static final String LOG_TAG = APIUtils.class.getSimpleName();
    // Base URL for API.
    private static final String API_URL = "https://api.ratings.food.gov.uk/Establishments?";
    // Headers for API Version
    public static final String API_VERSION = "2";
    // Query parameters for the search string.
    private static final String QUERY_PARAM_ADDRESS = "address";
    private static final String QUERY_PARAM_DISTANCE = "maxDistanceLimit";
    // Parameter that limits search results based on address.
    private static final String MAX_DISTANCE_MILES = "1";

    static String getFoodHygieneData(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        try {

            Uri builtURI = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM_ADDRESS, queryString)
                    .appendQueryParameter(QUERY_PARAM_DISTANCE, MAX_DISTANCE_MILES)
                    .build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("x-api-version", API_VERSION);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            if (builder.length() == 0) {
                return null;
            }

            bookJSONString = builder.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, bookJSONString);

        return bookJSONString;

    }
}
