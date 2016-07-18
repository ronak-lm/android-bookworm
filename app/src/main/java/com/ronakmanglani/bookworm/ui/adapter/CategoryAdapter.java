package com.ronakmanglani.bookworm.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.model.Category;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnCategoryClickListener;
import com.ronakmanglani.bookworm.ui.adapter.viewholder.CategoryViewHolder;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnCategoryClickListener onCategoryClickListener;

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
        CategoryViewHolder holder = (CategoryViewHolder) viewHolder;
        Category category = Category.getCategoryList().get(position);
        holder.categoryIcon.setImageResource(category.getIconId());
        holder.categoryName.setText(category.getDisplayName());
    }
}