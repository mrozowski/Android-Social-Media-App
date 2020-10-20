package com.example.test_store.NewPost;

import com.google.firebase.Timestamp;

public class NewPostModel {
    private final String title;
    private final String content;
    private final String category;
    private final Timestamp postDate;
    private final String authorID;
    private int likes;
    private int commentsCount;

    public NewPostModel(String title, String content, String category, Timestamp postDate, String authorID) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.postDate = postDate;
        this.authorID = authorID;
        likes = 0;
        commentsCount = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public String getAuthorID() {
        return authorID;
    }

    public int getLikes() {
        return likes;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public String getCategory() {
        return category;
    }
}
