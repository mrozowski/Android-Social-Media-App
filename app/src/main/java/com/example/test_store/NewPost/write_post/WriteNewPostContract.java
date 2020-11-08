package com.example.test_store.NewPost.write_post;

import android.view.View;

public interface WriteNewPostContract {
    interface View{
        void showToast(String message);
    }

    interface Presenter{
        void goBack();
        void goNext();
        void showMoreOptions(android.view.View v);
    }
}
