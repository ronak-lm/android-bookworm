package com.ronakmanglani.booknerd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    // Attributes
    private String title;
    private String authors;
    private String pageCount;
    private String averageRating;
    private String ratingCount;
    private String imageUrl;
    private String publisher;
    private String publishDate;
    private String description;
    private String itemUrl;

    // Getters
    public String getTitle() {
        return title;
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
    public Book(String title, String authors, String pageCount, String averageRating,
                String ratingCount, String imageUrl, String publisher,
                String publishDate, String description, String itemUrl) {
        this.title = title;
        this.authors = authors;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
        this.itemUrl = itemUrl;
    }
    public Book(Bestseller bestsellerBook) {
        this.title = bestsellerBook.getTitle();
        this.authors = bestsellerBook.getAuthor();
        this.pageCount = "";
        this.averageRating = "";
        this.ratingCount = "";
        this.imageUrl = bestsellerBook.getImageUrl();
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
        this.title = in.readString();
        this.authors = in.readString();
        this.pageCount = in.readString();
        this.averageRating = in.readString();
        this.ratingCount = in.readString();
        this.imageUrl = in.readString();
        this.publisher = in.readString();
        this.publishDate = in.readString();
        this.description = in.readString();
        this.itemUrl = in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(title);
        out.writeString(authors);
        out.writeString(pageCount);
        out.writeString(averageRating);
        out.writeString(ratingCount);
        out.writeString(imageUrl);
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
