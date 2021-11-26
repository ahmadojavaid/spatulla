package com.pongodev.recipesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.android.gms.ads.AdView;

/**
 * Design and developed by pongodev.com
 *
 * Utils is created to set application configuration, from database path, ad visibility.
 * Created using SQLiteOpenHelper.
 */
public class Utils {

    // Application parameters. do not change this parameters.
    public static final String ARG_PAGE = "page";
    public static final String ARG_CATEGORY = "category";
    public static final String ARG_SEARCH = "search";
    public static final String ARG_FAVORITES = "favorites";
    public static final String ARG_KEY = "key";
    public static final String ARG_CONTENT = "content";
    public static final String ARG_PARENT_ACTIVITY = "parent_activity";
    public static final String ARG_ACTIVITY_HOME = "activities.ActivityHome";
    public static final String ARG_ACTIVITY_SEARCH = "activities.ActivitySearch";
    public static final String ARG_ACTIVITY_FAVORITES = "activities.ActivityFavorites";
    public static final String ARG_TRIGGER = "trigger";

    // Configurable parameters. you can configure these parameter.
    // Set database path. Change com.pongodev.spatullr with your own package name.
    // It must be equal with package name.
    public static final String ARG_DATABASE_PATH = "/data/data/com.pongodev.recipesapp/databases/";
    // For every recipe detail you want to display interstitial ad.
    // 3 means interstitial ad will display after user open detail page three times.
    public static final int ARG_TRIGGER_VALUE = 3;
    // Admob visibility parameter. set true to show admob and false to hide.
    public static final boolean IS_ADMOB_VISIBLE = true;
    // Set value to true if you are still in development process,
    // and false if you are ready to publish the app.
    public static final boolean IS_ADMOB_IN_DEBUG = false;
    // Set default category data, you can see the category id in sqlite database.
    public static final String ARG_DEFAULT_CATEGORY_ID = "2";


    // Method to check admob visibility
    public static boolean admobVisibility(AdView ad, boolean isInDebugMode){
        if(isInDebugMode) {
            ad.setVisibility(View.VISIBLE);
            return true;
        }else {
            ad.setVisibility(View.GONE);
            return false;
        }
    }

    // Method to load data that stored in SharedPreferences
    public static int loadPreferences(String param, Context c){
        SharedPreferences sharedPreferences = c.getSharedPreferences("user_data", 0);
        return sharedPreferences.getInt(param, 0);
    }

    // Method to save data to SharedPreferences
    public static void savePreferences(String param, int value, Context c){
        SharedPreferences sharedPreferences = c.getSharedPreferences("user_data", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(param, value);
        editor.apply();
    }



}
