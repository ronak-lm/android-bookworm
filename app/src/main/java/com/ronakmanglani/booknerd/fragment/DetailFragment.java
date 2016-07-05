package com.ronakmanglani.booknerd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.model.BookDetail;
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

    private String isbnNumber;
    private BookDetail bookDetail;

    // Main views
    @BindView(R.id.toolbar)                 Toolbar toolbar;
    @BindView(R.id.progress_circle)         View progressCircle;
    @BindView(R.id.error_message)           View errorMessage;
    @BindView(R.id.book_detail_holder)      NestedScrollView bookDetailHolder;

    // Detail views
    @BindView(R.id.book_cover)              ImageView bookCover;
    @BindView(R.id.book_title)              TextView bookTitle;
    @BindView(R.id.book_subtitle)           TextView bookSubtitle;
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
        toolbar.setTitle(R.string.loading);

        isbnNumber = getArguments().getString(BookNerdApp.ISBN_NUMBER);
        downloadBookDetails();

        return v;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleySingleton.getInstance().requestQueue.cancelAll(this.getClass().getName());
        unbinder.unbind();
    }

    // JSON parsing and display
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
                            String publisher;
                            try {
                                publisher = volumeInfo.getString("publisher");
                            } catch (Exception e) {
                                publisher = "";
                            }
                            String publishedDate = volumeInfo.getString("publishedDate");
                            String description = volumeInfo.getString("description");
                            String pageCount = volumeInfo.getString("pageCount");
                            String averageRating = volumeInfo.getString("averageRating");
                            if (averageRating.length() == 1) {
                                averageRating = averageRating + ".0";
                            }
                            String ratingCount = volumeInfo.getString("ratingsCount");
                            String imageUrl;
                            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                            if (imageLinks.has("thumbnail")) {
                                imageUrl = imageLinks.getString("thumbnail");
                            } else if (imageLinks.has("smallThumbnail")) {
                                imageUrl = imageLinks.getString("smallThumbnail");
                            } else { // No image found
                                imageUrl = "";
                            }

                            bookDetail = new BookDetail(volumeId, title, authors, pageCount,
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
    }
    private void onDownloadSuccessful() {
        // Toggle visibility
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        bookDetailHolder.setVisibility(View.VISIBLE);

        // Basic info
        VolleySingleton.getInstance().imageLoader.get(bookDetail.getImageUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                bookCover.setImageBitmap(response.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        toolbar.setTitle(bookDetail.getTitle());
        bookTitle.setText(bookDetail.getTitle());
        bookSubtitle.setText(getString(R.string.detail_subtitle, bookDetail.getAuthors(), bookDetail.getPageCount()));
        bookRating.setText(bookDetail.getAverageRating());
        bookVoteCount.setText(getString(R.string.detail_rating, bookDetail.getRatingCount()));

        // Publication info
        if (bookDetail.getPublisher().length() == 0 && bookDetail.getPublishDate().length() == 0) {
            bookPublisherHolder.setVisibility(View.GONE);
        } else if (bookDetail.getPublisher().length() == 0) {
            bookPublisher.setVisibility(View.GONE);
            bookDate.setText(bookDetail.getPublishDate());
        } else if (bookDetail.getPublishDate().length() == 0) {
            bookPublisher.setText(bookDetail.getPublisher());
            bookDate.setVisibility(View.GONE);
        } else {
            bookPublisher.setText(bookDetail.getPublisher());
            bookDate.setText(bookDetail.getPublishDate());
        }

        // Book description
        if (bookDetail.getDescription().length() == 0) {
            bookDescriptionHolder.setVisibility(View.GONE);
        } else {
            bookDescription.setText(bookDetail.getDescription());
        }
    }
    private void onDownloadFailed() {
        progressCircle.setVisibility(View.GONE);
        bookDetailHolder.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.unable_to_load);
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
