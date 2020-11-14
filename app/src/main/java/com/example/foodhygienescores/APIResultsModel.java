package com.example.foodhygienescores;

import org.json.JSONObject;

import java.util.ArrayList;

public class APIResultsModel {

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
    private String postCode;
    private String ratingValue;
    private JSONObject geoCode;
    private ArrayList<APIResultsModel> test;

    public APIResultsModel(String fhrsid, String businessName, String addressLine1,
                           String addressLine2, String addressLine3, String postCode,
                           String ratingValue, JSONObject geoCode) {

        this.FHRSID = fhrsid;
        this.businessName = businessName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.postCode = postCode;
        this.ratingValue = ratingValue;
        this.geoCode = geoCode;
    }

    public ArrayList<APIResultsModel> getTest() {
        return test;
    }

    public void setTest(ArrayList<APIResultsModel> test) {
        this.test = test;
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

    public JSONObject getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(JSONObject geoCode) {
        this.geoCode = geoCode;
    }

}

