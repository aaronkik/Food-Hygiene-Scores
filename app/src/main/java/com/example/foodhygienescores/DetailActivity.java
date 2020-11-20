package com.example.foodhygienescores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private String mBusinessName;
    private String mLongitude;
    private String mLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        APIResultsModel mResult = (APIResultsModel)
                intent.getSerializableExtra(FoodHygieneAdapter.FoodHygieneHolder.PASS_DATA);
        mBusinessName = mResult.getBusinessName();
        mLongitude = mResult.getLongitude();
        mLatitude = mResult.getLatitude();
        setTitle(mBusinessName);
        if (savedInstanceState == null) {
            DetailFragment detailFragment = DetailFragment.newInstance(mResult);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();
        }
    }

    // https://developers.google.com/maps/documentation/urls/android-intents
    public void openMap(View view) {
        Uri mapQuery = Uri.parse("geo:" + mLatitude + "," + mLongitude + "?z=19");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapQuery);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, R.string.map_error, Toast.LENGTH_SHORT).show();
        }
    }
}