package com.ronakmanglani.bookworm.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.data.BookColumns;
import com.ronakmanglani.bookworm.ui.activity.BarcodeActivity;
import com.ronakmanglani.bookworm.ui.activity.MainActivity;
import com.ronakmanglani.bookworm.ui.activity.SearchActivity;
import com.ronakmanglani.bookworm.util.DimenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DrawerFragment extends Fragment implements OnMenuItemClickListener, OnNavigationItemSelectedListener {

    private static final int CAMERA_REQUEST_CODE = 42;

    private Unbinder unbinder;

    @BindView(R.id.toolbar)         Toolbar toolbar;
    @BindView(R.id.drawer_layout)   DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;

    // Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        unbinder = ButterKnife.bind(this, v);

        // Setup toolbar
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_drawer);
        toolbar.setOnMenuItemClickListener(this);

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
        if (savedInstanceState == null) {
            toolbar.setTitle(R.string.drawer_bestseller);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new BestsellerFragment(), BookWormApp.TAG_BESTSELLER)
                    .commit();
            if (DimenUtil.isTablet()) {
                ((MainActivity) getActivity()).loadDetailFragmentWith(null, true);
            }
        } else if (savedInstanceState.containsKey(BookWormApp.KEY_TITLE)) {
            toolbar.setTitle(savedInstanceState.getString(BookWormApp.KEY_TITLE));
        }

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BookWormApp.KEY_TITLE, toolbar.getTitle().toString());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // Handle permission requests for Marshmallow
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(getContext(), BarcodeActivity.class));
                } else {
                    Toast.makeText(getContext(), R.string.camera_permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // Toolbar/Navigation drawer item select
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_twitter:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Ronak_LM"));
                startActivity(browserIntent);
                return true;

            case R.id.action_email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "ronak_lm@outlook.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                try {
                    startActivity(Intent.createChooser(emailIntent, getString(R.string.action_email_using)));
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.action_email_error, Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, null);
                builder.setView(view);
                builder.show();
                return true;

            default:
                return false;

        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        switch (id) {

            case R.id.drawer_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                return false;

            case R.id.drawer_barcode:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                   requestPermissions(new String[] { Manifest.permission.CAMERA }, CAMERA_REQUEST_CODE);
                } else {
                    startActivity(new Intent(getContext(), BarcodeActivity.class));
                }
                return false;

            case R.id.drawer_bestseller:
                toolbar.setTitle(R.string.drawer_bestseller);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new BestsellerFragment(), BookWormApp.TAG_BESTSELLER)
                        .commit();
                if (DimenUtil.isTablet()) {
                    ((MainActivity) getActivity()).loadDetailFragmentWith(null, true);
                }
                return true;

            case R.id.drawer_to_read:
                toolbar.setTitle(R.string.drawer_to_read);
                ListFragment toReadFragment = new ListFragment();
                Bundle args1 = new Bundle();
                args1.putInt(BookWormApp.KEY_SHELF, BookColumns.SHELF_TO_READ);
                toReadFragment.setArguments(args1);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, toReadFragment)
                        .commit();
                return true;

            case R.id.drawer_reading:
                toolbar.setTitle(R.string.drawer_reading);
                ListFragment readingFragment = new ListFragment();
                Bundle args2 = new Bundle();
                args2.putInt(BookWormApp.KEY_SHELF, BookColumns.SHELF_READING);
                readingFragment.setArguments(args2);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, readingFragment)
                        .commit();
                return true;

            case R.id.drawer_finished:
                toolbar.setTitle(R.string.drawer_finished);
                ListFragment finishedFragment = new ListFragment();
                Bundle args3 = new Bundle();
                args3.putInt(BookWormApp.KEY_SHELF, BookColumns.SHELF_FINISHED);
                finishedFragment.setArguments(args3);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, finishedFragment)
                        .commit();
                return true;

            case R.id.drawer_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, null);
                builder.setView(view);
                builder.show();
                break;
        }
        return false;
    }
}
