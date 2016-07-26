package com.ronakmanglani.bookworm.ui.fragment;

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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ronakmanglani.bookworm.BookWormApp;
import com.ronakmanglani.bookworm.R;
import com.ronakmanglani.bookworm.api.ApiHelper;
import com.ronakmanglani.bookworm.api.VolleySingleton;
import com.ronakmanglani.bookworm.model.Book;
import com.ronakmanglani.bookworm.ui.activity.BookActivity;
import com.ronakmanglani.bookworm.ui.activity.SearchActivity;
import com.ronakmanglani.bookworm.ui.adapter.SearchAdapter;
import com.ronakmanglani.bookworm.ui.adapter.listener.OnBookClickListener;
import com.ronakmanglani.bookworm.ui.view.PaddingDecorationView;
import com.ronakmanglani.bookworm.util.DatabaseUtil;
import com.ronakmanglani.bookworm.util.DimenUtil;

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
    private int startIndex;
    private int totalItems;
    private String searchQuery;
    private SearchAdapter adapter;
    private GridLayoutManager layoutManager;

    @BindView(R.id.toolbar)             Toolbar toolbar;
    @BindView(R.id.search_bar)          EditText searchBar;
    @BindView(R.id.search_list)         RecyclerView searchList;
    @BindView(R.id.ad_view)             AdView adView;
    @BindView(R.id.no_results)          View noResults;
    @BindView(R.id.error_message)       View errorMessage;
    @BindView(R.id.progress_circle)     View progressCircle;
    @BindView(R.id.loading_more)        View loadingMore;

    // Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, v);
        startIndex = 0;
        totalItems = 0;

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
                        // Initialize
                        startIndex = 0;
                        totalItems = 0;
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
        layoutManager = new GridLayoutManager(getContext(), DimenUtil.getNumberOfColumns(R.dimen.book_list_card_width, 1));
        searchList.addItemDecoration(new PaddingDecorationView(getContext(), R.dimen.recycler_item_padding));
        searchList.setHasFixedSize(true);
        searchList.setLayoutManager(layoutManager);
        searchList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // Load more if RecyclerView has reached the end and isn't already loading
                if (adapter.getList().size() > 0 &&
                        layoutManager.findLastVisibleItemPosition() == adapter.getList().size() - 1 &&
                        currentState != BookWormApp.STATE_LOADING &&
                        currentState != BookWormApp.STATE_LOCKED) {
                    if (startIndex < totalItems) {
                        loadingMore.setVisibility(View.VISIBLE);
                        searchBooksList();
                    }
                }
            }
        });

        // Restore state
        if (savedInstanceState != null && savedInstanceState.containsKey(BookWormApp.KEY_STATE)) {
            searchQuery = savedInstanceState.getString(BookWormApp.KEY_QUERY);
            startIndex = savedInstanceState.getInt(BookWormApp.KEY_INDEX, 0);
            totalItems = savedInstanceState.getInt(BookWormApp.KEY_TOTAL, 0);
            currentState = savedInstanceState.getInt(BookWormApp.KEY_STATE);
            // Data had already been loaded: Display the data again
            if (currentState == BookWormApp.STATE_LOADED || currentState == BookWormApp.STATE_LOCKED ||
                    (currentState == BookWormApp.STATE_LOADING && savedInstanceState.containsKey(BookWormApp.KEY_SEARCH))) {
                ArrayList<Book> booksList = savedInstanceState.getParcelableArrayList(BookWormApp.KEY_SEARCH);
                adapter = new SearchAdapter(this);
                adapter.setList(booksList);
                searchList.swapAdapter(adapter, true);
                onDownloadSuccessful();
                if (currentState == BookWormApp.STATE_LOADING) {
                    loadingMore.setVisibility(View.VISIBLE);
                    searchBooksList();
                }
            }
            // Data was still loading when fragment was lost: Load again
            else if (currentState == BookWormApp.STATE_LOADING) {
                progressCircle.setVisibility(View.VISIBLE);
                searchBooksList();
            }
            // Data had failed to load when fragment was lost: Show error message again
            else if (currentState == BookWormApp.STATE_FAILED) {
                onDownloadFailed();
            }
        }

        // Load Ad
        if (!DimenUtil.isTablet()) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(getString(R.string.device_moto_g4_id))
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            adView.loadAd(adRequest);
        } else {
            adView.setVisibility(View.GONE);
        }

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BookWormApp.KEY_STATE, currentState);
        outState.putInt(BookWormApp.KEY_INDEX, startIndex);
        outState.putInt(BookWormApp.KEY_TOTAL, totalItems);
        outState.putString(BookWormApp.KEY_QUERY, searchQuery);
        if (adapter != null) {
            outState.putParcelableArrayList(BookWormApp.KEY_SEARCH, adapter.getList());
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
        String urlToDownload = ApiHelper.getSearchListUrl(searchQuery, startIndex);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, urlToDownload, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("items")) {
                                totalItems = response.getInt("totalItems");
                                JSONArray itemsArray = response.getJSONArray("items");
                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject bookObject = itemsArray.getJSONObject(i);
                                    String uniqueId = "gbid_" + bookObject.getString("id");
                                    JSONObject volumeInfo = bookObject.getJSONObject("volumeInfo");

                                    // Title, subtitle and author
                                    String title = volumeInfo.getString("title");
                                    String subtitle = getStringFromObject(volumeInfo, "subtitle");
                                    String authors = "";
                                    if (volumeInfo.has("authors")) {
                                        JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                                        StringBuilder sb1 = new StringBuilder();
                                        for (int j = 0; j < authorsArray.length(); j++) {
                                            sb1.append(authorsArray.getString(j)).append(", ");
                                        }
                                        sb1.delete(sb1.length() - 2, sb1.length());
                                        authors = sb1.toString();
                                    }

                                    // ISBN numbers
                                    String isbn10 = "";
                                    String isbn13 = "";
                                    if (volumeInfo.has("industryIdentifiers")) {
                                        JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                                        for (int j = 0; j < identifiers.length(); j++) {
                                            JSONObject identifier = identifiers.getJSONObject(j);
                                            if (identifier.getString("type").equals("ISBN_13")) {
                                                isbn13 = identifier.getString("identifier");
                                            }
                                            if (identifier.getString("type").equals("ISBN_10")) {
                                                isbn10 = identifier.getString("identifier");
                                            }
                                        }
                                    }

                                    // Book's cover image
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

                                    // Other info
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

                                    adapter.addToList(new Book(uniqueId, isbn10, isbn13, title, subtitle,
                                            authors, pageCount, averageRating, ratingCount, imageUrl,
                                            publisher, publishedDate, description, itemUrl));
                                }
                            }

                            startIndex += 11;
                            onDownloadSuccessful();

                        } catch (Exception e) {
                            // Parsing error
                            onDownloadFailed();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network error
                        onDownloadFailed();
                        error.printStackTrace();
                    }
                });
        request.setTag(getClass().getName());
        VolleySingleton.getInstance().requestQueue.add(request);

        currentState = BookWormApp.STATE_LOADING;
    }
    private void onDownloadSuccessful() {
        loadingMore.setVisibility(View.GONE);
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        if (adapter.getList().size() == 0) {
            searchList.setVisibility(View.GONE);
            noResults.setVisibility(View.VISIBLE);
        } else {
            noResults.setVisibility(View.GONE);
            searchList.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();

            if (DimenUtil.isTablet() && startIndex == 10) {
                ((SearchActivity) getActivity()).loadDetailFragmentWith(adapter.getList().get(0));
            }
        }

        currentState = BookWormApp.STATE_LOADED;
    }
    private void onDownloadFailed() {
        progressCircle.setVisibility(View.GONE);
        loadingMore.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
        if (startIndex == 0) {
            searchList.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
            currentState = BookWormApp.STATE_FAILED;
        } else {
            errorMessage.setVisibility(View.GONE);
            searchList.setVisibility(View.VISIBLE);
            currentState = BookWormApp.STATE_LOCKED;
        }
    }

    // Helper methods
    public void performSearchFor(String searchQuery) {
        startIndex = 0;
        totalItems = 0;
        this.searchQuery = searchQuery;
        searchBar.setText(searchQuery);

        searchList.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);

        adapter = null;
        searchBooksList();
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
    public void onBookCardClicked(int position) {
        Book book = adapter.getList().get(position);
        if (DimenUtil.isTablet()) {
            ((SearchActivity) getActivity()).loadDetailFragmentWith(book);
        } else {
            Intent intent = new Intent(getContext(), BookActivity.class);
            intent.putExtra(BookWormApp.KEY_BOOK, book);
            startActivity(intent);
        }

    }
    @Override
    public void onBookMenuClicked(int position, View view) {
        Book book = adapter.getList().get(position);
        int currentShelf = DatabaseUtil.getCurrentShelf(book.getUniqueId());
        DatabaseUtil.getPopupMenu(getActivity(), book, currentShelf, view).show();
    }
}
