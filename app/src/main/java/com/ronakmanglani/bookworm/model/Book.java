package com.ronakmanglani.bookworm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ronakmanglani.bookworm.util.StringUtil;

public class Book implements Parcelable {

    // Attributes
    private String uniqueId;
    private String isbn10;
    private String isbn13;
    private String title;
    private String subtitle;
    private String authors;
    private String pageCount;
    private String averageRating;
    private String ratingCount;
    private String imageUrl;
    private String currentRank;
    private String weeksOnList;
    private String publisher;
    private String publishDate;
    private String description;
    private String itemUrl;

    // Getters
    public String getUniqueId() {
        return uniqueId;
    }
    public String getIsbn10() {
        return isbn10;
    }
    public String getIsbn13() {
        return isbn13;
    }
    public String getIdentifiers() {
        if (StringUtil.isNullOrEmpty(isbn10) && StringUtil.isNullOrEmpty(isbn13)) {
            return "";
        } else if (StringUtil.isNullOrEmpty(isbn10)) {
            return isbn13;
        } else if (StringUtil.isNullOrEmpty(isbn13)) {
            return isbn10;
        } else {
            return isbn10 + ", " + isbn13;
        }
    }
    public String getTitle() {
        return title;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public String getAuthors() {
        return authors;
    }
    public String getPageCount() {
        return pageCount;
    }
    public String getAverageRating() {
        return averageRating;
    }
    public String getRatingCount() {
        return ratingCount;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getCurrentRank() {
        return currentRank;
    }
    public String getWeeksOnList() {
        return weeksOnList;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getPublishDate() {
        return publishDate;
    }
    public String getDescription() {
        return description;
    }
    public String getItemUrl() {
        return itemUrl;
    }

    // Constructor
    public Book(String uniqueId, String isbn10, String isbn13, String title, String subtitle,
                String authors, String pageCount, String averageRating, String ratingCount,
                String imageUrl, String publisher, String publishDate,
                String description, String itemUrl) {
        this.uniqueId = uniqueId;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.imageUrl = imageUrl;
        this.currentRank = "";
        this.weeksOnList = "";
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
        this.itemUrl = itemUrl;
    }
    public Book(Bestseller bestsellerBook) {
        this.uniqueId = bestsellerBook.getUniqueId();
        this.isbn10 = bestsellerBook.getIsbn10();
        this.isbn13 = bestsellerBook.getIsbn13();
        this.title = bestsellerBook.getTitle();
        this.subtitle = "";
        this.authors = bestsellerBook.getAuthor();
        this.pageCount = "";
        this.averageRating = "";
        this.ratingCount = "";
        this.imageUrl = bestsellerBook.getImageUrl();
        this.currentRank = bestsellerBook.getCurrentRank();
        this.weeksOnList = bestsellerBook.getWeeksOnList();
        this.publisher = bestsellerBook.getPublisher();
        this.publishDate = "";
        this.description = bestsellerBook.getDescription();
        this.itemUrl = bestsellerBook.getItemUrl();
    }

    // Parceling methods
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    public Book(Parcel in) {
        this.uniqueId = in.readString();
        this.isbn10 = in.readString();
        this.isbn13 = in.readString();
        this.title = in.readString();
        this.subtitle = in.readString();
        this.authors = in.readString();
        this.pageCount = in.readString();
        this.averageRating = in.readString();
        this.ratingCount = in.readString();
        this.imageUrl = in.readString();
        this.currentRank = in.readString();
        this.weeksOnList = in.readString();
        this.publisher = in.readString();
        this.publishDate = in.readString();
        this.description = in.readString();
        this.itemUrl = in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(uniqueId);
        out.writeString(isbn10);
        out.writeString(isbn13);
        out.writeString(title);
        out.writeString(subtitle);
        out.writeString(authors);
        out.writeString(pageCount);
        out.writeString(averageRating);
        out.writeString(ratingCount);
        out.writeString(imageUrl);
        out.writeString(currentRank);
        out.writeString(weeksOnList);
        out.writeString(publisher);
        out.writeString(publishDate);
        out.writeString(description);
        out.writeString(itemUrl);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
