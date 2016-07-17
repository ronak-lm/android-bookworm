package com.ronakmanglani.bookworm.ui.adapter;

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
import com.ronakmanglani.bookworm.model.Book;
import com.ronakmanglani.bookworm.api.VolleySingleton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // ArrayList of books to display
    private ArrayList<Book> booksList;

    // Constructor
    public SearchAdapter(OnBookClickListener onBookClickListener) {
        this.booksList = new ArrayList<>();
        this.onBookClickListener = onBookClickListener;
    }

    // Helper methods
    public void addToList(Book book) {
        booksList.add(book);
    }
    public ArrayList<Book> getList() {
        return booksList;
    }
    public void setList(ArrayList<Book> booksList) {
        this.booksList = booksList;
    }

    // RecyclerView methods
    @Override
    public int getItemCount() {
        return booksList.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(v, onBookClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Book book = booksList.get(position);
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