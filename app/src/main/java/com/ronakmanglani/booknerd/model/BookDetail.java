package com.ronakmanglani.booknerd.model;

public class BookDetail {

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
    public BookDetail(String volumeId, String title, String authors, String pageCount,
                      String averageRating, String ratingCount,String imageUrl,
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
}
