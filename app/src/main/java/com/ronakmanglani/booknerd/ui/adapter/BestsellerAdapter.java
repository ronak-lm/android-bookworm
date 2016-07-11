package com.ronakmanglani.booknerd.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.model.Bestseller;
import com.ronakmanglani.booknerd.api.VolleySingleton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BestsellerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // ArrayList of books to display
    private ArrayList<Bestseller> bestsellerBooks;

    // Constructor
    public BestsellerAdapter(OnBestsellerClickListener onBestsellerClickListener) {
        this.bestsellerBooks = new ArrayList<>();
        this.onBestsellerClickListener = onBestsellerClickListener;
    }
    public BestsellerAdapter(ArrayList<Bestseller> bestsellerBooks, OnBestsellerClickListener onBestsellerClickListener) {
        this.bestsellerBooks = bestsellerBooks;
        this.onBestsellerClickListener = onBestsellerClickListener;
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
        return new BestsellerViewHolder(v, onBestsellerClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Bestseller book = bestsellerBooks.get(position);
        final BestsellerViewHolder holder = (BestsellerViewHolder) viewHolder;
        holder.title.setText(book.getTitle());
        holder.author.setText(BookNerdApp.getAppContext().getString(R.string.detail_subtitle_by, book.getAuthor()));
        holder.description.setText(book.getDescription());
        holder.coverImage.setImageUrl(book.getImageUrl(), VolleySingleton.getInstance().imageLoader);
        holder.bookRatingHolder.setVisibility(View.GONE);
    }

    // ViewHolder
    public class BestsellerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_card)           CardView cardView;
        @BindView(R.id.book_title)          TextView title;
        @BindView(R.id.book_author)         TextView author;
        @BindView(R.id.book_description)    TextView description;
        @BindView(R.id.book_cover)          NetworkImageView coverImage;
        @BindView(R.id.book_rating_holder)  View bookRatingHolder;

        public BestsellerViewHolder(final ViewGroup itemView, final OnBestsellerClickListener onBestsellerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBestsellerClickListener.onBestsellerClicked(getAdapterPosition());
                }
            });
        }
    }

    // Click listener interface
    private OnBestsellerClickListener onBestsellerClickListener;
    public interface OnBestsellerClickListener {
        void onBestsellerClicked(final int position);
    }
}