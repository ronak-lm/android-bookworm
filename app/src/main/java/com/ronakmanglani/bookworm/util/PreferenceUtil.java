package com.ronakmanglani.bookworm.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ronakmanglani.bookworm.BookWormApp;

public class PreferenceUtil {

    // Constants
    private static final String PREFERENCE_NAME = "user_settings";
    private static final String PREF_SORT = "sort_type";

    // Return SharedPreferences instance
    private static SharedPreferences getPreference() {
        return BookWormApp.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    // Get/Put methods
    public static void putSortType(int sortType) {
        getPreference().edit().putInt(PREF_SORT, sortType).commit();
    }
    public static int getSortType() {
        return getPreference().getInt(PREF_SORT, BookWormApp.SORT_TITLE);
    }
}
