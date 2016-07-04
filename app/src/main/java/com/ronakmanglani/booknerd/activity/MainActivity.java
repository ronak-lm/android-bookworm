package com.ronakmanglani.booknerd.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.fragment.BestsellerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        BestsellerFragment fragment = (BestsellerFragment) getSupportFragmentManager().findFragmentByTag(BookNerdApp.TAG_BESTSELLER);
        if (fragment != null && fragment.canGoBack()) {
            fragment.navigateToCategories();
        } else {
            super.onBackPressed();
        }
    }
}
