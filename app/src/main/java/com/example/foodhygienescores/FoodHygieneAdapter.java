package com.example.foodhygienescores;

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

import java.util.ArrayList;

public class FoodHygieneAdapter extends RecyclerView.Adapter<FoodHygieneAdapter.FoodHygieneHolder> {

    class FoodHygieneHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static final String PASS_DATA = "DATA";
        public static final String PASS_INDEX = "INDEX";
        private int mAdapterPosition;
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
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            mAdapterPosition = getAdapterPosition();
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
                intent.putExtra(PASS_DATA, mResultsList);
                intent.putExtra(PASS_INDEX, mAdapterPosition);
                context.startActivity(intent);
            }
        }
    }

    private final ArrayList<APIResultsModel> mResultsList;
    private final LayoutInflater mInflater;


    public FoodHygieneAdapter(Context context, ArrayList<APIResultsModel> resultsList) {
        mInflater = LayoutInflater.from(context);
        this.mResultsList = resultsList;
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
            String doubleToString = String.format("%.2f", distance);
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

    private boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    // getDistance is always type Double as defined in APIResultsModel
    private boolean isDouble(Double d) {
        if (Double.isNaN(d)) {
            return false;
        } else {
            return true;
        }
    }


}
