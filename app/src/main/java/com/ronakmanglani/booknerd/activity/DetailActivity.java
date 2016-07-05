package com.ronakmanglani.booknerd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            DetailFragment fragment = new DetailFragment();

            Bundle args = new Bundle();
            args.putString(BookNerdApp.ISBN_NUMBER, getIntent().getStringExtra(BookNerdApp.ISBN_NUMBER));
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.book_detail_container, fragment).commit();
        }
    }
}
