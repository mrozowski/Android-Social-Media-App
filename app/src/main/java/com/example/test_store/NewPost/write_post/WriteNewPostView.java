package com.example.test_store.NewPost.write_post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test_store.R;

import static com.example.test_store.Constants.IMG_INTEND_REQUEST_CODE;

public class WriteNewPostView extends AppCompatActivity implements WriteNewPostContract.View{

    EditText postContent;
    ImageView goBack, next, more;
    private WriteNewPostPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);
        initComponents();
        presenter = new WriteNewPostPresenter(this);
        setButtons();
        getExtras();
    }

    private void getExtras() {
        if(getIntent().getExtras() != null){
            postContent.setText(getIntent().getStringExtra("loadPost"));
        }
    }

    private void setButtons() {
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goBack();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goNext();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showMoreOptions(v);
            }
        });
    }

    private void initComponents() {
        postContent = findViewById(R.id.new_post_edit_text);
        goBack = findViewById(R.id.new_post_go_back);
        next = findViewById(R.id.new_post_next);
        more = findViewById(R.id.new_post_more);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == IMG_INTEND_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.openGallery();
            } else {
                showToast("Permission not granted");
            }
        }
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}