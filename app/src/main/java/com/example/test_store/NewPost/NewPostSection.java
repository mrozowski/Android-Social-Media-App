package com.example.test_store.NewPost;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test_store.R;

import java.util.zip.Inflater;

public class NewPostSection extends Fragment {

    private NewPostSectionPresenter presenter;
    TextView sentenceOfTheDay;
    CardView newPost, template1, template2, saved1;


    public NewPostSection() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post_section, container, false);
        initComponents(view);
        setButtons();
        presenter = new NewPostSectionPresenter(this);
        return view;
    }

    private void setButtons() {
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openWriteNewPostActivity();
            }
        });
    }

    private void initComponents(View view){
        sentenceOfTheDay = view.findViewById(R.id.sentence_of_the_day);
        newPost = view.findViewById(R.id.post_section_new_empty_post);
        template1 = view.findViewById(R.id.post_section_new_post_template1);
        template2 = view.findViewById(R.id.post_section_new_post_template2);
        saved1 = view.findViewById(R.id.post_section_saved1);
    }

}