package com.ronakmanglani.bookworm.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = BookDatabase.VERSION)
public final class BookDatabase {

    public static final int VERSION = 1;

    @Table(BookColumns.class) public static final String BOOKS = "books";

}
