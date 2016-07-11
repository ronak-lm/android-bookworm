package com.ronakmanglani.booknerd.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.fragment.BestsellerFragment;
import com.ronakmanglani.booknerd.fragment.BookFragment;
import com.ronakmanglani.booknerd.model.Book;
import com.ronakmanglani.booknerd.util.DimenUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (DimenUtil.isTablet() && savedInstanceState == null) {
            loadDetailFragmentWith(null, false);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onBackPressed() {
        BestsellerFragment fragment = (BestsellerFragment) getSupportFragmentManager().findFragmentByTag(BookNerdApp.TAG_BESTSELLER);
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
        args.putParcelable(BookNerdApp.KEY_BOOK, book);
        args.putBoolean(BookNerdApp.KEY_VISIBILITY, isMessageVisible);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, fragment).commit();
    }
}
