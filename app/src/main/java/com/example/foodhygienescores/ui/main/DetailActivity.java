package com.example.foodhygienescores.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodhygienescores.APIResultsModel;
import com.example.foodhygienescores.R;

import java.io.Serializable;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String mOutStateIndex = "OUT_STATE_INDEX";
    private ViewPager2 mViewPager2;
    private List<APIResultsModel> mResultList;
    private int mResultListSize;
    private static final String mTitleText = "Result: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String mDataKey = FoodHygieneAdapter.FoodHygieneHolder.PASS_DATA;
        String mIndexKey = FoodHygieneAdapter.FoodHygieneHolder.PASS_INDEX;
        mResultList = (List<APIResultsModel>) intent.getSerializableExtra(mDataKey);
        int mResultIndex = intent.getIntExtra(mIndexKey, 0);
        mResultListSize = mResultList.size();

        mViewPager2 = findViewById(R.id.pager);
        FragmentStateAdapter fragmentStateAdapter = new DetailPagerAdapter(this);
        mViewPager2.setAdapter(fragmentStateAdapter);

        if (savedInstanceState != null) {
            int savedIndex = savedInstanceState.getInt(mOutStateIndex);
            mViewPager2.setCurrentItem(savedIndex);
        } else {
            mViewPager2.setCurrentItem(mResultIndex);
        }

        setTitle(titleBuilder(mViewPager2.getCurrentItem(), mResultListSize));

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setTitle(titleBuilder(mViewPager2.getCurrentItem(), mResultListSize));
            }
        });
    }

    /**
     * Format a title to display in the actionbar.
     *
     * @param currentIndex ViewPager2 current item.
     * @param resultLength mResultList size.
     * @return String value of StringBuilder
     */
    @NonNull
    private String titleBuilder(int currentIndex, int resultLength) {
        StringBuilder mStringBuilder;
        String stringCurrentIndex, stringResultLength;

        mStringBuilder = new StringBuilder();
        stringCurrentIndex = String.valueOf(currentIndex + 1);
        stringResultLength = String.valueOf(resultLength);
        mStringBuilder
                .append(mTitleText)
                .append(stringCurrentIndex)
                .append("/")
                .append(stringResultLength);
        return mStringBuilder.toString();
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

    private class DetailPagerAdapter extends FragmentStateAdapter {
        public DetailPagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            APIResultsModel mResult = mResultList.get(position);
            return DetailFragment.newInstance(mResult);
        }

        @Override
        public int getItemCount() {
            return mResultList.size();
        }
    }

}