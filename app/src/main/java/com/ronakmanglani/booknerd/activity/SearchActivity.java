package com.ronakmanglani.booknerd.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.fragment.BookFragment;
import com.ronakmanglani.booknerd.fragment.SearchFragment;
import com.ronakmanglani.booknerd.model.Book;
import com.ronakmanglani.booknerd.util.DimenUtil;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Perform search if query is passed as an argument
        String searchQuery = getIntent().getStringExtra(BookNerdApp.KEY_QUERY);
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
        args.putParcelable(BookNerdApp.KEY_BOOK, book);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, fragment).commit();
    }
}
