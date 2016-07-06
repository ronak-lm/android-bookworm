package com.ronakmanglani.booknerd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Detail implements Parcelable {

    // Attributes
    private String volumeId;
    private String title;
    private String authors;
    private String pageCount;
    private String averageRating;
    private String ratingCount;
    private String imageUrl;
    private String publisher;
    private String publishDate;
    private String description;

    // Getters
    public String getVolumeId() {
        return volumeId;
    }
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

    // Constructor
    public Detail(String volumeId, String title, String authors, String pageCount,
                  String averageRating, String ratingCount, String imageUrl,
                  String publisher, String publishDate, String description) {
        this.volumeId = volumeId;
        this.title = title;
        this.authors = authors;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
    }

    // Parceling methods
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };
    public Detail(Parcel in) {
        this.volumeId = in.readString();
        this.title = in.readString();
        this.authors = in.readString();
        this.pageCount = in.readString();
        this.averageRating = in.readString();
        this.ratingCount = in.readString();
        this.imageUrl = in.readString();
        this.publisher = in.readString();
        this.publishDate = in.readString();
        this.description = in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(volumeId);
        out.writeString(title);
        out.writeString(authors);
        out.writeString(pageCount);
        out.writeString(averageRating);
        out.writeString(ratingCount);
        out.writeString(imageUrl);
        out.writeString(publisher);
        out.writeString(publishDate);
        out.writeString(description);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
