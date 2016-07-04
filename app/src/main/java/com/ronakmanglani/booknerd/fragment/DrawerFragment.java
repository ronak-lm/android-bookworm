package com.ronakmanglani.booknerd.fragment;

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
        if (savedInstanceState != null && savedInstanceState.containsKey(BookNerdApp.TOOLBAR_TITLE)) {
            toolbar.setTitle(savedInstanceState.getString(BookNerdApp.TOOLBAR_TITLE));
        }

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BookNerdApp.TOOLBAR_TITLE, toolbar.getTitle().toString());
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
        setSelectedDrawerItem(item);
        return true;
    }
    private void setSelectedDrawerItem(MenuItem item) {
        toolbar.setTitle(item.getTitle());
        item.setChecked(true);
        int id = item.getItemId();
        if (id == R.id.drawer_bestseller) {
            BestsellerFragment fragment = new BestsellerFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment, "bestseller_fragment");
            transaction.commit();
        }
    }
}
