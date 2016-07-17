package com.ronakmanglani.booknerd.api;

import android.net.Uri;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;

public class ApiHelper {

    // Private constructor to prevent instantiation
    private ApiHelper() {
    }

    // API keys
    private static String getGoogleBooksApiKey() {
        return BookNerdApp.getAppContext().getString(R.string.google_books_key);
    }
    private static String getNewYorkTimesBestsellerApiKey() {
        return BookNerdApp.getAppContext().getString(R.string.nyt_bestseller_key);
    }

    // API endpoints
    public static String getBestsellerListUrl(String listName) {
        return "https://api.nytimes.com/svc/books/v3/lists//" + listName + ".json?api-key=" + getNewYorkTimesBestsellerApiKey();
    }
    public static String getSearchListUrl(String query, int startIndex) {
        return Uri.parse("https://www.googleapis.com/books/v1/volumes").buildUpon()
                .appendQueryParameter("q", query)
                .appendQueryParameter("key", getGoogleBooksApiKey())
                .appendQueryParameter("startIndex", startIndex + "")
                .build().toString();
    }
}
