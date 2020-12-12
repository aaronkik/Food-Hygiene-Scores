package com.example.foodhygienescores;

import java.io.Serializable;

public class UserLocation implements Serializable {

    private String userLongitude;
    private String userLatitude;

    public UserLocation() {
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }
}
