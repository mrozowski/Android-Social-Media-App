package com.example.test_store.Post;

public interface PostContract {
    interface View{

    }

    interface Presenter{

        void loadPostData(String postID);
    }
}
