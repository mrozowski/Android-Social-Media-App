package com.example.test_store.NewPost.add_details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_store.R;
public class AddPostDetailsView extends AppCompatActivity{

    protected String content;
    private AddPostDetailsPresenter presenter;
    EditText title, tags;
    Button back, submit;
    Spinner choose_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_details);

        initComponents();
        presenter = new AddPostDetailsPresenter(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.submitPost();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackClicked();
            }
        });

    }

    private void initComponents() {
        content = getIntent().getStringExtra("content");
        title = findViewById(R.id.new_post_title);
        tags = findViewById(R.id.new_post_tags);
        choose_cat = findViewById(R.id.new_post_category_button);
        back = findViewById(R.id.new_post_details_back);
        submit = findViewById(R.id.new_post_details_send);
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}