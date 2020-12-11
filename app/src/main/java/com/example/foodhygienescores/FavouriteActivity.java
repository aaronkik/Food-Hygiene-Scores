package com.example.foodhygienescores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.foodhygienescores.db.Favourite;
import com.example.foodhygienescores.viewmodel.FavouritesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private FoodHygieneAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FavouritesViewModel mFavouritesViewModel;
    private List<APIResultsModel> mResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mRecyclerView = findViewById(R.id.recyclerview1);
        mAdapter = new FoodHygieneAdapter(this, mResultsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFavouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);
        mFavouritesViewModel.getAllFavourites().observe(this, new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {


                mAdapter.notifyDataSetChanged();
            }
        });
    }
}