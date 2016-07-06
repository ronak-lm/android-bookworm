package com.ronakmanglani.booknerd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.model.Detail;
import com.ronakmanglani.booknerd.util.ApiUtil;
import com.ronakmanglani.booknerd.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailFragment extends Fragment {

    private Unbinder unbinder;

    private int currentState;
    private String isbnNumber;
    private Detail bookDetail;

    // Main views
    @BindView(R.id.toolbar)                 Toolbar toolbar;
    @BindView(R.id.progress_circle)         View progressCircle;
    @BindView(R.id.error_message)           View errorMessage;
    @BindView(R.id.book_detail_holder)      NestedScrollView bookDetailHolder;

    // Detail views
    @BindView(R.id.book_cover)              NetworkImageView bookCover;
    @BindView(R.id.book_title)              TextView bookTitle;
    @BindView(R.id.book_subtitle)           TextView bookSubtitle;
    @BindView(R.id.book_rating_holder)      View bookRatingHolder;
    @BindView(R.id.book_rating)             TextView bookRating;
    @BindView(R.id.book_vote_count)         TextView bookVoteCount;
    @BindView(R.id.book_publisher_holder)   View bookPublisherHolder;
    @BindView(R.id.publication_name)        TextView bookPublisher;
    @BindView(R.id.publication_date)        TextView bookDate;
    @BindView(R.id.book_description_holder) View bookDescriptionHolder;
    @BindView(R.id.book_description)        TextView bookDescription;

    // Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, v);

        // Setup toolbar
        toolbar.setTitle(R.string.loading);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.action_home));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        // Load book details
        isbnNumber = getArguments().getString(BookNerdApp.ISBN_NUMBER);
        if (savedInstanceState != null && savedInstanceState.containsKey(BookNerdApp.CURRENT_STATE)) {
            currentState = savedInstanceState.getInt(BookNerdApp.CURRENT_STATE);
            if (currentState == BookNerdApp.STATE_LOADED) {
                bookDetail = savedInstanceState.getParcelable(BookNerdApp.BOOK_DETAIL);
                onDownloadSuccessful();
            } else if (currentState == BookNerdApp.STATE_LOADING) {
                downloadBookDetails();
            } else if (currentState == BookNerdApp.STATE_FAILED) {
                onDownloadFailed();
            }
        } else {
            downloadBookDetails();
        }

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BookNerdApp.CURRENT_STATE, currentState);
        if (bookDetail != null) {
            outState.putParcelable(BookNerdApp.BOOK_DETAIL, bookDetail);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
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
    private void downloadBookDetails() {
        String url = ApiUtil.getBookDetails(isbnNumber);
        JsonObjectRequest request = new JsonObjectRequest(
                Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject bookObject = response.getJSONArray("items").getJSONObject(0);
                            String volumeId = bookObject.getString("id");
                            JSONObject volumeInfo = bookObject.getJSONObject("volumeInfo");
                            String title = volumeInfo.getString("title");

                            JSONArray authorsObject = volumeInfo.getJSONArray("authors");
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < authorsObject.length(); i++) {
                                sb.append(authorsObject.getString(i)).append(", ");
                            }
                            sb.delete(sb.length() - 2, sb.length());
                            String authors = sb.toString();

                            String publisher = getStringFromObject(volumeInfo, "publisher");
                            String publishedDate = getStringFromObject(volumeInfo, "publishedDate");
                            String description = getStringFromObject(volumeInfo, "description");
                            String pageCount = getStringFromObject(volumeInfo, "pageCount");
                            String ratingCount = getStringFromObject(volumeInfo, "ratingsCount");
                            String averageRating = getStringFromObject(volumeInfo, "averageRating");
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

                            bookDetail = new Detail(volumeId, title, authors, pageCount,
                                    averageRating, ratingCount, imageUrl, publisher, publishedDate, description);

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

        currentState = BookNerdApp.STATE_LOADING;
    }
    private void onDownloadSuccessful() {
        // Toggle visibility
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        bookDetailHolder.setVisibility(View.VISIBLE);

        // Cover Image
        if (bookDetail.getImageUrl().length() == 0) {
            bookCover.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.default_cover));
        } else {
            bookCover.setImageUrl(bookDetail.getImageUrl(), VolleySingleton.getInstance().imageLoader);
        }

        // Title and Subtitle
        toolbar.setTitle(bookDetail.getTitle());
        bookTitle.setText(bookDetail.getTitle());
        if (bookDetail.getAuthors().length() == 0 && bookDetail.getPageCount().length() == 0) {
            bookSubtitle.setVisibility(View.GONE);
        } else if (bookDetail.getPageCount().length() == 0) {
            bookSubtitle.setText(getString(R.string.detail_subtitle_by, bookDetail.getAuthors()));
        } else if (bookDetail.getAuthors().length() == 0) {
            bookSubtitle.setText(getString(R.string.detail_subtitle_page, bookDetail.getPageCount()));
        } else {
            bookSubtitle.setText(getString(R.string.detail_subtitle, bookDetail.getAuthors(), bookDetail.getPageCount()));
        }

        // Rating
        if (bookRating.length() == 0 || bookVoteCount.length() == 0) {
            bookRatingHolder.setVisibility(View.GONE);
        } else {
            bookRating.setText(bookDetail.getAverageRating());
            bookVoteCount.setText(getString(R.string.detail_rating, bookDetail.getRatingCount()));
        }

        // Publication info
        if (bookDetail.getPublisher().length() == 0 && bookDetail.getPublishDate().length() == 0) {
            bookPublisherHolder.setVisibility(View.GONE);
        } else if (bookDetail.getPublisher().length() == 0) {
            bookPublisher.setVisibility(View.GONE);
            bookDate.setText(getString(R.string.detail_publication_date, bookDetail.getPublishDate()));
        } else if (bookDetail.getPublishDate().length() == 0) {
            bookPublisher.setText(getString(R.string.detail_publication_name, bookDetail.getPublisher()));
            bookDate.setVisibility(View.GONE);
        } else {
            bookPublisher.setText(getString(R.string.detail_publication_name, bookDetail.getPublisher()));
            bookDate.setText(getString(R.string.detail_publication_date, bookDetail.getPublishDate()));
        }

        // Book description
        if (bookDetail.getDescription().length() == 0) {
            bookDescriptionHolder.setVisibility(View.GONE);
        } else {
            bookDescription.setText(bookDetail.getDescription());
        }

        // Update flag
        currentState = BookNerdApp.STATE_LOADED;
    }
    private void onDownloadFailed() {
        progressCircle.setVisibility(View.GONE);
        bookDetailHolder.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.unable_to_load);

        currentState = BookNerdApp.STATE_FAILED;
    }

    // Click Events
    @OnClick(R.id.try_again)
    public void onTryAgainClicked() {
        toolbar.setTitle(R.string.loading);
        errorMessage.setVisibility(View.GONE);
        bookDetailHolder.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
        downloadBookDetails();
    }
}
