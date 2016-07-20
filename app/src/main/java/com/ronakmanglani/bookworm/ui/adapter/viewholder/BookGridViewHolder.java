package com.ronakmanglani.bookworm.ui.adapter.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnBookClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookGridViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.book_card)           public CardView cardView;
    @BindView(R.id.book_cover)          public NetworkImageView coverImage;
    @BindView(R.id.book_title)          public TextView title;
    @BindView(R.id.book_author)         public TextView author;
    @BindView(R.id.book_menu)           public View bookMenu;

    public BookGridViewHolder(final ViewGroup itemView, final OnBookClickListener onBookClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBookClickListener.onBookCardClicked(getAdapterPosition());
            }
        });
        bookMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookClickListener.onBookMenuClicked(getAdapterPosition(), bookMenu);
            }
        });
    }
}