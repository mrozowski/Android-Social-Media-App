package com.example.test_store.NewPost;

import android.content.Intent;
import android.util.Log;

import com.example.test_store.NewPost.write_post.WriteNewPostView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;

public class NewPostSectionPresenter {
    private NewPostSection view;
    private String loadedPost;

    public NewPostSectionPresenter(NewPostSection view) {
        this.view = view;
    }

    public void openWriteNewPostActivity() {
        if(loadedPost == null){
            view.getActivity().startActivity(new Intent(view.getContext(), WriteNewPostView.class));
        }
        else{
            Intent intent = new Intent(view.getContext(), WriteNewPostView.class);
            intent.putExtra("loadPost", loadedPost);
            loadedPost = null;
            view.getActivity().startActivity(intent);
        }
    }


    public void loadSavedDraft() {
        FileInputStream draft = null;
        StringBuilder post = new StringBuilder();
        Scanner scanner;
        try{
            draft = view.getActivity().openFileInput("post.txt");
            scanner = new Scanner(draft).useDelimiter("\\Z");
            while (scanner.hasNext()) {
                post.append(scanner.next());
            }
            scanner.close();
            loadedPost = post.toString();

        } catch (IOException e){
            Log.d("MyTAG", "error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
