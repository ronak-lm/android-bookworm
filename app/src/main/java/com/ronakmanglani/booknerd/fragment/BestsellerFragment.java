package com.ronakmanglani.booknerd.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.adapter.BestsellerAdapter;
import com.ronakmanglani.booknerd.model.BestsellerBook;
import com.ronakmanglani.booknerd.util.ApiUtil;
import com.ronakmanglani.booknerd.util.DimenUtil;
import com.ronakmanglani.booknerd.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ronakmanglani.booknerd.adapter.BestsellerAdapter.*;

public class BestsellerFragment extends Fragment implements OnBestsellerClickListener {

    private Unbinder unbinder;

    private String listName;
    private BestsellerAdapter adapter;
    private GridLayoutManager layoutManager;

    @BindView(R.id.error_message)       View errorMessage;
    @BindView(R.id.progress_circle)     View progressCircle;
    @BindView(R.id.bestseller_list)     RecyclerView recyclerView;

    // Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bestseller, container, false);

        // Initialization
        unbinder = ButterKnife.bind(this, v);
        listName = getArguments().getString(BookNerdApp.LIST_NAME);

        // Setup RecyclerView
        adapter = new BestsellerAdapter(this);
        layoutManager = new GridLayoutManager(getContext(), DimenUtil.getNumberOfColumns(R.dimen.bestseller_card_width, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Download bestseller list
        downloadBestsellerList();

        return v;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
        unbinder.unbind();
    }

    // JSON parsing and display
    private void downloadBestsellerList() {
        if (adapter == null) {
            adapter = new BestsellerAdapter(this);
            recyclerView.setAdapter(adapter);
        }
        String urlToDownload = ApiUtil.getBestsellerList(listName);
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, urlToDownload, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray books = response.getJSONObject("results").getJSONArray("books");
                            for (int i = 0; i < books.length(); i++) {
                                JSONObject bookObject = books.getJSONObject(i);
                                String isbn10 = bookObject.getString("primary_isbn10");
                                String isbn13 = bookObject.getString("primary_isbn13");
                                String description = bookObject.getString("description");
                                String title = bookObject.getString("title");
                                String author = bookObject.getString("author");
                                String imageUrl = bookObject.getString("book_image");

                                BestsellerBook book = new BestsellerBook(title, author, description, imageUrl, isbn10, isbn13);
                                adapter.addToList(book);
                            }

                            onDownloadSuccessful();

                        } catch (Exception e) {
                            // JSON parsing error
                            onDownloadFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network error
                        onDownloadFailed();
                    }
                });
        request.setTag(this.getClass().getName());
        VolleySingleton.getInstance().requestQueue.add(request);
    }
    private void onDownloadSuccessful() {
        errorMessage.setVisibility(View.GONE);
        progressCircle.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }
    private void onDownloadFailed() {
        errorMessage.setVisibility(View.VISIBLE);
        progressCircle.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    // Click events
    @OnClick(R.id.try_again)
    public void onTryAgainClicked() {
        // Hide all views
        errorMessage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        // Show progress circle
        progressCircle.setVisibility(View.VISIBLE);
        // Try to download the data again
        adapter = null;
        downloadBestsellerList();
    }
    @Override
    public void onBestsellerClicked(int position) {
        // TODO: Navigate to details page
    }
}

