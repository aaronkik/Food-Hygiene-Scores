package com.example.foodhygienescores.ui.favourites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodhygienescores.R;
import com.example.foodhygienescores.db.Favourite;

import java.util.List;

public class FavouriteDetailActivity extends AppCompatActivity {

    private static final String mOutStateIndex = "OUT_STATE_INDEX";
    private List<Favourite> mFavouriteList;
    private Favourite mFavourite;
    private int mFavouriteListSize;
    private int mFavouriteIndex;
    private ViewPager2 mViewPager2;
    private FragmentStateAdapter fragmentStateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_detail);
        String dataKey = FavouriteAdapter.FavouriteHolder.PASS_DATA;
        String indexKey = FavouriteAdapter.FavouriteHolder.PASS_INDEX;
        Intent intent = getIntent();

        mFavouriteList = (List<Favourite>) intent.getSerializableExtra(dataKey);
        mFavouriteIndex = intent.getIntExtra(indexKey, 0);
        mFavouriteListSize = mFavouriteList.size();

        mViewPager2 = findViewById(R.id.pager_favourite);

        fragmentStateAdapter = new FavouriteDetailPagerAdapter(this);
        mViewPager2.setAdapter(fragmentStateAdapter);
        if (savedInstanceState != null) {
            int savedIndex = savedInstanceState.getInt(mOutStateIndex);
            mViewPager2.setCurrentItem(savedIndex);
        } else {
            mViewPager2.setCurrentItem(mFavouriteIndex);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(mOutStateIndex, mViewPager2.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class FavouriteDetailPagerAdapter extends FragmentStateAdapter {
        public FavouriteDetailPagerAdapter(FavouriteDetailActivity favouriteDetailActivity) {
            super(favouriteDetailActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            mFavourite = mFavouriteList.get(position);
            return FavouriteDetailFragment.newInstance(mFavourite);
        }

        @Override
        public int getItemCount() {
            return mFavouriteListSize;
        }
    }
}