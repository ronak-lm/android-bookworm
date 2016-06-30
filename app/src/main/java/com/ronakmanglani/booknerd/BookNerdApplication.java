package com.ronakmanglani.booknerd;

import android.app.Application;
import android.content.Context;

public class BookNerdApplication extends Application {

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
