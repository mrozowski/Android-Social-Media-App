package com.example.test_store.Profile;

import java.io.Serializable;

public class AppUser implements Serializable {
    private final String userID;
    private final String nick;
    private final String email;
    private final String phone;
    private final String description;
    private final int followers;
    private final int likes;
    private final int posts;


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
