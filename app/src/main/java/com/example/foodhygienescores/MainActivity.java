package com.example.foodhygienescores;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<APIResultsModel>> {

    private ArrayList<APIResultsModel> mResultsList;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFab;
    private FoodHygieneAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mIntroText, mBusinessName, mAddress, mRatingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mResultsList = new ArrayList<>();
        mProgressBar = findViewById(R.id.progress_bar);
        mIntroText = findViewById(R.id.textView);
        mBusinessName = findViewById(R.id.business_name);
        mAddress = findViewById(R.id.address);
        mRatingValue = findViewById(R.id.rating_value);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new FoodHygieneAdapter(this, mResultsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.search:
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Bundle queryBundle = new Bundle();
                        queryBundle.putString("query", query);
                        getSupportLoaderManager().restartLoader(0, queryBundle, MainActivity.this);
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        if (inputManager != null) {
                            inputManager.hideSoftInputFromWindow(searchView.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        mProgressBar.setVisibility(View.VISIBLE);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //Required but not in use.
                        return false;
                    }
                });
        }

        return super.onOptionsItemSelected(item);

    }

    //Callback methods to the ResultsLoader
    @NonNull
    @Override
    public Loader<ArrayList<APIResultsModel>> onCreateLoader(int id, @Nullable Bundle args) {

        String query = "";
        if (args != null) {
            query = args.getString("query");
        }

        return new ResultLoader(this, query);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<APIResultsModel>> loader, ArrayList<APIResultsModel> data) {
        // Clear previous list and load list with new data
        mResultsList.clear();
        mResultsList.addAll(data);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
        mIntroText.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<APIResultsModel>> loader) {
    }
}