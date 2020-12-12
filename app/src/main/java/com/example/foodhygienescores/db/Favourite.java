package com.example.foodhygienescores.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favourites")
public class Favourite implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "fhrsid")
    private int mFHRSID;
    private String business_name;
    private String address_line1;
    private String address_line2;
    private String address_line3;
    private String address_line4;
    private String post_code;
    private String rating_value;
    private String authority_name;
    private String authority_website;
    private String authority_email;
    private int score_hygiene;
    private int score_structural;
    private int score_con_in_man;
    private String long_;
    private String lat;

    public Favourite(int mFHRSID,
                     String business_name,
                     String address_line1,
                     String address_line2,
                     String address_line3,
                     String address_line4,
                     String post_code,
                     String rating_value,
                     String authority_name,
                     String authority_website,
                     String authority_email,
                     int score_hygiene,
                     int score_structural,
                     int score_con_in_man,
                     String long_,
                     String lat) {

        this.mFHRSID = mFHRSID;
        this.business_name = business_name;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.address_line3 = address_line3;
        this.address_line4 = address_line4;
        this.post_code = post_code;
        this.rating_value = rating_value;
        this.authority_name = authority_name;
        this.authority_website = authority_website;
        this.authority_email = authority_email;
        this.score_hygiene = score_hygiene;
        this.score_structural = score_structural;
        this.score_con_in_man = score_con_in_man;
        this.long_ = long_;
        this.lat = lat;
    }

    public int getFHRSID() {
        return mFHRSID;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public String getAddress_line3() {
        return address_line3;
    }

    public String getAddress_line4() {
        return address_line4;
    }

    public String getPost_code() {
        return post_code;
    }

    public String getRating_value() {
        return rating_value;
    }

    public String getAuthority_name() {
        return authority_name;
    }

    public String getAuthority_website() {
        return authority_website;
    }

    public String getAuthority_email() {
        return authority_email;
    }

    public int getScore_hygiene() {
        return score_hygiene;
    }

    public int getScore_structural() {
        return score_structural;
    }

    public int getScore_con_in_man() {
        return score_con_in_man;
    }

    public String getLong_() {
        return long_;
    }

    public String getLat() {
        return lat;
    }
}
