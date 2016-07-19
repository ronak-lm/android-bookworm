package com.ronakmanglani.bookworm.ui.adapter.listener;

import android.view.View;

public interface OnBookClickListener {
    void onBookCardClicked(final int position);
    void onBookMenuClicked(final int position, View view);
}
