package com.example.foodhygienescores.ui.favourites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodhygienescores.db.Favourite;
import com.example.foodhygienescores.ui.main.MainActivity;
import com.example.foodhygienescores.R;
import com.example.foodhygienescores.viewmodel.FavouritesViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.foodhygienescores.R.string.favourites_deleted;


public class FavouriteActivity extends AppCompatActivity {

    protected boolean isWideScreen = MainActivity.isWideScreen;
    private TextView mInitialText, mWideScreenFragmentText;
    private List<Favourite> mFavouriteList;
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
            int favouriteSize = favourites.size();
            mAdapter.setFavouritesList(favourites);
            setTitle("Favourites: " + favouriteSize);

            ItemTouchHelper.SimpleCallback simpleCallback =
                    new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView,
                                              @NonNull RecyclerView.ViewHolder viewHolder,
                                              @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                             int direction) {
                            int position = viewHolder.getAdapterPosition();
                            Favourite favourite = favourites.get(position);
                            mFavouritesViewModel.delete(favourite);
                            View view = findViewById(R.id.favourite_activity);
                            Snackbar.make(view, R.string.favourite_removed, Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.undo, v ->
                                            mFavouritesViewModel.insert(favourite))
                                    .show();
                        }

                        @Override
                        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY,
                                    actionState, isCurrentlyActive)
                                    .addSwipeLeftBackgroundColor(ContextCompat.getColor
                                            (FavouriteActivity.this, R.color.red_200))
                                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                                    .create()
                                    .decorate();
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        }
                    };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);

            if (favouriteSize > 0) {
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
            mFavouriteList = mFavouritesViewModel.getAllFavourites().getValue();
            FavouritesViewModel mFavouritesViewModel1 = new ViewModelProvider
                    (FavouriteActivity.this).get(FavouritesViewModel.class);
            mFavouritesViewModel1.deleteAll();
            Snackbar.make(view, favourites_deleted, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.undo, view1 -> mFavouritesViewModel.insertFavourites(mFavouriteList))
                    .show();
        });
    }
}