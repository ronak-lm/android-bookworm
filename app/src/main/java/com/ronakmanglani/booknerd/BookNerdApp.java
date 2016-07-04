package com.ronakmanglani.booknerd;

import android.app.Application;
import android.content.Context;

public class BookNerdApp extends Application {

    // Constants
    public static final String TOOLBAR_TITLE = "toolbar_title";
    public static final String TAG_BESTSELLER_FRAGMENT = "bestseller_fragment";

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
