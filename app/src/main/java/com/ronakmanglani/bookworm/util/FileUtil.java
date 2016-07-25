package com.ronakmanglani.bookworm.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ronakmanglani.bookworm.data.BookColumns;
import com.ronakmanglani.bookworm.data.BookDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {

    private FileUtil() { }

    // File file from source to destination
    public static void copyFile(File source, File destination) throws IOException {
        FileInputStream fromFile = new FileInputStream(source);
        FileOutputStream toFile = new FileOutputStream(destination);
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    // Function to check if file is a valid database
    public static boolean isValidDbFile(File db) {
        try {
            // Open file as a database
            SQLiteDatabase sqlDb = SQLiteDatabase.openDatabase(db.getPath(), null, SQLiteDatabase.OPEN_READONLY);
            // Get cursors for book table
            Cursor cursor = sqlDb.query(true, BookDatabase.BOOKS, null, null, null, null, null, null, null);
            // Check if BOOK_ID, ISBN_10, ISBN_13, SHELF columns exist (else throw exception)
            cursor.getColumnIndexOrThrow(BookColumns.BOOK_ID);
            cursor.getColumnIndexOrThrow(BookColumns.ISBN_10);
            cursor.getColumnIndexOrThrow(BookColumns.ISBN_13);
            cursor.getColumnIndexOrThrow(BookColumns.SHELF);
            // Close database and cursors
            sqlDb.close();
            cursor.close();
            // No exceptions = Valid database
            return true;
        } catch (Exception e) {
            // Exception thrown - Invalid database
            return false;
        }
    }
}
