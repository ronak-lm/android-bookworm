package com.ronakmanglani.booknerd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.adapter.CategoryAdapter;
import com.ronakmanglani.booknerd.adapter.CategoryAdapter.OnCategoryClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryFragment extends Fragment implements OnCategoryClickListener {

    private Unbinder unbinder;
    @BindView(R.id.category_list) RecyclerView categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, v);

        // Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        CategoryAdapter adapter = new CategoryAdapter(this);
        categoryList.setHasFixedSize(true);
        categoryList.setLayoutManager(layoutManager);
        categoryList.setAdapter(adapter);

        return v;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCategoryClicked(int position) {

    }
}
