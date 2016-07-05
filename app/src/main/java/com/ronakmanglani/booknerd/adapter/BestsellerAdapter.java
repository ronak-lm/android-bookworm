package com.ronakmanglani.booknerd.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.model.BestsellerBook;
import com.ronakmanglani.booknerd.util.VolleySingleton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BestsellerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // ArrayList of books to display
    private ArrayList<BestsellerBook> bestsellerBooks;

    // Constructor
    public BestsellerAdapter(OnBestsellerClickListener onBestsellerClickListener) {
        this.bestsellerBooks = new ArrayList<>();
        this.onBestsellerClickListener = onBestsellerClickListener;
    }
    public BestsellerAdapter(ArrayList<BestsellerBook> bestsellerBooks, OnBestsellerClickListener onBestsellerClickListener) {
        this.bestsellerBooks = bestsellerBooks;
        this.onBestsellerClickListener = onBestsellerClickListener;
    }

    // Helper methods
    public void addToList(BestsellerBook book) {
        bestsellerBooks.add(book);
    }
    public ArrayList<BestsellerBook> getList() {
        return bestsellerBooks;
    }

    // RecyclerView methods
    @Override
    public int getItemCount() {
        return bestsellerBooks.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestseller, parent, false);
        return new BestsellerViewHolder(v, onBestsellerClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        BestsellerBook book = bestsellerBooks.get(position);
        final BestsellerViewHolder holder = (BestsellerViewHolder) viewHolder;
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.description.setText(book.getDescription());
        holder.coverImage.setImageUrl(book.getImageUrl(), VolleySingleton.getInstance().imageLoader);
    }

    // ViewHolder
    public class BestsellerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bestseller_card)         CardView cardView;
        @BindView(R.id.bestseller_title)        TextView title;
        @BindView(R.id.bestseller_author)       TextView author;
        @BindView(R.id.bestseller_description)  TextView description;
        @BindView(R.id.bestseller_image)        NetworkImageView coverImage;

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