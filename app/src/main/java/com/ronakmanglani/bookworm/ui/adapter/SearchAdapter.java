package com.ronakmanglani.bookworm.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.api.VolleySingleton;
import com.ronakmanglani.bookworm.model.Book;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnBookClickListener;
import com.ronakmanglani.bookworm.ui.adapter.viewholder.BookGridViewHolder;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Book> booksList;
    private OnBookClickListener onBookClickListener;

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
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_grid, parent, false);
        return new BookGridViewHolder(v, onBookClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Book book = booksList.get(position);
        final BookGridViewHolder holder = (BookGridViewHolder) viewHolder;
        // Cover image
        if (book.getImageUrl().length() == 0) {
            holder.coverImage.setImageDrawable(ContextCompat.getDrawable(
                    BookWormApp.getAppContext(), R.drawable.default_cover_big));
        } else {
            holder.coverImage.setImageUrl(book.getImageUrl(), VolleySingleton.getInstance().imageLoader);
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