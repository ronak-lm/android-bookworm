package com.ronakmanglani.booknerd.model;

public class BestsellerBook {

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
}
