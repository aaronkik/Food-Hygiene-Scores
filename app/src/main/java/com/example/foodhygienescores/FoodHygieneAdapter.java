package com.example.foodhygienescores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class FoodHygieneAdapter extends
        RecyclerView.Adapter<FoodHygieneAdapter.FoodHygieneHolder> {

    private final LinkedList<String> mFoodHygieneData;
    private final LayoutInflater mInflater;

    public FoodHygieneAdapter(Context context, LinkedList<String> foodHygieneData) {
        mInflater = LayoutInflater.from(context);
        this.mFoodHygieneData = foodHygieneData;
    }

    class FoodHygieneHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView mItemView;
        final FoodHygieneAdapter mAdapter;


        public FoodHygieneHolder(View itemView, FoodHygieneAdapter adapter) {
            super(itemView);
            mItemView = itemView.findViewById(R.id.card_title);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("ITEM CLICK", "onClick: ");
        }
    }

    @NonNull
    @Override
    public FoodHygieneAdapter.FoodHygieneHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new FoodHygieneHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHygieneAdapter.FoodHygieneHolder holder,
                                 int position) {
        String mCurrent = mFoodHygieneData.get(position);
        holder.mItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mFoodHygieneData.size();
    }
}
