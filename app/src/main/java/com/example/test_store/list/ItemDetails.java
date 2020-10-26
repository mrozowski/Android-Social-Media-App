package com.example.test_store.list;

import android.widget.ImageView;

public class ItemDetails {
    private final String title;
    private final String date;
    private final String author;
    private final String category;
    private final int comments;
    private final int likes;
    private final String postID;
    private ImageView image;


    public ItemDetails(String title, String author, String category, int comments, int likes, String date, String ID) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.comments = comments;
        this.likes = likes;
        this.date = date;
        this.postID = ID;
    }

    public String getTitle() {
        return title;
    }

    public int getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public String getDate() {
        return date;
    }

    public String getPostID() {
        return postID;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
