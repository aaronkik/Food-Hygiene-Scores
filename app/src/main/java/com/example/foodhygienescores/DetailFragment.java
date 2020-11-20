package com.example.foodhygienescores;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static final String RESULT_DETAIL = FoodHygieneAdapter.FoodHygieneHolder.PASS_DATA;
    private TextView mBusinessName, mAddress, mRatingValue, mHygiene, mStructural,
            mConInMan, mAuthorityName, mAuthorityWebsite, mAuthorityEmail;
    private APIResultsModel mResultDetail;
    protected boolean isWideScreen = MainActivity.isWideScreen;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * @param resultsModel APIResultModel of a selected recyclerview.
     * @return A new instance of fragment DetailFragment.
     */
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            mResultDetail = (APIResultsModel) getArguments().getSerializable(RESULT_DETAIL);
        }

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

        mBusinessName.setText(mResultDetail.getBusinessName());
        mAddress.setText(formatAddress());
        mRatingValue.setText(mResultDetail.getRatingValue());

        if (mResultDetail.getScoreHygiene() >= 0) {
            mHygiene.setText(String.valueOf(mResultDetail.getScoreHygiene()));
        } else {
            mHygiene.setText(R.string.score_unavailable);
        }
        if (mResultDetail.getScoreStructural() >= 0) {
            mStructural.setText(String.valueOf(mResultDetail.getScoreStructural()));
        } else {
            mStructural.setText(R.string.score_unavailable);
        }
        if (mResultDetail.getScoreConInMan() >= 0) {
            mConInMan.setText(String.valueOf(mResultDetail.getScoreConInMan()));
        } else {
            mConInMan.setText(R.string.score_unavailable);
        }

        mAuthorityName.setText(mResultDetail.getAuthorityName());
        mAuthorityWebsite.setText(mResultDetail.getAuthorityWebsite());
        mAuthorityEmail.setText(mResultDetail.getAuthorityEmail());

        view.findViewById(R.id.fragment).setVisibility(View.VISIBLE);

        if (isWideScreen) {
            container.findViewById(R.id.fragmentText).setVisibility(View.GONE);
        }

        return view;
    }

    /**
     * @return String of a full address
     */
    private String formatAddress() {

        List<String> addressList = new ArrayList<>();
        addressList.add(mResultDetail.getAddressLine1());
        addressList.add(mResultDetail.getAddressLine2());
        addressList.add(mResultDetail.getAddressLine3());
        addressList.add(mResultDetail.getAddressLine4());
        addressList.add(mResultDetail.getPostCode());

        StringBuilder stringBuilder = new StringBuilder();
        // Filter out any address lines with empty strings
        for (String string : addressList) {
            if (string.length() > 0) {
                stringBuilder.append(string + "\n");
            }
        }
        return stringBuilder.toString();
    }

}
