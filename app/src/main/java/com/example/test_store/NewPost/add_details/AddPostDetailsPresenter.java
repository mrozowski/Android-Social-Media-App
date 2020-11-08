package com.example.test_store.NewPost.add_details;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.test_store.BottomNavigation;
import com.example.test_store.Constants;
import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Home.HomeView;
import com.example.test_store.R;
import com.example.test_store.NewPost.NewPostModel;
import com.example.test_store.Profile.AppUser;
import com.google.firebase.Timestamp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.test_store.Constants.USER_DATA_FILE;

public class AddPostDetailsPresenter implements AdapterView.OnItemSelectedListener, AddPostDetailsContract.Presenter {
    private AddPostDetailsView view;
    public AddPostDetailsPresenter(AddPostDetailsView view) {
        this.view = view;
        setCategoryDropDownMenu();
    }

    private void setCategoryDropDownMenu() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.choose_cat.setAdapter(adapter);

        view.choose_cat.setOnItemSelectedListener(this);
        view.choose_cat.setSelection(0);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextSize(22);
        ((TextView) view).setGravity(Gravity.CENTER);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            view.showToast("Please select category");
    }

    @Override
    public void submitPost() {
        Database database = new Database();
        if(validatePost()){
            ResultDataListenerAdapter dataAdapter = new ResultDataListenerAdapter() {
                @Override
                public void onDataResultListener(String result) {
                    view.showToast(result);
                    openHomeFragment();
                }
            };
            database.setListener(dataAdapter);
            NewPostModel newPost = createPost();
            database.uploadNewPost(newPost);
        }
    }

    private void openHomeFragment() {
       view.startActivity(new Intent(view.getApplicationContext(), BottomNavigation.class));
       view.finish();
    }

    private boolean validatePost() {
        if(!checkTitle()) return false;
        if(!checkContent()) return false;
        return true;
    }

    private boolean checkContent() {
        String content = view.content;
        if(!content.equals("")){
            if(content.length() < 10) {
                view.showToast("Content is too small");
                return false;
            }
        }
        else {
            view.showToast("Content cannot be empty!");
            return false;
        }
        return true;
    }

    private boolean checkTitle() {
        String title = view.title.getText().toString();
        if(!title.equals("")) {
            if (title.length() < 3){
                view.title.setError("Title is too short");
                return false;
            }
            else if (title.length() > 30) {
                view.title.setError("Title is too long");
                return false;
            }
        }
        else{
            view.title.setError("Title cannot be empty");
            return false;
        }
        return true;
    }

    private NewPostModel createPost() {
        String authorID = getUserId();
        return new NewPostModel(
                view.title.getText().toString(),
                view.content,
                view.choose_cat.getSelectedItem().toString(),
                Timestamp.now(),
                authorID);
    }

    private String getUserId(){
        String id = "";
        FileInputStream user = null;
        AppUser appUser = null;
        try {
            user = view.openFileInput(USER_DATA_FILE);
            ObjectInputStream objectIn = new ObjectInputStream(user);
            appUser = (AppUser) objectIn.readObject();
            id = appUser.getUserID();
        } catch (IOException e){
            //If something goes wrong with the file then connect to database to get userID
            id = new Database().getUserID();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public void onBackClicked() {
        view.finish();
    }
}
