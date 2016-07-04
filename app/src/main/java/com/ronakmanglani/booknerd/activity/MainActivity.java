package com.ronakmanglani.booknerd.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

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
        BestsellerFragment fragment = (BestsellerFragment) getSupportFragmentManager().findFragmentByTag("bestseller_fragment");
        if (fragment != null && fragment.canGoBack) {
            fragment.navigateToCategories();
        } else {
            super.onBackPressed();
        }
    }
}
