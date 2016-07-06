package com.ronakmanglani.booknerd.util;

import android.net.Uri;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;

public class ApiUtil {

    // Private constructor to prevent instantiation
    private ApiUtil() {
    }

    // Get API keys
    private static String getGoogleBooksApiKey() {
        return BookNerdApp.getAppContext().getString(R.string.google_books_key);
    }
    private static String getNewYorkTimesBestsellerApiKey() {
        return BookNerdApp.getAppContext().getString(R.string.nyt_bestseller_key);
    }

    // API endpoints
    public static String getBestsellerList(String listName) {
        return "https://api.nytimes.com/svc/books/v3/lists//" + listName + ".json?api-key=" + getNewYorkTimesBestsellerApiKey();
    }
    public static String getBookDetails(String isbn) {
        return "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + getGoogleBooksApiKey();
    }
    public static String getSearchList(String query) {
        return Uri.parse("https://www.googleapis.com/books/v1/volumes").buildUpon()
                .appendQueryParameter("q", query)
                .appendQueryParameter("key", getGoogleBooksApiKey())
                .build().toString();
    }
}
