package com.example.test_store.Profile;

public interface ProfileContract {
    interface View{

    }

    interface Presenter{
        void loadProfileImage();

        void logout();
    }
}
