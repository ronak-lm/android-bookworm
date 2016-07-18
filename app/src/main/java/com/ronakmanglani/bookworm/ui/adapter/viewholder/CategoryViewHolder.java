package com.ronakmanglani.bookworm.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnCategoryClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.category_item) public View categoryItem;
    @BindView(R.id.category_icon) public ImageView categoryIcon;
    @BindView(R.id.category_name) public TextView categoryName;

    public CategoryViewHolder(final ViewGroup itemView, final OnCategoryClickListener onCategoryClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        categoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryClickListener.onCategoryClicked(getAdapterPosition());
            }
        });
    }
}