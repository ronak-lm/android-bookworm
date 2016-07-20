package com.ronakmanglani.bookworm.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.api.VolleySingleton;
import com.ronakmanglani.bookworm.model.Bestseller;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnBookClickListener;
import com.ronakmanglani.bookworm.ui.adapter.viewholder.BookGridViewHolder;

import java.util.ArrayList;

public class BestsellerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Bestseller> bestsellerBooks;
    private OnBookClickListener onBookClickListener;

    // Constructor
    public BestsellerAdapter(OnBookClickListener onBookClickListener) {
        this.bestsellerBooks = new ArrayList<>();
        this.onBookClickListener = onBookClickListener;
    }
    public BestsellerAdapter(ArrayList<Bestseller> bestsellerBooks, OnBookClickListener onBookClickListener) {
        this.bestsellerBooks = bestsellerBooks;
        this.onBookClickListener = onBookClickListener;
    }

    // Helper methods
    public void addToList(Bestseller book) {
        bestsellerBooks.add(book);
    }
    public ArrayList<Bestseller> getList() {
        return bestsellerBooks;
    }

    // RecyclerView methods
    @Override
    public int getItemCount() {
        return bestsellerBooks.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_grid, parent, false);
        return new BookGridViewHolder(v, onBookClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Bestseller book = bestsellerBooks.get(position);
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
        holder.author.setText(book.getAuthor());
    }
}