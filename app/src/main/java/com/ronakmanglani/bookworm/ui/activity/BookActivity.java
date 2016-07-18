package com.ronakmanglani.bookworm.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.ui.fragment.BookFragment;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        if (savedInstanceState == null) {
            BookFragment fragment = new BookFragment();

            Bundle args = new Bundle();
            args.putParcelable(BookWormApp.KEY_BOOK, getIntent().getParcelableExtra(BookWormApp.KEY_BOOK));
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.book_detail_container, fragment).commit();
        }
    }
}
