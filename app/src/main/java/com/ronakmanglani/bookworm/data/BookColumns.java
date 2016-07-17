package com.ronakmanglani.bookworm.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface BookColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(TEXT) @NotNull String BOOK_ID = "book_id";
    @DataType(TEXT) @NotNull String ISBN_10 = "isbn_10";
    @DataType(TEXT) @NotNull String ISBN_13 = "isbn_13";
    @DataType(TEXT) @NotNull String TITLE = "title";
    @DataType(TEXT) @NotNull String SUBTITLE = "subtitle";
    @DataType(TEXT) @NotNull String AUTHORS = "authors";
    @DataType(TEXT) @NotNull String PAGE_COUNT = "page_count";
    @DataType(TEXT) @NotNull String AVG_RATING = "avg_rating";
    @DataType(TEXT) @NotNull String RATING_COUNT = "rating_count";
    @DataType(TEXT) @NotNull String IMAGE_URL = "image_url";
    @DataType(TEXT) @NotNull String PUBLISHER = "publisher";
    @DataType(TEXT) @NotNull String PUBLISH_DATE = "publish_date";
    @DataType(TEXT) @NotNull String DESCRIPTION = "description";
    @DataType(TEXT) @NotNull String ITEM_URL = "item_url";
    @DataType(INTEGER) @NotNull String SHELF = "shelf";

    // Constants for book shelf
    int SHELF_NONE = 0;
    int SHELF_TO_READ = 1;
    int SHELF_READING = 2;
    int SHELF_FINISHED = 3;

}
