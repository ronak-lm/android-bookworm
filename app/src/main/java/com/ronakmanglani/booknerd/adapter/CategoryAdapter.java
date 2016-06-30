package com.ronakmanglani.booknerd.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.model.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Constructor
    public CategoryAdapter(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    // RecyclerView methods
    @Override
    public int getItemCount() {
        return Category.getCategoryList().size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(v, onCategoryClickListener);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CategoryViewHolder)viewHolder).categoryName.setText(Category.getCategoryList().get(position).getDisplayName());
    }

    // ViewHolder
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_item) View categoryItem;
        @BindView(R.id.category_name) TextView categoryName;

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

    // Click listener interface
    private OnCategoryClickListener onCategoryClickListener;
    public interface OnCategoryClickListener {
        void onCategoryClicked(final int position);
    }
}