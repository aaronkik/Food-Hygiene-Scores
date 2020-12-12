package com.example.foodhygienescores.ui.favourites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhygienescores.R;
import com.example.foodhygienescores.viewmodel.FavouritesViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class FavouriteActivity extends AppCompatActivity {

    private TextView mInitialText;
    private FavouriteAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ExtendedFloatingActionButton mDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mInitialText = findViewById(R.id.favourite_initial_text);
        mRecyclerView = findViewById(R.id.favourites_recyclerview);
        mAdapter = new FavouriteAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FavouritesViewModel mFavouritesViewModel = new ViewModelProvider(this)
                .get(FavouritesViewModel.class);
        mFavouritesViewModel.getAllFavourites().observe(this, favourites -> {
            mAdapter.setFavouritesList(favourites);
            if (favourites.size() > 0) {
                mInitialText.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.GONE);
                mInitialText.setVisibility(View.VISIBLE);
            }
        });
        mDeleteAll = findViewById(R.id.fab_delete_all);
        mDeleteAll.setOnClickListener(view -> {
            FavouritesViewModel mFavouritesViewModel1 = new ViewModelProvider
                    (FavouriteActivity.this).get(FavouritesViewModel.class);
            mFavouritesViewModel1.deleteAll();
            Toast.makeText(FavouriteActivity.this,
                    "Favourites Deleted...", Toast.LENGTH_SHORT).show();
        });
    }
}