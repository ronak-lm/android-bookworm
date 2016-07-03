package com.ronakmanglani.booknerd;

import android.app.Application;
import android.content.Context;

public class BookNerdApp extends Application {

    // Constants for SavedInstanceState
    public static final String TOOLBAR_TITLE = "toolbar_title";
    // Constants for Bundle Arguments
    public static final String LIST_NAME = "list_name";

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
