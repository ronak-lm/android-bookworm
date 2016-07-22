package com.ronakmanglani.bookworm.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.api.VolleySingleton;
import com.ronakmanglani.bookworm.data.BookColumns;
import com.ronakmanglani.bookworm.model.Book;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnBookClickListener;
import com.ronakmanglani.bookworm.ui.adapter.viewholder.BookViewHolder;
import com.ronakmanglani.bookworm.util.StringUtil;
import com.squareup.picasso.Picasso;

public class BookCursorAdapter extends CursorAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnBookClickListener onBookClickListener;

    // Constructor
    public BookCursorAdapter(OnBookClickListener onBookClickListener) {
        super(BookWormApp.getAppContext(), null);
        this.context = BookWormApp.getAppContext();
        this.onBookClickListener = onBookClickListener;
    }

    // Helper methods
    public Book getItemAt(int position) {
        Cursor cursor = getCursor();
        if (cursor == null || cursor.isClosed() || cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToPosition(position);
            return getBookFromCursor(cursor);
        }
    }
    public Book getBookFromCursor(Cursor cursor) {
        String uniqueId = cursor.getString(cursor.getColumnIndex(BookColumns.BOOK_ID));
        String isbn10 = cursor.getString(cursor.getColumnIndex(BookColumns.ISBN_10));
        String isbn13 = cursor.getString(cursor.getColumnIndex(BookColumns.ISBN_13));
        String title = cursor.getString(cursor.getColumnIndex(BookColumns.TITLE));
        String subtitle = cursor.getString(cursor.getColumnIndex(BookColumns.SUBTITLE));
        String authors = cursor.getString(cursor.getColumnIndex(BookColumns.AUTHORS));
        String pageCount = cursor.getString(cursor.getColumnIndex(BookColumns.PAGE_COUNT));
        String avgRating = cursor.getString(cursor.getColumnIndex(BookColumns.AVG_RATING));
        String ratingCount = cursor.getString(cursor.getColumnIndex(BookColumns.RATING_COUNT));
        String imageUrl = cursor.getString(cursor.getColumnIndex(BookColumns.IMAGE_URL));
        String publisher = cursor.getString(cursor.getColumnIndex(BookColumns.PUBLISHER));
        String publishDate = cursor.getString(cursor.getColumnIndex(BookColumns.PUBLISH_DATE));
        String description = cursor.getString(cursor.getColumnIndex(BookColumns.DESCRIPTION));
        String itemUrl = cursor.getString(cursor.getColumnIndex(BookColumns.ITEM_URL));
        return new Book(uniqueId, isbn10, isbn13, title, subtitle,
                authors, pageCount, avgRating, ratingCount, imageUrl,
                publisher, publishDate, description, itemUrl);
    }

    // RecyclerView methods
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_grid, parent, false);
        return new BookViewHolder(v, onBookClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        // Get book from database
        Book book = getBookFromCursor(cursor);
        final BookViewHolder holder = (BookViewHolder) viewHolder;
        // Cover image
        if (!StringUtil.isNullOrEmpty(book.getImageUrl())) {
            Picasso.with(context)
                    .load(book.getImageUrl())
                    .centerCrop()
                    .into(holder.coverImage);
        } else {
            holder.coverImage.setImageDrawable(ContextCompat.
                    getDrawable(BookWormApp.getAppContext(), R.drawable.default_cover_big));
        }
        // TextViews
        holder.title.setText(book.getTitle());
        holder.title.post(new Runnable() {
            @Override
            public void run() {
                if (holder.title.getLineCount() == 1) {
                    holder.author.setMaxLines(2);
                } else {
                    holder.author.setMaxLines(1);
                }
            }
        });
        holder.author.setText(book.getAuthors());
    }
}
