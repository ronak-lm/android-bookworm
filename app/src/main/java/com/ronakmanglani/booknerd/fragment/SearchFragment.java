package com.ronakmanglani.booknerd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.activity.BookActivity;
import com.ronakmanglani.booknerd.adapter.SearchAdapter;
import com.ronakmanglani.booknerd.adapter.SearchAdapter.OnBookClickListener;
import com.ronakmanglani.booknerd.model.Book;
import com.ronakmanglani.booknerd.util.ApiUtil;
import com.ronakmanglani.booknerd.util.DimenUtil;
import com.ronakmanglani.booknerd.util.VolleySingleton;
import com.ronakmanglani.booknerd.view.PaddingDecorationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements OnBookClickListener {

    private Unbinder unbinder;

    private int currentState;
    private String searchQuery;
    private SearchAdapter adapter;

    @BindView(R.id.toolbar)             Toolbar toolbar;
    @BindView(R.id.search_bar)          EditText searchBar;
    @BindView(R.id.error_message)       View errorMessage;
    @BindView(R.id.progress_circle)     View progressCircle;
    @BindView(R.id.no_results)          View noResults;
    @BindView(R.id.search_list)         RecyclerView searchList;

    // Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, v);

        // Setup toolbar
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.action_home));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        // Setup search bar
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String query = searchBar.getText().toString().trim();
                    if (query.length() > 0) {
                        // Set query string
                        searchQuery = query;
                        // Toggle visibility
                        searchList.setVisibility(View.GONE);
                        errorMessage.setVisibility(View.GONE);
                        noResults.setVisibility(View.GONE);
                        progressCircle.setVisibility(View.VISIBLE);
                        // Download list
                        adapter = null;
                        searchBooksList();
                        return true;
                    }
                }
                return false;
            }
        });

        // Setup RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), DimenUtil.getNumberOfColumns(R.dimen.book_card_width, 1));
        searchList.addItemDecoration(new PaddingDecorationView(getContext(), R.dimen.recycler_item_padding));
        searchList.setHasFixedSize(true);
        searchList.setLayoutManager(layoutManager);

        // Restore state
        if (savedInstanceState != null && savedInstanceState.containsKey(BookNerdApp.KEY_STATE)) {
            currentState = savedInstanceState.getInt(BookNerdApp.KEY_STATE);
            // Data had already been loaded: Display the data again
            if (currentState == BookNerdApp.STATE_LOADED) {
                searchQuery = savedInstanceState.getString(BookNerdApp.KEY_QUERY);
                ArrayList<Book> booksList = savedInstanceState.getParcelableArrayList(BookNerdApp.KEY_SEARCH);
                adapter = new SearchAdapter(this);
                adapter.setList(booksList);
                searchList.swapAdapter(adapter, true);
                onDownloadSuccessful();
            }
            // Data was still loading when fragment was lost: Load again
            else if (currentState == BookNerdApp.STATE_LOADING) {
                searchQuery = savedInstanceState.getString(BookNerdApp.KEY_QUERY);
                progressCircle.setVisibility(View.VISIBLE);
                searchBooksList();
            }
            // Data had failed to load when fragment was lost: Show error message again
            else if (currentState == BookNerdApp.STATE_FAILED) {
                onDownloadFailed();
            }
        }

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BookNerdApp.KEY_STATE, currentState);
        if (searchQuery != null) {
            outState.putString(BookNerdApp.KEY_QUERY, searchQuery);
            outState.putParcelableArrayList(BookNerdApp.KEY_SEARCH, adapter.getList());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(getClass().getName());
        unbinder.unbind();
    }

    // JSON parsing and display
    private String getStringFromObject(JSONObject object, String key) {
        try {
            return object.getString(key);
        } catch (Exception e) {
            return "";
        }
    }
    private void searchBooksList() {
        if (adapter == null) {
            adapter = new SearchAdapter(this);
            searchList.swapAdapter(adapter, true);
        }
        String urlToDownload = ApiUtil.getSearchList(searchQuery);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, urlToDownload, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("items")) {
                                JSONArray itemsArray = response.getJSONArray("items");
                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject bookObject = itemsArray.getJSONObject(i);
                                    String uniqueId = "gbid:" + bookObject.getString("id");
                                    JSONObject volumeInfo = bookObject.getJSONObject("volumeInfo");
                                    String title = volumeInfo.getString("title");

                                    String authors = "";
                                    if (volumeInfo.has("authors")) {
                                        JSONArray authorsObject = volumeInfo.getJSONArray("authors");
                                        StringBuilder sb = new StringBuilder();
                                        for (int j = 0; j < authorsObject.length(); j++) {
                                            sb.append(authorsObject.getString(j)).append(", ");
                                        }
                                        sb.delete(sb.length() - 2, sb.length());
                                        authors = sb.toString();
                                    }

                                    String publisher = getStringFromObject(volumeInfo, "publisher");
                                    String publishedDate = getStringFromObject(volumeInfo, "publishedDate");
                                    String description = getStringFromObject(volumeInfo, "description");
                                    String pageCount = getStringFromObject(volumeInfo, "pageCount");
                                    String ratingCount = getStringFromObject(volumeInfo, "ratingsCount");
                                    String averageRating = getStringFromObject(volumeInfo, "averageRating");
                                    String itemUrl = getStringFromObject(volumeInfo, "infoLink");
                                    if (averageRating.length() == 1) {
                                        averageRating = averageRating + ".0";
                                    }

                                    String imageUrl;
                                    if (volumeInfo.has("imageLinks")) {
                                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                        if (imageLinks.has("thumbnail")) {
                                            imageUrl = imageLinks.getString("thumbnail");
                                        } else if (imageLinks.has("smallThumbnail")) {
                                            imageUrl = imageLinks.getString("smallThumbnail");
                                        } else { // No image found
                                            imageUrl = "";
                                        }
                                    } else { // No images
                                        imageUrl = "";
                                    }

                                    adapter.addToList(new Book(uniqueId, title, authors, pageCount, averageRating,
                                            ratingCount, imageUrl, publisher, publishedDate, description, itemUrl));
                                }
                            }

                            onDownloadSuccessful();

                        } catch (Exception e) {
                            // Parsing error
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
        request.setTag(getClass().getName());
        VolleySingleton.getInstance().requestQueue.add(request);

        currentState = BookNerdApp.STATE_LOADING;
    }
    private void onDownloadSuccessful() {
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        if (adapter.getList().size() == 0) {
            searchList.setVisibility(View.GONE);
            noResults.setVisibility(View.VISIBLE);
        } else {
            noResults.setVisibility(View.GONE);
            searchList.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }

        currentState = BookNerdApp.STATE_LOADED;
    }
    private void onDownloadFailed() {
        progressCircle.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
        searchList.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);

        currentState = BookNerdApp.STATE_FAILED;
    }

    // Click events
    @OnClick(R.id.try_again)
    public void onTryAgainClicked() {
        // Toggle Visibility
        noResults.setVisibility(View.GONE);
        searchList.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
        // Try to download the data again
        adapter = null;
        searchBooksList();
    }
    @Override
    public void onBookClicked(int position) {
        Intent intent = new Intent(getContext(), BookActivity.class);
        intent.putExtra(BookNerdApp.KEY_BOOK, adapter.getList().get(position));
        startActivity(intent);
    }
}
