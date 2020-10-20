package com.example.test_store.Post;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_store.R;

import us.feras.mdv.MarkdownView;

public class PostView extends AppCompatActivity {

    TextView title, content, author, authorPostCount, postLikes, postDate, category;
    ImageView authorPhoto, likeButton;

    private PostPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        String postID = getIntent().getStringExtra("postID");

        //content = findViewById(R.id.post_content);
        content = findViewById(R.id.post_content);
        title = findViewById(R.id.post_title);
        category = findViewById(R.id.post_category);
        author = findViewById(R.id.post_author_nick);
        authorPostCount = findViewById(R.id.post_author_posts_count);
        authorPhoto = findViewById(R.id.post_author_profile_picture);
        postLikes = findViewById(R.id.post_likes_count);
        postDate = findViewById(R.id.post_date);
        likeButton = findViewById(R.id.giveLikeButton);

        presenter = new PostPresenter(this);
        presenter.loadPostData(postID);

        //WebView - check it later to add more custom look post like adding picture to post

        likeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                presenter.giveLike();
            }
        });

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
