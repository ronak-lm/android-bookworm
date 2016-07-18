package com.ronakmanglani.bookworm.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.model.Book;
import com.ronakmanglani.bookworm.ui.fragment.BestsellerFragment;
import com.ronakmanglani.bookworm.ui.fragment.BookFragment;
import com.ronakmanglani.bookworm.util.DimenUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (DimenUtil.isTablet() && savedInstanceState == null) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onBackPressed() {
        BestsellerFragment fragment = (BestsellerFragment) getSupportFragmentManager().findFragmentByTag(BookWormApp.TAG_BESTSELLER);
        if (fragment != null && fragment.canGoBack()) {
            fragment.navigateToCategories();
            if (DimenUtil.isTablet()) {
                loadDetailFragmentWith(null, true);
            }
        } else {
            super.onBackPressed();
        }
    }

    public void loadDetailFragmentWith(Book book, boolean isMessageVisible) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putParcelable(BookWormApp.KEY_BOOK, book);
        args.putBoolean(BookWormApp.KEY_VISIBILITY, isMessageVisible);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, fragment).commit();
    }
}
