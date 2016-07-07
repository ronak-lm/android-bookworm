package com.ronakmanglani.booknerd;

import android.app.Application;
import android.content.Context;

public class BookNerdApp extends Application {

    // Constants
    public static final String TAG_BESTSELLER = "bestseller_fragment";
    public static final String KEY_TITLE = "toolbar_title";
    public static final String KEY_NAME = "list_name";
    public static final String KEY_BESTSELLER = "bestseller_list";
    public static final String KEY_QUERY = "search_query";
    public static final String KEY_SEARCH = "search_list";
    public static final String KEY_BOOK = "book_object";
    public static final String KEY_STATE = "current_state";
    public static final int STATE_LOADING = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_LOADED = 3;

    // Initialize context
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
    }

    // To access context from any class
    private static Context mAppContext;
    public static Context getAppContext() {
        return mAppContext;
    }
}
