package com.example.test_store.Profile;

import java.io.Serializable;

public class AppUser implements Serializable {
    private String userID;
    private String nick;
    private String email;
    private String phone;
    private String description;
    private int followers;
    private int likes;
    private int posts;

    public AppUser(){
        //empty constructor for loading data from firebase
    }

    public AppUser(String userID, String nick, String email, String phone, String description, int followers, int likes, int posts) {
        this.userID = userID;
        this.nick = nick;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.followers = followers;
        this.likes = likes;
        this.posts = posts;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }
    public String getUserID() {
        return userID;
    }

    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public int getFollowers() {
        return followers;
    }

    public int getLikes() {
        return likes;
    }

    public int getPosts() {
        return posts;
    }

    public String getPhone() {
        return phone;
    }


}
