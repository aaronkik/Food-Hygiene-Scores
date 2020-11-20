package com.example.foodhygienescores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private String mBusinessName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        APIResultsModel mResult = (APIResultsModel)
                intent.getSerializableExtra(FoodHygieneAdapter.FoodHygieneHolder.PASS_DATA);
        mBusinessName = mResult.getBusinessName();
        setTitle(mBusinessName);
        if (savedInstanceState == null) {
            DetailFragment detailFragment = DetailFragment.newInstance(mResult);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();
        }
    }
}