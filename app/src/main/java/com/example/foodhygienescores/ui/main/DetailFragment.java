package com.example.foodhygienescores.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhygienescores.APIResultsModel;
import com.example.foodhygienescores.R;
import com.example.foodhygienescores.db.Favourite;
import com.example.foodhygienescores.viewmodel.FavouritesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.foodhygienescores.R.string.saved_error;
import static com.example.foodhygienescores.R.string.saved_message;
import static com.example.foodhygienescores.Utilities.addressFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static final String RESULT_DETAIL = FoodHygieneAdapter.FoodHygieneHolder.PASS_DATA;
    protected boolean isWideScreen = MainActivity.isWideScreen;
    private APIResultsModel mResultDetail;
    private TextView mBusinessName, mAddress, mRatingValue, mHygiene, mStructural,
            mConInMan, mAuthorityName, mAuthorityWebsite, mAuthorityEmail;
    private Button mFavouriteButton, mOpenMapButton;
    private int FHRSID, score_hygiene, score_structural, score_con_in_man;
    private String business_name, address_line1, address_line2, address_line3, address_line4,
            postcode, rating_value, authority_name, authority_website, authority_email, long_, lat;

    public DetailFragment() {
    }

    /**
     * @param resultsModel APIResultModel of a selected recyclerview.
     * @return A new instance of fragment DetailFragment.
     */
    @NonNull
    public static DetailFragment newInstance(APIResultsModel resultsModel) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(RESULT_DETAIL, resultsModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mResultDetail = (APIResultsModel) getArguments().getSerializable(RESULT_DETAIL);
            FHRSID = mResultDetail.getFHRSID();
            business_name = mResultDetail.getBusinessName();
            address_line1 = mResultDetail.getAddressLine1();
            address_line2 = mResultDetail.getAddressLine2();
            address_line3 = mResultDetail.getAddressLine3();
            address_line4 = mResultDetail.getAddressLine4();
            postcode = mResultDetail.getPostCode();
            rating_value = mResultDetail.getRatingValue();
            score_hygiene = mResultDetail.getScoreHygiene();
            score_structural = mResultDetail.getScoreStructural();
            score_con_in_man = mResultDetail.getScoreConInMan();
            authority_name = mResultDetail.getAuthorityName();
            authority_website = mResultDetail.getAuthorityWebsite();
            authority_email = mResultDetail.getAuthorityEmail();
            long_ = mResultDetail.getLongitude();
            lat = mResultDetail.getLatitude();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
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

        view.findViewById(R.id.fragment).setVisibility(View.VISIBLE);

        if (isWideScreen) {
            container.findViewById(R.id.fragmentText).setVisibility(View.GONE);
            mFavouriteButton = view.findViewById(R.id.button_favourite);
            mFavouriteButton.setOnClickListener(view1 -> addFavourite());
        } else {
            FloatingActionButton mFavouriteFab = view.findViewById(R.id.fab_favourite);
            mFavouriteFab.setOnClickListener(view2 -> addFavourite());
        }

        mOpenMapButton = view.findViewById(R.id.button_show_map);

        // https://developers.google.com/maps/documentation/urls/android-intents
        mOpenMapButton.setOnClickListener(view2 -> {
            Uri mapQuery = Uri.parse("geo:" + lat + "," + long_ + "?z=19");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapQuery);
            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(getContext(), R.string.map_error, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addFavourite() {
        try {
            FavouritesViewModel mFavouritesViewModel = new ViewModelProvider
                    (DetailFragment.this).get(FavouritesViewModel.class);
            Favourite favourite = new Favourite(
                    FHRSID,
                    business_name,
                    address_line1,
                    address_line2,
                    address_line3,
                    address_line4,
                    postcode,
                    rating_value,
                    authority_name,
                    authority_website,
                    authority_email,
                    score_hygiene,
                    score_structural,
                    score_con_in_man,
                    long_,
                    lat
            );
            mFavouritesViewModel.insert(favourite);
            Toast.makeText(getContext(), saved_message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), saved_error, Toast.LENGTH_SHORT).show();
        }
    }

}
