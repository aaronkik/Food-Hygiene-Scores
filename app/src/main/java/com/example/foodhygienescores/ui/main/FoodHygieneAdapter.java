package com.example.foodhygienescores.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhygienescores.APIResultsModel;
import com.example.foodhygienescores.R;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import static com.example.foodhygienescores.Utilities.isDouble;
import static com.example.foodhygienescores.Utilities.isNumber;

public class FoodHygieneAdapter extends RecyclerView.Adapter<FoodHygieneAdapter.FoodHygieneHolder> {

    class FoodHygieneHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static final String PASS_DATA = "DATA";
        public static final String PASS_INDEX = "INDEX";
        private final TextView mBusinessName, mRatingValue, mDistance;
        private final ImageView mLocationPin;
        final FoodHygieneAdapter mAdapter;
        protected boolean isWideScreen = MainActivity.isWideScreen;

        public FoodHygieneHolder(View itemView, FoodHygieneAdapter adapter) {
            super(itemView);
            this.mBusinessName = itemView.findViewById(R.id.business_name);
            this.mRatingValue = itemView.findViewById(R.id.rating_value);
            this.mDistance = itemView.findViewById(R.id.distance);
            this.mLocationPin = itemView.findViewById(R.id.location_pin);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mAdapterPosition = getAdapterPosition();
            APIResultsModel resultList = mResultsList.get(mAdapterPosition);
            Context context = view.getContext();
            // If the wide layout exists the device is a tablet
            if (isWideScreen) {
                DetailFragment detailFragment = DetailFragment.newInstance(resultList);
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_layout, detailFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(PASS_DATA, (Serializable) mResultsList);
                intent.putExtra(PASS_INDEX, mAdapterPosition);
                context.startActivity(intent);
            }
        }
    }

    private List<APIResultsModel> mResultsList;
    private final LayoutInflater mInflater;

    public FoodHygieneAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setFoodHygieneAdapter(List<APIResultsModel> apiResultsModels) {
        mResultsList = apiResultsModels;
        notifyDataSetChanged();
    }

    public APIResultsModel getAPIResultsModel(int position) {
        return mResultsList.get(position);
    }

    @NonNull
    @Override
    public FoodHygieneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new FoodHygieneHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHygieneHolder holder, int position) {

        APIResultsModel resultsModel = mResultsList.get(position);
        String businessName = resultsModel.getBusinessName();
        String ratingValue = resultsModel.getRatingValue();
        Double distance = resultsModel.getDistance();

        holder.mBusinessName.setText(businessName);
        if (isNumber(ratingValue)) {
            holder.mRatingValue.setText(ratingValue);
        } else {
            holder.mRatingValue.setText(R.string.question_mark);
        }
        if (isDouble(distance)) {
            String doubleToString = String.format(Locale.ENGLISH, "%.2fm", distance);
            holder.mDistance.setVisibility(View.VISIBLE);
            holder.mLocationPin.setVisibility(View.VISIBLE);
            holder.mDistance.setText(doubleToString);
        } else {
            holder.mLocationPin.setVisibility(View.GONE);
            holder.mDistance.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return mResultsList.size();
    }

}
