package com.example.foodhygienescores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class FoodHygieneAdapter extends RecyclerView.Adapter<FoodHygieneAdapter.FoodHygieneHolder> {

    class FoodHygieneHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;
        final FoodHygieneAdapter mAdapter;

        public FoodHygieneHolder(View itemView, FoodHygieneAdapter adapter) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.card_title);
            this.mAdapter = adapter;
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
    public FoodHygieneHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new FoodHygieneHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHygieneHolder holder, int position) {
//        APIResultsModel resultsModel = mResultsList.get(position);
//        String mName = resultsModel.getBusinessName();
        holder.mTextView.setText("OWOW");
    }

    @Override
    public int getItemCount() {
        return mResultsList.size();
    }
}
