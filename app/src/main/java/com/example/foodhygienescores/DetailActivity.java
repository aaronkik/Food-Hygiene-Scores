package com.example.foodhygienescores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

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