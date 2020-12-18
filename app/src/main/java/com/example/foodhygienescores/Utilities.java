package com.example.foodhygienescores;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.foodhygienescores.db.Favourite;
import com.example.foodhygienescores.viewmodel.FavouritesViewModel;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    /**
     * Checks to see if a string can be parsed as a number
     *
     * @param string number
     * @return boolean
     */
    public static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Checks to see if a double value is a number
     *
     * @param d Double
     * @return boolean
     */
    public static boolean isDouble(Double d) {
        return !Double.isNaN(d);
    }

    /**
     * @param address1 First address line
     * @param address2 Second address line
     * @param address3 Third address line
     * @param address4 Fourth address line
     * @param postcode PostCode
     * @return String of a full address
     */
    @NonNull
    public static String addressFormatter(String address1, String address2,
                                          String address3, String address4, String postcode) {

        List<String> addressList = new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);
        addressList.add(address3);
        addressList.add(address4);
        addressList.add(postcode);

        StringBuilder stringBuilder = new StringBuilder();
        // Filter out any address lines with empty strings
        for (String string : addressList) {
            if (string.length() > 0) {
                stringBuilder.append(string).append("\n");
            }
        }
        return stringBuilder.toString();
    }


    /**
     * Adds a new Favourite to the Room DB
     *
     * @param apiResultsModel APIResultsModel
     * @param vmso            ViewModelOwner
     */
    public static void addFavouriteToRoom(@NonNull APIResultsModel apiResultsModel,
                                          ViewModelStoreOwner vmso) {
        FavouritesViewModel mFavouritesViewModel = new ViewModelProvider
                (vmso).get(FavouritesViewModel.class);
        Favourite favourite = new Favourite(
                apiResultsModel.getFHRSID(),
                apiResultsModel.getBusinessName(),
                apiResultsModel.getAddressLine1(),
                apiResultsModel.getAddressLine2(),
                apiResultsModel.getAddressLine3(),
                apiResultsModel.getAddressLine4(),
                apiResultsModel.getPostCode(),
                apiResultsModel.getRatingValue(),
                apiResultsModel.getAuthorityName(),
                apiResultsModel.getAuthorityWebsite(),
                apiResultsModel.getAuthorityEmail(),
                apiResultsModel.getScoreHygiene(),
                apiResultsModel.getScoreStructural(),
                apiResultsModel.getScoreConInMan(),
                apiResultsModel.getLongitude(),
                apiResultsModel.getLatitude()
        );
        mFavouritesViewModel.insert(favourite);
    }


    /**
     * Deletes a Favourite from Room by FHRSID
     *
     * @param vmso   ViewModelStoreOwner
     * @param fhrsid FHRSID of business
     */
    public static void deleteFromRoomByFhrsid(ViewModelStoreOwner vmso, int fhrsid) {
        FavouritesViewModel mFavouritesViewModel = new ViewModelProvider
                (vmso).get(FavouritesViewModel.class);
        mFavouritesViewModel.deleteByFhrsid(fhrsid);
    }
}
