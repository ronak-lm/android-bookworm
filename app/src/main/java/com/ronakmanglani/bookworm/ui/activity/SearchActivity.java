package com.ronakmanglani.bookworm.ui.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.ui.fragment.BookFragment;
import com.ronakmanglani.bookworm.ui.fragment.SearchFragment;
import com.ronakmanglani.bookworm.model.Book;
import com.ronakmanglani.bookworm.util.DimenUtil;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Perform search if query is passed as an argument
        String searchQuery = getIntent().getStringExtra(BookWormApp.KEY_QUERY);
        if (searchQuery != null) {
            SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
            searchFragment.performSearchFor(searchQuery);
        }

        // Lock orientation and display detail fragment for tablets
        if (DimenUtil.isTablet() && savedInstanceState == null) {
            loadDetailFragmentWith(null);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void loadDetailFragmentWith(Book book) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putParcelable(BookWormApp.KEY_BOOK, book);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, fragment).commit();
    }
}
