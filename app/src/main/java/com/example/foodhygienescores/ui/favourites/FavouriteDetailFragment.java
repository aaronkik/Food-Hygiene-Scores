package com.example.foodhygienescores.ui.favourites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhygienescores.R;
import com.example.foodhygienescores.db.Favourite;
import com.example.foodhygienescores.viewmodel.FavouritesViewModel;

import java.io.Serializable;

import static com.example.foodhygienescores.Utilities.addressFormatter;

public class FavouriteDetailFragment extends Fragment {

    private static final String DATA_KEY = FavouriteAdapter.FavouriteHolder.PASS_DATA;
    private Favourite mFavourite;
    private TextView mBusinessName, mAddress, mRatingValue, mHygiene, mStructural,
            mConInMan, mAuthorityName, mAuthorityWebsite, mAuthorityEmail;
    private Button mDeleteButton, mOpenMapButton;
    private int FHRSID, score_hygiene, score_structural, score_con_in_man;
    private String business_name, address_line1, address_line2, address_line3, address_line4,
            postcode, rating_value, authority_name, authority_website, authority_email, long_, lat;

    public FavouriteDetailFragment() {
    }

    public static FavouriteDetailFragment newInstance(Favourite favourite) {
        FavouriteDetailFragment fragment = new FavouriteDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATA_KEY, (Serializable) favourite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFavourite = (Favourite) getArguments().getSerializable(DATA_KEY);
            FHRSID = mFavourite.getFHRSID();
            business_name = mFavourite.getBusiness_name();
            address_line1 = mFavourite.getAddress_line1();
            address_line2 = mFavourite.getAddress_line2();
            address_line3 = mFavourite.getAddress_line3();
            address_line4 = mFavourite.getAddress_line4();
            postcode = mFavourite.getPost_code();
            rating_value = mFavourite.getRating_value();
            score_hygiene = mFavourite.getScore_hygiene();
            score_structural = mFavourite.getScore_structural();
            score_con_in_man = mFavourite.getScore_con_in_man();
            authority_name = mFavourite.getAuthority_name();
            authority_website = mFavourite.getAuthority_website();
            authority_email = mFavourite.getAuthority_email();
            long_ = mFavourite.getLong_();
            lat = mFavourite.getLat();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_detail, container, false);
        mBusinessName = view.findViewById(R.id.business_name);
        mAddress = view.findViewById(R.id.business_address);
        mRatingValue = view.findViewById(R.id.rating_value);
        mHygiene = view.findViewById(R.id.hygiene);
        mStructural = view.findViewById(R.id.structural);
        mConInMan = view.findViewById(R.id.confidenceInManagement);
        mAuthorityName = view.findViewById(R.id.authority_name);
        mAuthorityWebsite = view.findViewById(R.id.authority_website);
        mAuthorityEmail = view.findViewById(R.id.authority_email);

        mBusinessName.setText(business_name);
        mAddress.setText(
                addressFormatter(address_line1, address_line2, address_line3, address_line4, postcode)
        );
        mRatingValue.setText(rating_value);

        if (score_hygiene >= 0) {
            mHygiene.setText(String.valueOf(score_hygiene));
        } else {
            mHygiene.setText(R.string.score_unavailable);
        }
        if (score_structural >= 0) {
            mStructural.setText(String.valueOf(score_structural));
        } else {
            mStructural.setText(R.string.score_unavailable);
        }
        if (score_con_in_man >= 0) {
            mConInMan.setText(String.valueOf(score_con_in_man));
        } else {
            mConInMan.setText(R.string.score_unavailable);
        }

        mAuthorityName.setText(authority_name);
        mAuthorityWebsite.setText(authority_website);
        mAuthorityEmail.setText(authority_email);

        view.findViewById(R.id.fragment_favourite).setVisibility(View.VISIBLE);

        mOpenMapButton = view.findViewById(R.id.button_show_map);
        // https://developers.google.com/maps/documentation/urls/android-intents
        mOpenMapButton.setOnClickListener(v -> {
            Uri mapQuery = Uri.parse("geo:" + lat + "," + long_ + "?z=19");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapQuery);
            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(getContext(), R.string.map_error, Toast.LENGTH_SHORT).show();
            }
        });

//        mDeleteButton = view.findViewById(R.id.fab_delete);
//        mDeleteButton.setOnClickListener(v -> deleteAll());

        return view;
    }

//    private void deleteAll() {
//        FavouritesViewModel mFavouritesViewModel = new ViewModelProvider
//                (FavouriteDetailFragment.this).get(FavouritesViewModel.class);
//        mFavouritesViewModel.deleteAll();
//        Toast.makeText(getContext(), "Favourites Deleted...", Toast.LENGTH_SHORT ).show();
//    }
}