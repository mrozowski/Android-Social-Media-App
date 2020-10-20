package com.example.test_store.ProfileEdit;

public class EditProfileModel {
    private final String userID;
    private final String nick;
    private final String email;
    private final String phone;
    private final String description;

    public EditProfileModel(String userID, String nick, String email, String phone, String description) {
        this.userID = userID;
        this.nick = nick;
        this.email = email;
        this.phone = phone;
        this.description = description;
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

    public String getPhone() {
        return phone;
    }
}
