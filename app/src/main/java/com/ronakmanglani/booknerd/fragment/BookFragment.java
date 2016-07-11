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
import com.ronakmanglani.booknerd.util.DimenUtil;
import com.ronakmanglani.booknerd.util.VolleySingleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v7.widget.Toolbar.*;

public class BookFragment extends Fragment implements OnMenuItemClickListener {

    private Unbinder unbinder;
    private Book book;

    @BindView(R.id.toolbar)                 Toolbar toolbar;
    @BindView(R.id.toolbar_text_holder)     View toolbarTextHolder;
    @BindView(R.id.toolbar_title)           TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)        TextView toolbarSubtitle;

    @BindView(R.id.book_message_holder)     View bookMessageHolder;
    @BindView(R.id.book_detail_holder)      View bookDetailHolder;

    @BindView(R.id.book_cover)              NetworkImageView bookCover;
    @BindView(R.id.book_title)              TextView bookTitle;
    @BindView(R.id.book_author_page)        TextView bookAuthorAndPage;
    @BindView(R.id.book_rating_holder)      View bookRatingHolder;
    @BindView(R.id.book_rating)             TextView bookRating;
    @BindView(R.id.book_vote_count)         TextView bookVoteCount;

    @BindView(R.id.book_ranking_holder)     View bookRankingHolder;
    @BindView(R.id.book_current_rank)       TextView bookCurrentRank;
    @BindView(R.id.book_weeks_list)         TextView bookWeeksOnList;

    @BindView(R.id.book_publication_holder) View bookPublisherHolder;
    @BindView(R.id.book_publication_name)   TextView bookPublisher;
    @BindView(R.id.book_publication_date)   TextView bookDate;
    @BindView(R.id.book_publication_isbn)   TextView bookIsbn;

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
            bookDetailHolder.setVisibility(GONE);
            if (getArguments().getBoolean(BookNerdApp.KEY_VISIBILITY, false)) {
                bookMessageHolder.setVisibility(VISIBLE);
            }
            return v;
        }

        // Setup toolbar
        if (book.getSubtitle().length() == 0) {
            toolbarTextHolder.setVisibility(GONE);
            toolbar.setTitle(book.getTitle());
        } else {
            toolbarTitle.setText(book.getTitle());
            toolbarSubtitle.setText(book.getSubtitle());
        }
        toolbar.setOnMenuItemClickListener(this);
        toolbar.inflateMenu(R.menu.menu_book);
        if (!DimenUtil.isTablet()) {
            toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.action_home));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().onBackPressed();
                }
            });
        }

        // Cover Image
        if (book.getImageUrl().length() == 0) {
            bookCover.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.default_cover));
        } else {
            bookCover.setImageUrl(book.getImageUrl(), VolleySingleton.getInstance().imageLoader);
        }

        // Title, author and page count
        bookTitle.setText(book.getTitle());
        if (book.getAuthors().length() == 0 && book.getPageCount().length() == 0) {
            bookAuthorAndPage.setVisibility(View.GONE);
        } else if (book.getPageCount().length() == 0) {
            bookAuthorAndPage.setText(getString(R.string.detail_subtitle_by, book.getAuthors()));
        } else if (book.getAuthors().length() == 0) {
            bookAuthorAndPage.setText(getString(R.string.detail_subtitle_page, book.getPageCount()));
        } else {
            bookAuthorAndPage.setText(getString(R.string.detail_subtitle, book.getAuthors(), book.getPageCount()));
        }

        // Rating
        if (book.getAverageRating().length() == 0 || book.getRatingCount().length() == 0) {
            bookRatingHolder.setVisibility(View.GONE);
        } else {
            bookRating.setText(book.getAverageRating());
            bookVoteCount.setText(getString(R.string.detail_rating, book.getRatingCount()));
        }

        // Ranking
        if (book.getCurrentRank().length() == 0 || book.getWeeksOnList().length() == 0) {
            bookRankingHolder.setVisibility(GONE);
        } else {
            bookCurrentRank.setText(getString(R.string.detail_ranking_current, book.getCurrentRank()));
            if (book.getWeeksOnList().equals("0")) {
                bookWeeksOnList.setVisibility(GONE);
            } else {
                bookWeeksOnList.setText(getString(R.string.detail_ranking_weeks, book.getWeeksOnList()));
            }
        }

        // Publication info
        String publisher = book.getPublisher();
        String publishDate = book.getPublishDate();
        String identifiers = book.getIdentifier();
        if (publisher.length() == 0 && publishDate.length() == 0 && identifiers.length() == 0) {
            bookPublisherHolder.setVisibility(View.GONE);
        } else {
            // Publisher
            if (publisher.length() == 0) {
                bookPublisher.setVisibility(GONE);
            } else {
                bookPublisher.setText(getString(R.string.detail_publication_name, publisher));
            }
            // Publish date
            if (publishDate.length() == 0) {
                bookDate.setVisibility(GONE);
            } else {
                bookDate.setText(getString(R.string.detail_publication_date, publishDate));
            }
            // Identifiers
            if (identifiers.length() == 0) {
                bookIsbn.setVisibility(GONE);
            } else {
                bookIsbn.setText(getString(R.string.detail_publication_isbn, identifiers));
            }
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
