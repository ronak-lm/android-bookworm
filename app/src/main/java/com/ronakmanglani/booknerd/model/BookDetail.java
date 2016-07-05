package com.ronakmanglani.booknerd.model;

public class BookDetail {

    // Attributes
    private String volumeId;
    private String title;
    private String authors;
    private String pageCount;
    private String averageRating;
    private String voteCount;
    private String publisher;
    private String publishDate;
    private String description;
    private String isbn10;
    private String isbn13;

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
    public String getVoteCount() {
        return voteCount;
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
    public String getIsbn10() {
        return isbn10;
    }
    public String getIsbn13() {
        return isbn13;
    }

    // Constructor
    public BookDetail(String volumeId, String title, String authors,
                      String pageCount, String averageRating, String voteCount,
                      String publisher, String publishDate, String description,
                      String isbn10, String isbn13) {
        this.volumeId = volumeId;
        this.title = title;
        this.authors = authors;
        this.pageCount = pageCount;
        this.averageRating = averageRating;
        this.voteCount = voteCount;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }
}
