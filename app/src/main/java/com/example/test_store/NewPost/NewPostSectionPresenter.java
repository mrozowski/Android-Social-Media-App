package com.example.test_store.NewPost;

import android.content.Intent;

import com.example.test_store.NewPost.write_post.WriteNewPostView;

public class NewPostSectionPresenter {
    private NewPostSection view;

    public NewPostSectionPresenter(NewPostSection view) {
        this.view = view;
    }

    public void openWriteNewPostActivity() {
        view.getActivity().startActivity(new Intent(view.getContext(), WriteNewPostView.class));
    }
}
