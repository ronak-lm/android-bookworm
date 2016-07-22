package com.ronakmanglani.bookworm;

import android.app.Application;
import android.content.Context;

public class BookWormApp extends Application {

    // Constants
    public static final String TAG_BESTSELLER = "bestseller_fragment";
    public static final String TAG_LIST = "list_fragment";
    public static final String KEY_TITLE = "toolbar_title";
    public static final String KEY_NAME = "list_name";
    public static final String KEY_BESTSELLER = "bestseller_list";
    public static final String KEY_QUERY = "search_query";
    public static final String KEY_SEARCH = "search_list";
    public static final String KEY_INDEX = "start_index";
    public static final String KEY_TOTAL = "total_items";
    public static final String KEY_SORT = "sort_type";
    public static final String KEY_BOOK = "book_object";
    public static final String KEY_VISIBILITY = "message_visibility";
    public static final String KEY_SHELF = "book_shelf";
    public static final String KEY_STATE = "current_state";
    public static final int STATE_LOADING = 1;
    public static final int STATE_LOADED = 2;
    public static final int STATE_FAILED = 3;
    public static final int STATE_LOCKED = 4;
    public static final int SORT_TITLE = 1;
    public static final int SORT_AUTHOR = 2;

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
