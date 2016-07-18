package com.ronakmanglani.bookworm.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.api.VolleySingleton;
import com.ronakmanglani.bookworm.model.Bestseller;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnBookClickListener;
import com.ronakmanglani.bookworm.ui.adapter.viewholder.BookViewHolder;

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
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(v, onBookClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Bestseller book = bestsellerBooks.get(position);
        final BookViewHolder holder = (BookViewHolder) viewHolder;
        holder.title.setText(book.getTitle());
        holder.author.setText(BookWormApp.getAppContext().getString(R.string.detail_subtitle_by, book.getAuthor()));
        holder.description.setText(book.getDescription());
        holder.coverImage.setImageUrl(book.getImageUrl(), VolleySingleton.getInstance().imageLoader);
        holder.bookRatingHolder.setVisibility(View.GONE);
    }
}