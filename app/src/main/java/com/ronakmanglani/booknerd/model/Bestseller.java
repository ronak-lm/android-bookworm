package com.ronakmanglani.booknerd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bestseller implements Parcelable {

    // Attributes
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    private String publisher;
    private String isbn10;
    private String isbn13;

    // Getters
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getDescription() {
        return description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getIsbn10() {
        return isbn10;
    }
    public String getIsbn13() {
        return isbn13;
    }

    // Constructor
    public Bestseller(String title, String author, String description, String imageUrl, String publisher, String isbn10, String isbn13) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }

    // Parceling methods
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Bestseller createFromParcel(Parcel in) {
            return new Bestseller(in);
        }
        public Bestseller[] newArray(int size) {
            return new Bestseller[size];
        }
    };
    public Bestseller(Parcel in) {
        this.title = in.readString();
        this.author = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.publisher = in.readString();
        this.isbn10 = in.readString();
        this.isbn13 = in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(title);
        out.writeString(author);
        out.writeString(description);
        out.writeString(imageUrl);
        out.writeString(publisher);
        out.writeString(isbn10);
        out.writeString(isbn13);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
