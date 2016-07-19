package com.ronakmanglani.bookworm.util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.data.BookColumns;
import com.ronakmanglani.bookworm.data.BookProvider;
import com.ronakmanglani.bookworm.model.Book;

public class DatabaseUtil {

    private DatabaseUtil() { }

    private static ContentValues getContentValues(Book book, int shelf) {
        ContentValues values = new ContentValues();
        values.put(BookColumns.BOOK_ID, book.getUniqueId());
        values.put(BookColumns.ISBN_10, book.getIsbn10());
        values.put(BookColumns.ISBN_13, book.getIsbn13());
        values.put(BookColumns.TITLE, book.getTitle());
        values.put(BookColumns.SUBTITLE, book.getSubtitle());
        values.put(BookColumns.AUTHORS, book.getAuthors());
        values.put(BookColumns.PAGE_COUNT, book.getPageCount());
        values.put(BookColumns.AVG_RATING, book.getAverageRating());
        values.put(BookColumns.RATING_COUNT, book.getRatingCount());
        values.put(BookColumns.IMAGE_URL, book.getImageUrl());
        values.put(BookColumns.PUBLISHER, book.getPublisher());
        values.put(BookColumns.PUBLISH_DATE, book.getPublishDate());
        values.put(BookColumns.DESCRIPTION, book.getDescription());
        values.put(BookColumns.ITEM_URL, book.getItemUrl());
        values.put(BookColumns.SHELF, shelf);
        return values;
    }

    public static int getCurrentShelf(String bookId) {
        Cursor data = BookWormApp.getAppContext().getContentResolver().
                query(BookProvider.Books.CONTENT_URI,
                        new String[] { BookColumns.SHELF },
                        BookColumns.BOOK_ID + " = '" + bookId + "'",
                        null, null);
        if (data != null && data.getCount() > 0) {
            data.moveToFirst();
            int currentShelf = data.getInt(data.getColumnIndex(BookColumns.SHELF));
            data.close();
            return currentShelf;
        } else {
            return 0;
        }
    }
    public static PopupMenu getPopupMenu(Activity activity, final Book book, final int currentShelf, View view) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        popupMenu.inflate(R.menu.menu_popup);
        switch (currentShelf) {
            case BookColumns.SHELF_TO_READ:
                popupMenu.getMenu().getItem(0).setTitle(activity.getString(R.string.detail_fab_to_read_remove));
                popupMenu.getMenu().getItem(1).setTitle(activity.getString(R.string.detail_fab_reading));
                popupMenu.getMenu().getItem(2).setTitle(activity.getString(R.string.detail_fab_finished));
                break;

            case BookColumns.SHELF_READING:
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.getMenu().getItem(1).setTitle(activity.getString(R.string.detail_fab_reading_remove));
                popupMenu.getMenu().getItem(2).setTitle(activity.getString(R.string.detail_fab_finished));
                break;

            case BookColumns.SHELF_FINISHED:
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.getMenu().getItem(1).setVisible(false);
                popupMenu.getMenu().getItem(2).setTitle(activity.getString(R.string.detail_fab_finished_remove));
                break;
        }
        // Set click listener for popup menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_to_read:
                        DatabaseUtil.onToReadClicked(book, currentShelf);
                        return true;

                    case R.id.action_reading:
                        DatabaseUtil.onReadingClicked(book, currentShelf);
                        return true;

                    case R.id.action_finished:
                        DatabaseUtil.onFinishedClicked(book, currentShelf);
                        return true;

                    default:
                        return false;
                }
            }
        });
        return popupMenu;
    }

    public static void onToReadClicked(Book book, int currentShelf) {
        if (currentShelf == BookColumns.SHELF_TO_READ) {
            // Remove from "To Read"
            BookWormApp.getAppContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + book.getUniqueId() + "'",
                            null);
        } else {
            // Insert into "To Read"
            BookWormApp.getAppContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(book, BookColumns.SHELF_TO_READ));
        }
    }
    public static void onReadingClicked(Book book, int currentShelf) {
        if (currentShelf == BookColumns.SHELF_TO_READ) {
            // Move from "To Read" to "Reading"
            ContentValues values = new ContentValues();
            values.put(BookColumns.SHELF, BookColumns.SHELF_READING);
            BookWormApp.getAppContext().getContentResolver().
                    update(BookProvider.Books.CONTENT_URI, values,
                            BookColumns.BOOK_ID + " = '" + book.getUniqueId() + "'",
                            new String[]{});
        } else if (currentShelf == BookColumns.SHELF_READING) {
            // Remove from "Reading"
            BookWormApp.getAppContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + book.getUniqueId() + "'",
                            null);
        } else {
            // Insert into "Reading"
            BookWormApp.getAppContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(book, BookColumns.SHELF_READING));
        }
    }
    public static void onFinishedClicked(Book book, int currentShelf) {
        if (currentShelf == BookColumns.SHELF_TO_READ || currentShelf == BookColumns.SHELF_READING) {
            // Move from "To Read" or "Reading" to "Finished"
            ContentValues values = new ContentValues();
            values.put(BookColumns.SHELF, BookColumns.SHELF_FINISHED);
            BookWormApp.getAppContext().getContentResolver().
                    update(BookProvider.Books.CONTENT_URI, values,
                            BookColumns.BOOK_ID + " = '" + book.getUniqueId() + "'",
                            new String[]{});
        } else if (currentShelf == BookColumns.SHELF_FINISHED) {
            // Remove from "Finished"
            BookWormApp.getAppContext().getContentResolver().
                    delete(BookProvider.Books.CONTENT_URI,
                            BookColumns.BOOK_ID + " = '" + book.getUniqueId() + "'",
                            null);
        } else {
            // Insert into "Finished"
            BookWormApp.getAppContext().getContentResolver().
                    insert(BookProvider.Books.CONTENT_URI,
                            getContentValues(book, BookColumns.SHELF_FINISHED));
        }
    }
}
