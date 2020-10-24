package com.example.test_store.Register;

import android.text.TextUtils;

public class RegisterModel {
    private final String nick;
    private final String email;
    private final String phone;
    private final String description;
    private final int likes;
    private final int posts;
    private final int followers;

    public RegisterModel(String nick, String email, String phone) {
        this.nick = nick;
        this.email = email;
        this.phone = phone;

        description = "";
        likes = 0;
        posts = 0;
        followers = 0;
    }


    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


}
