package com.ronakmanglani.booknerd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.ui.fragment.BookFragment;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        if (savedInstanceState == null) {
            BookFragment fragment = new BookFragment();

            Bundle args = new Bundle();
            args.putParcelable(BookNerdApp.KEY_BOOK, getIntent().getParcelableExtra(BookNerdApp.KEY_BOOK));
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.book_detail_container, fragment).commit();
        }
    }
}
