package com.ronakmanglani.bookworm.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = BookProvider.AUTHORITY, database = BookDatabase.class)
public class BookProvider {

    public static final String AUTHORITY = "com.ronakmanglani.bookworm.data.BookProvider";

    @TableEndpoint(table = BookDatabase.BOOKS) public static class Books {

        @ContentUri(
                path = "books",
                type = "vnd.android.cursor.dir/list",
                defaultSort = BookColumns.TITLE + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/books");
    }

}
