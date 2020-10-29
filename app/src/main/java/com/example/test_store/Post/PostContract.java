package com.example.test_store.Post;

public interface PostContract {
    interface View{
        void showToast(String message);
    }

    interface Presenter{
        void giveLike();
        void loadPostData(String postID);
        void openAuthorProfile();
    }
}
