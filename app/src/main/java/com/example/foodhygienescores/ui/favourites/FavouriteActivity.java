package com.example.foodhygienescores.ui.favourites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhygienescores.MainActivity;
import com.example.foodhygienescores.R;
import com.example.foodhygienescores.viewmodel.FavouritesViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import static com.example.foodhygienescores.R.string.favourites_deleted;

public class FavouriteActivity extends AppCompatActivity {

    protected boolean isWideScreen = MainActivity.isWideScreen;
    private TextView mInitialText, mWideScreenFragmentText;
    private FavouriteAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ExtendedFloatingActionButton mDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mInitialText = findViewById(R.id.favourite_initial_text);
        mDeleteAll = findViewById(R.id.fab_delete_all);
        mRecyclerView = findViewById(R.id.favourites_recyclerview);
        mAdapter = new FavouriteAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (isWideScreen) {
            mWideScreenFragmentText = findViewById(R.id.fragmentText_favourite);
        }

        FavouritesViewModel mFavouritesViewModel = new ViewModelProvider(this)
                .get(FavouritesViewModel.class);
        mFavouritesViewModel.getAllFavourites().observe(this, favourites -> {
            mAdapter.setFavouritesList(favourites);
            if (favourites.size() > 0) {
                mInitialText.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mDeleteAll.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.GONE);
                mInitialText.setVisibility(View.VISIBLE);
                mDeleteAll.setVisibility(View.INVISIBLE);
                if (isWideScreen) {
                    mWideScreenFragmentText.setVisibility(View.VISIBLE);
                }
            }
        });

        mDeleteAll.setOnClickListener(view -> {
            FavouritesViewModel mFavouritesViewModel1 = new ViewModelProvider
                    (FavouriteActivity.this).get(FavouritesViewModel.class);
            mFavouritesViewModel1.deleteAll();
            Toast.makeText(FavouriteActivity.this,
                    favourites_deleted, Toast.LENGTH_SHORT).show();
        });
    }
}