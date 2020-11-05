package com.example.test_store.OtherUserProfile;

public interface OtherUserProfileContract {
    interface View{
        void showToast(String message);
    }

    interface Presenter{
        void getUser(String userID);
        void openPostFragment(String postID);
    }
}
