package com.example.foodhygienescores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FoodHygieneAdapter extends RecyclerView.Adapter<FoodHygieneAdapter.FoodHygieneHolder> {

    class FoodHygieneHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final TextView mBusinessName, mAddress, mRatingValue;
        final FoodHygieneAdapter mAdapter;
        public static final String PASS_DATA = "DATA";

        public FoodHygieneHolder(View itemView, FoodHygieneAdapter adapter) {

            super(itemView);
            this.mBusinessName = itemView.findViewById(R.id.business_name);
            this.mAddress = itemView.findViewById(R.id.address);
            this.mRatingValue = itemView.findViewById(R.id.rating_value);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            APIResultsModel resultList = mResultsList.get(getAdapterPosition());
            Context context = view.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(PASS_DATA, resultList);
            context.startActivity(intent);
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

        List<String> addressList = new ArrayList<>();
        addressList.add(resultsModel.getAddressLine1());
        addressList.add(resultsModel.getAddressLine2());
        addressList.add(resultsModel.getAddressLine3());
        addressList.add(resultsModel.getAddressLine4());
        addressList.add(resultsModel.getPostCode());

        StringBuilder stringBuilder = new StringBuilder();
        // Filter out any address lines with empty strings
        for (String string : addressList) {
            if (string.length() > 0) {
                stringBuilder.append(string + "\n");
            }
        }

        String address = stringBuilder.toString();
        String ratingValue = resultsModel.getRatingValue();

        holder.mBusinessName.setText(businessName);
        holder.mAddress.setText(address);
        holder.mRatingValue.setText(ratingValue);

    }

    @Override
    public int getItemCount() {
        return mResultsList.size();
    }
}
