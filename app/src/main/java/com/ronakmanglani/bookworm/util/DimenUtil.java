package com.ronakmanglani.bookworm.util;

import android.util.DisplayMetrics;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;

public class DimenUtil {

    private DimenUtil() { }

    // Check if device is a tablet
    public static boolean isTablet() {
        return BookWormApp.getAppContext().getResources().getBoolean(R.bool.is_tablet);
    }

    // Get number of columns for RecyclerView
    public static int getNumberOfColumns(int dimenId, int minColumns) {
        // Get screen width
        DisplayMetrics displayMetrics = BookWormApp.getAppContext().getResources().getDisplayMetrics();
        float widthPx = displayMetrics.widthPixels;
        if (isTablet()) {
            widthPx = widthPx / 3;
        }
        // Calculate desired width
        float desiredPx = BookWormApp.getAppContext().getResources().getDimensionPixelSize(dimenId);
        int columns = Math.round(widthPx / desiredPx);
        return columns > minColumns ? columns : minColumns;
    }
}
