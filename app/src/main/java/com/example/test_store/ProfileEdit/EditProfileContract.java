package com.example.test_store.ProfileEdit;

public interface EditProfileContract {
    interface View{
        void showToast(String message);
    }

    interface Presenter{
        void saveData();
        void onDestroy();
        void changeImage();
    }
}
