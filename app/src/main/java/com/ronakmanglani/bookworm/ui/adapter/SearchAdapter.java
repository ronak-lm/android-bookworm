package com.ronakmanglani.bookworm.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.model.Book;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnBookClickListener;
import com.ronakmanglani.bookworm.ui.adapter.viewholder.BookViewHolder;
import com.ronakmanglani.bookworm.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Book> booksList;
    private OnBookClickListener onBookClickListener;

    // Constructor
    public SearchAdapter(OnBookClickListener onBookClickListener) {
        this.context = BookWormApp.getAppContext();
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
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_list, parent, false);
        return new BookViewHolder(v, onBookClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Book book = booksList.get(position);
        final BookViewHolder holder = (BookViewHolder) viewHolder;
        // Cover image
        if (!StringUtil.isNullOrEmpty(book.getImageUrl())) {
            Picasso.with(context)
                    .load(book.getImageUrl())
                    .fit()
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