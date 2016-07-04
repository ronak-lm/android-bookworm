package com.ronakmanglani.booknerd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BestsellerBook implements Parcelable {

    // Attributes
    private String title;
    private String author;
    private String description;
    private String imageUrl;
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
    public String getIsbn10() {
        return isbn10;
    }
    public String getIsbn13() {
        return isbn13;
    }

    // Constructor
    public BestsellerBook(String title, String author, String description, String imageUrl, String isbn10, String isbn13) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }

    // Parceling methods
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public BestsellerBook createFromParcel(Parcel in) {
            return new BestsellerBook(in);
        }
        public BestsellerBook[] newArray(int size) {
            return new BestsellerBook[size];
        }
    };
    public BestsellerBook(Parcel in) {
        this.title = in.readString();
        this.author = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.isbn10 = in.readString();
        this.isbn13 = in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(title);
        out.writeString(author);
        out.writeString(description);
        out.writeString(imageUrl);
        out.writeString(isbn10);
        out.writeString(isbn13);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
