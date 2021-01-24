package com.example.foodhygienescores.ui.main;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;

import com.example.foodhygienescores.APIResultsModel;
import com.example.foodhygienescores.R;
import com.example.foodhygienescores.ResultLoader;
import com.example.foodhygienescores.UserLocation;
import com.example.foodhygienescores.ui.favourites.FavouriteActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.foodhygienescores.Utilities.addFavouriteToRoom;
import static com.example.foodhygienescores.Utilities.deleteFromRoomByFhrsid;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<APIResultsModel>> {

    private static final int REQUEST_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocation;
    private Location mLocation;
    private List<APIResultsModel> mResultsList;
    private ProgressBar mProgressBar;
    private FoodHygieneAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mIntroText;
    public static boolean isWideScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Check if wide screen
        if (findViewById(R.id.wide_layout) != null) {
            isWideScreen = true;
        }
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mProgressBar = findViewById(R.id.progress_bar);
        mIntroText = findViewById(R.id.textView);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new FoodHygieneAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (LoaderManager.getInstance(this).getLoader(0) != null) {
            LoaderManager.getInstance(this).initLoader(0, null, this);
        }

        FloatingActionButton mFabLocation = findViewById(R.id.fab_location);
        mFabLocation.setOnClickListener(view -> getLocation());

        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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
                        APIResultsModel apiResultsModel = mAdapter.getAPIResultsModel(position);
                        int FHRSID = apiResultsModel.getFHRSID();
                        ViewModelStoreOwner viewModelStoreOwner = MainActivity.this;
                        addFavouriteToRoom(apiResultsModel, viewModelStoreOwner);
                        mResultsList.remove(position);
                        mAdapter.setFoodHygieneAdapter(mResultsList);
                        View view = findViewById(R.id.main_activity);
                        Snackbar.make(view, R.string.added_favourites, Snackbar.LENGTH_SHORT)
                                .setAction(R.string.undo, v -> {
                                    deleteFromRoomByFhrsid(viewModelStoreOwner, FHRSID);
                                    mResultsList.add(position, apiResultsModel);
                                    mAdapter.setFoodHygieneAdapter(mResultsList);
                                })
                                .show();
                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                            @NonNull RecyclerView.ViewHolder viewHolder,
                                            float dX, float dY, int actionState,
                                            boolean isCurrentlyActive) {
                        // https://github.com/xabaras/RecyclerViewSwipeDecorator
                        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY,
                                actionState, isCurrentlyActive)
                                .addSwipeRightBackgroundColor(ContextCompat.getColor
                                        (MainActivity.this, R.color.green_500))
                                .addSwipeRightActionIcon(R.drawable.ic_favorite)
                                .create()
                                .decorate();
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                                isCurrentlyActive);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int itemId = item.getItemId();
        if (itemId == R.id.search) {
            SearchManager searchManager = (SearchManager)
                    getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Bundle queryBundle = new Bundle();
                    queryBundle.putString("query", query);
                    startLoader(queryBundle);
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(searchView.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            return true;
        } else if (itemId == R.id.favourites) {
            Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Custom method
    public void startLoader(Bundle bundle) {
        LoaderManager.getInstance(this).restartLoader(0, bundle, MainActivity.this);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    // Callback methods to the ResultsLoader
    @NonNull
    @Override
    public Loader<List<APIResultsModel>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ResultLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<APIResultsModel>> loader,
                               List<APIResultsModel> apiResultsModels) {
        mResultsList = apiResultsModels;
        mAdapter.setFoodHygieneAdapter(apiResultsModels);
        mProgressBar.setVisibility(View.GONE);
        if (mResultsList.size() < 1) {
            mIntroText.setText(R.string.no_results_message);
            mIntroText.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mIntroText.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<APIResultsModel>> loader) {
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions
                    (this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {

            mFusedLocation.getLastLocation().addOnSuccessListener
                    (location -> {
                        if (location != null) {
                            mLocation = location;
                            UserLocation userLocation = new UserLocation();
                            userLocation.setUserLongitude(String.valueOf(mLocation.getLongitude()));
                            userLocation.setUserLatitude(String.valueOf(mLocation.getLatitude()));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("location", userLocation);
                            startLoader(bundle);
                        } else {
                            Toast.makeText
                                    (MainActivity.this,
                                            getString(R.string.location_unavailable),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText
                        (this, getString(R.string.location_disabled),
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}