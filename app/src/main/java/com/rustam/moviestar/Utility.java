package com.rustam.moviestar;

/**
 * Created by ribra on 12/20/2015.
 */
public class Utility {

    private Utility() {}

    public static String getYearFromDate(String date) {
        int index = date.indexOf("-");
        return date.substring(0, index);
    }

    public static String modifyRating(String rating) {
        return rating + "/10";
    }

    public static String createRequestString(String path) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String SIZE = "w185/";
        return BASE_URL + SIZE + path;
    }

}
