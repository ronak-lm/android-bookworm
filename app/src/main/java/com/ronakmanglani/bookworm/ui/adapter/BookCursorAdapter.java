package com.ronakmanglani.bookworm.ui.adapter;

import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.api.VolleySingleton;
import com.ronakmanglani.bookworm.data.BookColumns;
import com.ronakmanglani.bookworm.model.Book;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookCursorAdapter extends CursorAdapter<RecyclerView.ViewHolder> {

    // Constructor
    public BookCursorAdapter(OnBookClickListener onBookClickListener) {
        super(BookWormApp.getAppContext(), null);
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
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(v, onBookClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        // Get book from database
        Book book = getBookFromCursor(cursor);
        BookViewHolder holder = (BookViewHolder) viewHolder;
        // Cover image
        if (book.getImageUrl().length() == 0) {
            holder.coverImage.setImageDrawable(ContextCompat.getDrawable(
                    BookWormApp.getAppContext(), R.drawable.default_cover_big));
        } else {
            holder.coverImage.setImageUrl(book.getImageUrl(), VolleySingleton.getInstance().imageLoader);
        }
        // TextViews
        holder.title.setText(book.getTitle());
        if (book.getAuthors().length() > 0) {
            holder.author.setText(BookWormApp.getAppContext().getString(R.string.detail_subtitle_by, book.getAuthors()));
        } else {
            holder.author.setVisibility(View.GONE);
        }
        holder.description.setText(book.getDescription());
        if (book.getAverageRating().length() == 0) {
            holder.bookRatingHolder.setVisibility(View.GONE);
        } else {
            holder.bookRatingHolder.setVisibility(View.VISIBLE);
            holder.bookRating.setText(book.getAverageRating());
        }
    }

    // ViewHolder
    public class BookViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_card)           CardView cardView;
        @BindView(R.id.book_title)          TextView title;
        @BindView(R.id.book_author)         TextView author;
        @BindView(R.id.book_description)    TextView description;
        @BindView(R.id.book_cover)          NetworkImageView coverImage;
        @BindView(R.id.book_rating_holder)  View bookRatingHolder;
        @BindView(R.id.book_rating)         TextView bookRating;

        public BookViewHolder(final ViewGroup itemView, final OnBookClickListener onBookClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBookClickListener.onBookClicked(getAdapterPosition());
                }
            });
        }
    }

    // Click listener interface
    private OnBookClickListener onBookClickListener;
    public interface OnBookClickListener {
        void onBookClicked(final int position);
    }
}
