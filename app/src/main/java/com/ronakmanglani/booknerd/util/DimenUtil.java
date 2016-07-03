package com.ronakmanglani.booknerd.util;

import android.util.DisplayMetrics;

import com.ronakmanglani.booknerd.BookNerdApp;

public class DimenUtil {

    // Private constructor to prevent instantiation
    private DimenUtil() {
    }

    // Get number of columns for RecyclerView
    public static int getNumberOfColumns(int dimenId, int minColumns) {
        // Get screen width
        DisplayMetrics displayMetrics = BookNerdApp.getAppContext().getResources().getDisplayMetrics();
        float widthPx = displayMetrics.widthPixels;
        // Calculate desired width
        float desiredPx = BookNerdApp.getAppContext().getResources().getDimensionPixelSize(dimenId);
        int columns = Math.round(widthPx / desiredPx);
        return columns > minColumns ? columns : minColumns;
    }
}
