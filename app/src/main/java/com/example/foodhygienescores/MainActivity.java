package com.example.foodhygienescores;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;

import com.example.foodhygienescores.ui.favourites.FavouriteActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        mResultsList = new ArrayList<>();
        mProgressBar = findViewById(R.id.progress_bar);
        mIntroText = findViewById(R.id.textView);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new FoodHygieneAdapter(this, mResultsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        FloatingActionButton mFabLocation = findViewById(R.id.fab_location);
        mFabLocation.setOnClickListener(view -> getLocation());
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
        switch (item.getItemId()) {
            case R.id.search:
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
            case R.id.favourites:
                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Toast.makeText(this, R.string.action_settings, Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Custom method
    public void startLoader(Bundle bundle) {
        getSupportLoaderManager().restartLoader(0, bundle, MainActivity.this);
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
                               List<APIResultsModel> data) {
        // Clear previous list and load list with new data
        mResultsList.clear();
        mResultsList.addAll(data);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
        mIntroText.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
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
                    (new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
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