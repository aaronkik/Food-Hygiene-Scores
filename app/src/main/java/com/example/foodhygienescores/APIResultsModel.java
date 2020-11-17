package com.example.foodhygienescores;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class APIResultsModel implements Serializable {

    /*
    Variable names based on the API response:

    https://api.ratings.food.gov.uk/Help/Api/GET-Establishments_name_address_longitude_latitude
    _maxDistanceLimit_businessTypeId_schemeTypeKey_ratingKey_ratingOperatorKey_localAuthorityId
    _countryId_sortOptionKey_pageNumber_pageSize
     */

    private String FHRSID;
    private String businessName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String postCode;
    private String ratingValue;
    private String longitude;
    private String latitude;


    public APIResultsModel(String fhrsid, String businessName, String addressLine1,
                           String addressLine2, String addressLine3, String addressLine4,
                           String postCode, String ratingValue, String longitude, String latitude) {

        this.FHRSID = fhrsid;
        this.businessName = businessName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.addressLine4 = addressLine4;
        this.postCode = postCode;
        this.ratingValue = ratingValue;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public String getFHRSID() {
        return FHRSID;
    }

    public void setFHRSID(String FHRSID) {
        this.FHRSID = FHRSID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}

