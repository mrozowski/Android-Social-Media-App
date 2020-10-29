package com.example.test_store.Profile;

public interface ProfileContract {
    interface View{
        void showToast(String message);
    }

    interface Presenter{
        void loadProfileImage();
        void logout();
    }
}
