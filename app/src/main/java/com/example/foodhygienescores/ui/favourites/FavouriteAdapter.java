package com.example.foodhygienescores.ui.favourites;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhygienescores.MainActivity;
import com.example.foodhygienescores.R;
import com.example.foodhygienescores.db.Favourite;

import java.io.Serializable;
import java.util.List;

import static com.example.foodhygienescores.Utilities.isNumber;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder> {

    class FavouriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static final String PASS_DATA = "DATA";
        public static final String PASS_INDEX = "INDEX";
        private final TextView mBusinessName, mRatingValue;
        protected boolean isWideScreen = MainActivity.isWideScreen;

        private FavouriteHolder(View view) {
            super(view);
            this.mBusinessName = view.findViewById(R.id.business_name);
            this.mRatingValue = view.findViewById(R.id.rating_value);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mAdapterPosition = getAdapterPosition();
            Favourite favourite = mFavouriteList.get(mAdapterPosition);
            Context context = view.getContext();
            if (isWideScreen) {
                FavouriteDetailFragment favouriteDetailFragment =
                        FavouriteDetailFragment.newInstance(favourite);
                FragmentManager fragmentManager =
                        ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_layout_favourite, favouriteDetailFragment)
                        .commit();
            } else {
                Intent intent = new Intent(context, FavouriteDetailActivity.class);
                intent.putExtra(PASS_DATA, (Serializable) mFavouriteList);
                intent.putExtra(PASS_INDEX, mAdapterPosition);
                context.startActivity(intent);
            }
        }
    }

    private List<Favourite> mFavouriteList;
    private final LayoutInflater mLayoutInflater;

    FavouriteAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    void setFavouritesList(List<Favourite> favouritesList) {
        mFavouriteList = favouritesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new FavouriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteHolder holder, int position) {
        if (mFavouriteList != null) {
            Favourite favourite = mFavouriteList.get(position);
            String business_name = favourite.getBusiness_name();
            String rating_value = favourite.getRating_value();
            holder.mBusinessName.setText(business_name);
            if (isNumber(rating_value)) {
                holder.mRatingValue.setText(rating_value);
            } else {
                holder.mRatingValue.setText(R.string.question_mark);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mFavouriteList != null) {
            return mFavouriteList.size();
        }
        return 0;
    }

}