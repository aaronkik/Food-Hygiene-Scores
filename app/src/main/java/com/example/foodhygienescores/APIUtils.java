package com.example.foodhygienescores;

import android.net.Uri;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class APIUtils {
    // Base URL for API.
    private static final String API_URL = "https://api.ratings.food.gov.uk/Establishments?";
    // Headers for API Version
    public static final String API_VERSION = "2";
    // Query parameters for the search string.
    private static final String QUERY_PARAM_ADDRESS = "address";
    private static final String QUERY_PARAM_LONGITUDE = "longitude";
    private static final String QUERY_PARAM_LATITUDE = "latitude";
    private static final String QUERY_PARAM_DISTANCE = "maxDistanceLimit";
    private static final String QUERY_PARAM_SORT = "sortOptionKey";
    private static final String QUERY_PARAM_PAGE_SIZE = "pageSize";
    private static final String SORT_BY_DESC = "descending";
    // Parameter that limits search results based on address.
    private static final String MAX_DISTANCE_MILES = "1";
    // Parameter limits pageSize to 500 results
    private static final String MAX_PAGE_SIZE = "500";

    static List<APIResultsModel> getFoodHygieneData(Bundle bundle) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Uri builtURI;
        List<APIResultsModel> resultsList = new ArrayList<>();
        String query;
        String userLongitude;
        String userLatitude;

        if (bundle.get("query") != null) {
            query = bundle.getString("query");
            builtURI = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM_ADDRESS, query)
                    .appendQueryParameter(QUERY_PARAM_PAGE_SIZE, MAX_PAGE_SIZE)
                    .build();
        } else {
            UserLocation userLocation = (UserLocation) bundle.getSerializable("location");
            userLongitude = userLocation.getUserLongitude();
            userLatitude = userLocation.getUserLatitude();
            builtURI = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM_LONGITUDE, userLongitude)
                    .appendQueryParameter(QUERY_PARAM_LATITUDE, userLatitude)
                    .appendQueryParameter(QUERY_PARAM_DISTANCE, MAX_DISTANCE_MILES)
                    .appendQueryParameter(QUERY_PARAM_SORT, SORT_BY_DESC)
                    .appendQueryParameter(QUERY_PARAM_PAGE_SIZE, MAX_PAGE_SIZE)
                    .build();
        }

        try {
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("x-api-version", API_VERSION);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject resultObject = new JSONObject(line);
                JSONArray jsonArray = resultObject.getJSONArray("establishments");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    // Skip empty objects if present
                    if (jsonObject != null) {
                        APIResultsModel resultsModel = new APIResultsModel();
                        resultsModel.setFHRSID(jsonObject.getInt("FHRSID"));
                        resultsModel.setBusinessName(jsonObject.getString("BusinessName"));
                        resultsModel.setAddressLine1(jsonObject.getString("AddressLine1"));
                        resultsModel.setAddressLine2(jsonObject.getString("AddressLine2"));
                        resultsModel.setAddressLine3(jsonObject.getString("AddressLine3"));
                        resultsModel.setAddressLine4(jsonObject.getString("AddressLine4"));
                        resultsModel.setPostCode(jsonObject.getString("PostCode"));
                        resultsModel.setRatingValue(jsonObject.getString("RatingValue"));
                        resultsModel.setAuthorityName(jsonObject.getString
                                ("LocalAuthorityName"));
                        resultsModel.setAuthorityWebsite(jsonObject.getString
                                ("LocalAuthorityWebSite"));
                        resultsModel.setAuthorityEmail(jsonObject.getString
                                ("LocalAuthorityEmailAddress"));
                        resultsModel.setScoreHygiene(jsonObject.getJSONObject
                                ("scores").optInt("Hygiene", -1));
                        resultsModel.setScoreStructural(jsonObject.getJSONObject
                                ("scores").optInt("Structural", -1));
                        resultsModel.setScoreConInMan(jsonObject.getJSONObject
                                ("scores").optInt("ConfidenceInManagement", -1));
                        resultsModel.setLongitude(jsonObject.getJSONObject
                                ("geocode").getString("longitude"));
                        resultsModel.setLatitude(jsonObject.getJSONObject
                                ("geocode").getString("latitude"));
                        resultsModel.setDistance(jsonObject.optDouble
                                ("Distance", Double.NaN));
                        resultsList.add(resultsModel);
                    }
                }
            }
        } catch (IOException | JSONException e) {
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
        // Sort by Distance if location is present
        if (bundle.get("location") != null) {
            resultsList.sort(Comparator.comparing(APIResultsModel::getDistance));
        }
        return resultsList;
    }
}
