package com.ronakmanglani.bookworm.api;

import android.net.Uri;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;

public class ApiHelper {

    // Private constructor to prevent instantiation
    private ApiHelper() {
    }

    // API keys
    private static String getGoogleBooksApiKey() {
        return BookWormApp.getAppContext().getString(R.string.google_books_key);
    }
    private static String getNewYorkTimesBestsellerApiKey() {
        return BookWormApp.getAppContext().getString(R.string.nyt_bestseller_key);
    }

    // API endpoints
    public static String getBestsellerListUrl(String listName) {
        return "https://api.nytimes.com/svc/books/v3/lists//" + listName + ".json?api-key=" + getNewYorkTimesBestsellerApiKey();
    }
    public static String getSearchListUrl(String query, int startIndex) {
        return Uri.parse("https://www.googleapis.com/books/v1/volumes").buildUpon()
                .appendQueryParameter("q", query)
                .appendQueryParameter("startIndex", startIndex + "")
                .appendQueryParameter("fields", "totalItems,items(id,volumeInfo)")
                .appendQueryParameter("key", getGoogleBooksApiKey())
                .build().toString();
    }
}
