package com.ronakmanglani.booknerd.util;

import android.util.DisplayMetrics;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;

public class DimenUtil {

    // Private constructor to prevent instantiation
    private DimenUtil() {
    }

    // Check if device is a tablet
    public static boolean isTablet() {
        return BookNerdApp.getAppContext().getResources().getBoolean(R.bool.is_tablet);
    }

    // Get number of columns for RecyclerView
    public static int getNumberOfColumns(int dimenId, int minColumns) {
        // Get screen width
        DisplayMetrics displayMetrics = BookNerdApp.getAppContext().getResources().getDisplayMetrics();
        float widthPx = displayMetrics.widthPixels;
        if (isTablet()) {
            widthPx = widthPx / 3;
        }
        // Calculate desired width
        float desiredPx = BookNerdApp.getAppContext().getResources().getDimensionPixelSize(dimenId);
        int columns = Math.round(widthPx / desiredPx);
        return columns > minColumns ? columns : minColumns;
    }
}
