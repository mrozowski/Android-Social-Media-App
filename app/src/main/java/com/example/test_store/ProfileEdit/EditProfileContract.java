package com.example.test_store.ProfileEdit;

public interface EditProfileContract {
    interface View{

    }

    interface Presenter{
        void saveData();
        void onDestroy();

        void changeImage();
    }
}
