package com.ronakmanglani.booknerd.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ronakmanglani.booknerd.BookNerdApp;
import com.ronakmanglani.booknerd.R;
import com.ronakmanglani.booknerd.model.Book;
import com.ronakmanglani.booknerd.util.VolleySingleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v7.widget.Toolbar.*;

public class BookFragment extends Fragment implements OnMenuItemClickListener {

    private Unbinder unbinder;
    private Book book;

    @BindView(R.id.toolbar)                 Toolbar toolbar;
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
        View v = inflater.inflate(R.layout.fragment_book, container, false);
        unbinder = ButterKnife.bind(this, v);

        // Get the book
        book = getArguments().getParcelable(BookNerdApp.KEY_BOOK);
        if (book == null) {
            return v;
        }

        // Setup toolbar
        toolbar.setTitle(book.getTitle());
        toolbar.setOnMenuItemClickListener(this);
        toolbar.inflateMenu(R.menu.menu_book);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.action_home));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        // Cover Image
        if (book.getImageUrl().length() == 0) {
            bookCover.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.default_cover));
        } else {
            bookCover.setImageUrl(book.getImageUrl(), VolleySingleton.getInstance().imageLoader);
        }

        // Title and Subtitle
        toolbar.setTitle(book.getTitle());
        bookTitle.setText(book.getTitle());
        if (book.getAuthors().length() == 0 && book.getPageCount().length() == 0) {
            bookSubtitle.setVisibility(View.GONE);
        } else if (book.getPageCount().length() == 0) {
            bookSubtitle.setText(getString(R.string.detail_subtitle_by, book.getAuthors()));
        } else if (book.getAuthors().length() == 0) {
            bookSubtitle.setText(getString(R.string.detail_subtitle_page, book.getPageCount()));
        } else {
            bookSubtitle.setText(getString(R.string.detail_subtitle, book.getAuthors(), book.getPageCount()));
        }

        // Rating
        if (book.getAverageRating().length() == 0 || book.getRatingCount().length() == 0) {
            bookRatingHolder.setVisibility(View.GONE);
        } else {
            bookRating.setText(book.getAverageRating());
            bookVoteCount.setText(getString(R.string.detail_rating, book.getRatingCount()));
        }

        // Publication info
        if (book.getPublisher().length() == 0 && book.getPublishDate().length() == 0) {
            bookPublisherHolder.setVisibility(View.GONE);
        } else if (book.getPublisher().length() == 0) {
            bookPublisher.setVisibility(View.GONE);
            bookDate.setText(getString(R.string.detail_publication_date, book.getPublishDate()));
        } else if (book.getPublishDate().length() == 0) {
            bookPublisher.setText(getString(R.string.detail_publication_name, book.getPublisher()));
            bookDate.setVisibility(View.GONE);
        } else {
            bookPublisher.setText(getString(R.string.detail_publication_name, book.getPublisher()));
            bookDate.setText(getString(R.string.detail_publication_date, book.getPublishDate()));
        }

        // Book description
        if (book.getDescription().length() == 0) {
            bookDescriptionHolder.setVisibility(View.GONE);
        } else {
            bookDescription.setText(book.getDescription());
        }

        return v;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // Click Events
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_share:
                String shareSubject = getString(R.string.action_share_subject, book.getTitle());
                String shareText = getString(R.string.action_share_text, book.getTitle(), book.getAuthors(), book.getItemUrl());
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.action_share_title)));
                return true;

            case R.id.action_open:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getItemUrl()));
                startActivity(browserIntent);
                return true;

            default:
                return false;
        }
    }
}
