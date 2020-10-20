package com.example.test_store.Post;

import java.util.Date;

public class PostModel {
    private  String postID;
    private  String title;
    private  String content;
    private Date postDate;
    private  String category;
    private  String authorID;
    private String authorNick;
    private int authorPostCount;
    private int likes;
    private int postCommentsCount;

    public PostModel(){
        //for firebase
    }

    public PostModel(String postID, String title, String content, Date postDate, String category, int likes, int postCommentsCount, String authorID) {
        //constructor without author details
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.category = category;
        this.likes = likes;
        this.postCommentsCount = postCommentsCount;
        this.authorID = authorID;
    }

    public PostModel(String postID, String title, String content, Date postDate, String category, int likes, int postCommentsCount, String authorID, String authorNick, int authorPostCount) {
        //constructor with all parameters
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.category = category;
        this.authorID = authorID;
        this.authorNick = authorNick;
        this.postDate = postDate;
        this.authorPostCount = authorPostCount;
        this.likes = likes;
        this.postCommentsCount = postCommentsCount;
    }

    public String getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorNick() {
        return authorNick;
    }

    public Date getPostDate() {
        return postDate;
    }

    public int getAuthorPostCount() {
        return authorPostCount;
    }

    public int getLikes() {
        return likes;
    }

    public int getPostCommentsCount() {
        return postCommentsCount;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorData(String authorNick, int authorPostCount){
        this.authorNick = authorNick;
        this.authorPostCount = authorPostCount;
    }

    public void addLike() {
        likes++;
    }

    public void addComment(){
        postCommentsCount++;
    }

    public String getCategory() {
        return category;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
