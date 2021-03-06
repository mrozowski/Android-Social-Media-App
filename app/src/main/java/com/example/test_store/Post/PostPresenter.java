package com.example.test_store.Post;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;

import com.example.test_store.Constants;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Database.Database;
import com.example.test_store.Helper;
import com.example.test_store.OtherUserProfile.OtherUserProfileView;
import com.example.test_store.ProfileEdit.EditProfileView;
import com.example.test_store.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostPresenter extends ResultDataListenerAdapter implements PostContract.Presenter {
    private boolean liked = false;
    private PostView view;
    private Database database;
    private PostModel post;

    public PostPresenter(PostView postView) {
        view = postView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void loadPostData(String postID) {
        database = new Database();
        database.setListener(this);
        database.getPostDataByID(postID);
    }

    @Override
    public void onDataResultListener(String result) {
        view.showToast(result);
    }

    @Override
    public void onReceivePostDataListener(PostModel post) {
        this.post = post;
        showPost();
        database.getUserProfilePictureByID(view.authorPhoto, post.getAuthorID());
    }

    private void showPost() {
        view.title.setText(post.getTitle());
        view.content.setText(HtmlCompat.fromHtml(post.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        view.category.setText(post.getCategory());
        view.author.setText((post.getAuthorNick()));
        view.authorPostCount.setText(String.valueOf(post.getAuthorPostCount()));
        view.postLikes.setText(String.valueOf(post.getLikes()));
        view.postDate.setText(Helper.getDefaultDateFormat(post.getPostDate()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void giveLike() {
        if(!liked){
            database.giveLike(post.getPostID(), post.getAuthorID());
            post.addLike();
            view.postLikes.setText(String.valueOf(post.getLikes()));
            view.postLikes.setTextColor(view.getActivity().getColor(R.color.colorPrimaryPurple));
            liked = true;
        }
    }

    @Override
    public void openAuthorProfile() {
        OtherUserProfileView authorProfile = new OtherUserProfileView();
        Bundle bundle = new Bundle();
        bundle.putString("userID", post.getAuthorID());

        authorProfile.setArguments(bundle);

        view.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)view.getView().getParent()).getId(), authorProfile, "authorProfile")
                .addToBackStack(view.getClass().getName())
                .commit();
    }
}
