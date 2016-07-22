package com.ronakmanglani.bookworm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ronakmanglani.bookworm.util.StringUtil;

public class Bestseller implements Parcelable {

    // Attributes
    private String isbn10;
    private String isbn13;
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    private String currentRank;
    private String weeksOnList;
    private String publisher;
    private String itemUrl;

    // Getters
    public String getUniqueId() {
        // If book has ISBN10, use it as ID
        if (!StringUtil.isNullOrEmpty(isbn10)) {
            return "nyt1_" + isbn10;
        }
        // If no ISBN10, use ISBN13
        else if (!StringUtil.isNullOrEmpty(isbn13)) {
            return "nyt2_" + isbn13;
        }
        // If no ISBN numbers, use combination of title and author
        else {
            return "nyt3_" + StringUtil.cleanText(title) + StringUtil.cleanText(author);
        }
    }
    public String getIsbn10() {
        return isbn10;
    }
    public String getIsbn13() {
        return isbn13;
    }
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
    public String getCurrentRank() {
        return currentRank;
    }
    public String getWeeksOnList() {
        return weeksOnList;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getItemUrl() {
        return itemUrl;
    }

    // Constructor
    public Bestseller(String isbn10, String isbn13, String title, String author,
                      String description, String imageUrl, String currentRank,
                      String weeksOnList, String publisher, String itemUrl) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.currentRank = currentRank;
        this.weeksOnList = weeksOnList;
        this.publisher = publisher;
        this.itemUrl = itemUrl;
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
        this.isbn10 = in.readString();
        this.isbn13 = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.currentRank = in.readString();
        this.weeksOnList = in.readString();
        this.publisher = in.readString();
        this.itemUrl = in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(isbn10);
        out.writeString(isbn13);
        out.writeString(title);
        out.writeString(author);
        out.writeString(description);
        out.writeString(imageUrl);
        out.writeString(currentRank);
        out.writeString(weeksOnList);
        out.writeString(publisher);
        out.writeString(itemUrl);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
