package com.example.foodhygienescores;

import java.io.Serializable;

public class APIResultsModel implements Serializable {

    /**
     * Variable names based on the API response
     *
     * https://api.ratings.food.gov.uk/Help/Api/GET-Establishments_name_address_longitude_latitude
     * _maxDistanceLimit_businessTypeId_schemeTypeKey_ratingKey_ratingOperatorKey_localAuthorityId
     * _countryId_sortOptionKey_pageNumber_pageSize
     */

    private int FHRSID;
    private String businessName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String postCode;
    private String ratingValue;
    private String authorityName;
    private String authorityWebsite;
    private String authorityEmail;
    private int scoreHygiene;
    private int scoreStructural;
    private int scoreConInMan;
    private String longitude;
    private String latitude;
    private double distance;

    public APIResultsModel() {
    }

    public int getFHRSID() {
        return FHRSID;
    }

    public void setFHRSID(int FHRSID) {
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

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityWebsite() {
        return authorityWebsite;
    }

    public void setAuthorityWebsite(String authorityWebsite) {
        this.authorityWebsite = authorityWebsite;
    }

    public String getAuthorityEmail() {
        return authorityEmail;
    }

    public void setAuthorityEmail(String authorityEmail) {
        this.authorityEmail = authorityEmail;
    }

    public int getScoreHygiene() {
        return scoreHygiene;
    }

    public void setScoreHygiene(int scoreHygiene) {
        this.scoreHygiene = scoreHygiene;
    }

    public int getScoreStructural() {
        return scoreStructural;
    }

    public void setScoreStructural(int scoreStructural) {
        this.scoreStructural = scoreStructural;
    }

    public int getScoreConInMan() {
        return scoreConInMan;
    }

    public void setScoreConInMan(int scoreConInMan) {
        this.scoreConInMan = scoreConInMan;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

