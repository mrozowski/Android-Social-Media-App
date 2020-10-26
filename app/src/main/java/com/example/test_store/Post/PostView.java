package com.example.test_store.Post;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test_store.R;

import us.feras.mdv.MarkdownView;

public class PostView extends Fragment {

    TextView title, content, author, authorPostCount, postLikes, postDate, category;
    ImageView authorPhoto, likeButton;

    private PostPresenter presenter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post, container, false);
        initComponents(view);
        presenter = new PostPresenter(this);

        //WebView - check it later to add more custom look post like adding picture to post

        likeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                presenter.giveLike();
            }
        });
        authorPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openAuthorProfile();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String postID = bundle.getString("postID");
            presenter.loadPostData(postID);
        }
    }

    private void initComponents(View view){
        content = view.findViewById(R.id.post_content);
        title = view.findViewById(R.id.post_title);
        category = view.findViewById(R.id.post_category);
        author = view.findViewById(R.id.post_author_nick);
        authorPostCount = view.findViewById(R.id.post_author_posts_count);
        authorPhoto = view.findViewById(R.id.post_author_profile_picture);
        postLikes = view.findViewById(R.id.post_likes_count);
        postDate = view.findViewById(R.id.post_date);
        likeButton = view.findViewById(R.id.giveLikeButton);

    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
