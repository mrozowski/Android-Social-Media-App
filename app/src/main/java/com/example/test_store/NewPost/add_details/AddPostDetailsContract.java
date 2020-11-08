package com.example.test_store.NewPost.add_details;

public interface AddPostDetailsContract {
    interface View{
        void showToast(String message);
    }
    interface Presenter{
        void onBackClicked();
        void submitPost();
    }
}
