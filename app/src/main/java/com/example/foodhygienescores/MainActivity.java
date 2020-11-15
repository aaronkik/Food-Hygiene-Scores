package com.example.foodhygienescores;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ArrayList<APIResultsModel> resultsList;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFab;
    private FoodHygieneAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mIntroText, mBusinessName, mAddress1, mAddress2, mAddress3, mRatingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resultsList = new ArrayList<>();
        mProgressBar = findViewById(R.id.progress_bar);
        mIntroText = findViewById(R.id.textView);
        mBusinessName = findViewById(R.id.business_name);
        mAddress1 = findViewById(R.id.address_1);
        mAddress2 = findViewById(R.id.address_2);
        mAddress3 = findViewById(R.id.address_3);
        mRatingValue = findViewById(R.id.rating_value);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new FoodHygieneAdapter(this, resultsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                        mProgressBar.setVisibility(View.VISIBLE);
                        mIntroText.setVisibility(View.GONE);
                        FetchResults fetchResults = new FetchResults(mProgressBar, mBusinessName,
                                mAddress1, mAddress2, mAddress3, mRatingValue);
                        try {
                            resultsList.clear();
                            resultsList.addAll(fetchResults.execute(query).get());
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.INVISIBLE);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

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


}