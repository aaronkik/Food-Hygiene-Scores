package com.example.foodhygienescores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    private FoodHygieneAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<APIResultsModel> mResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new FoodHygieneAdapter(this, mResultsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}