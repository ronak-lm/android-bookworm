package com.ronakmanglani.booknerd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.activity.BarcodeActivity;
import com.ronakmanglani.booknerd.activity.MainActivity;
import com.ronakmanglani.booknerd.activity.SearchActivity;
import com.ronakmanglani.booknerd.util.DimenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DrawerFragment extends Fragment implements OnNavigationItemSelectedListener {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)         Toolbar toolbar;
    @BindView(R.id.drawer_layout)   DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;

    // Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        unbinder = ButterKnife.bind(this, v);
        toolbar.setTitle(R.string.app_name);

        // Setup navigation drawer
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle.syncState();

        // Restore toolbar title
        if (savedInstanceState != null && savedInstanceState.containsKey(BookNerdApp.KEY_TITLE)) {
            toolbar.setTitle(savedInstanceState.getString(BookNerdApp.KEY_TITLE));
        }

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BookNerdApp.KEY_TITLE, toolbar.getTitle().toString());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // Navigation drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        if (id == R.id.drawer_search) {
            startActivity(new Intent(getContext(), SearchActivity.class));
            return false;
        } else if (id == R.id.drawer_barcode) {
            startActivity(new Intent(getContext(), BarcodeActivity.class));
            return false;
        }
        else if (id == R.id.drawer_bestseller) {
            BestsellerFragment fragment = new BestsellerFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment, BookNerdApp.TAG_BESTSELLER)
                    .commit();
            if (DimenUtil.isTablet()) {
                ((MainActivity) getActivity()).loadDetailFragmentWith(null, true);
            }
            return true;
        }
        return false;
    }
}
