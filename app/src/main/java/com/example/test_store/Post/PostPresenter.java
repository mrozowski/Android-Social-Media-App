package com.example.test_store.Post;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;

import com.example.test_store.Constants;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Database.Database;
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

        //view.webView.getSettings().setJavaScriptEnabled(true);
        //view.webView.getSettings().setAllowFileAccess(true);
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
        // view.webView.loadDataWithBaseURL(null, post.getContent(), "text/html", "utf-8", null);
        view.category.setText(post.getCategory());
        //view.webView.loadData(post.getContent(), "text/html; charset=utf-8", "UTF-8");
        view.author.setText((post.getAuthorNick()));
        view.authorPostCount.setText(String.valueOf(post.getAuthorPostCount()));
        view.postLikes.setText(String.valueOf(post.getLikes()));
        view.postDate.setText(Constants.getDefaultDateFormat(post.getPostDate()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void giveLike() {
        if(!liked){
            database.giveLike(post.getPostID(), post.getAuthorID());
            post.addLike();
            view.postLikes.setText(String.valueOf(post.getLikes()));
            view.postLikes.setTextColor(view.getActivity().getColor(R.color.colorPrimaryPurple));
            liked = true;
        }
    }
}
