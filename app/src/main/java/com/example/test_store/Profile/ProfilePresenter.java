package com.example.test_store.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test_store.Constants;
import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Login;
import com.example.test_store.NewPost.write_post.WriteNewPostView;
import com.example.test_store.Post.PostView;
import com.example.test_store.ProfileEdit.EditProfileView;
import com.example.test_store.list.ItemDetails;
import com.example.test_store.list.MyRecyclerViewAdapter;
import com.example.test_store.list.VerticalSpaceItemDecoration;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.test_store.Constants.TAG;
import static com.example.test_store.Constants.USER_DATA_FILE;

public class ProfilePresenter extends ResultDataListenerAdapter implements ProfileContract.Presenter {
    private Profile view;
    private Database database;
    private AppUser appUser;

    ArrayList<ItemDetails> postList;

    public ProfilePresenter(Profile view) {
        this.view = view;
        postList = new ArrayList<>();
        database = new Database();
        database.setListener(this);
        loadProfileImage();
    }

    public void loadProfileImage() {
        database.getCurrentUserProfilePicture(view.profileImage);
    }

    public void connectUser() {
        database.getCurrentUserData();
    }

    protected void loadUserPostList(){
        database.getUserPostList(appUser.getUserID());
    }

    private void showPostList(){
        view.recyclerView.setLayoutManager(new LinearLayoutManager(view));
        view.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        view.adapter = new MyRecyclerViewAdapter(view, postList);
        view.adapter.setClickListener(view);
        view.recyclerView.setAdapter(view.adapter);
        view.recyclerView.setNestedScrollingEnabled(false);
    }
    private void loadUserData() {
        view.nick.setText(appUser.getNick());
        view.likes.setText(String.valueOf(appUser.getLikes()));
        view.posts.setText(String.valueOf(appUser.getPosts()));
        view.followers.setText(String.valueOf(appUser.getFollowers()));
        view.description.setText(appUser.getDescription());
    }

    public void openPostActivity(String postID){
        Intent myIntent = new Intent(view.getApplicationContext(), PostView.class);
        myIntent.putExtra("postID", postID);
        view.startActivity(myIntent);
    }

    public void openEditProfileActivity() {
        Intent myIntent = new Intent(view.getApplicationContext(), EditProfileView.class);
        myIntent.putExtra("userID", appUser.getUserID());
        myIntent.putExtra("nick", appUser.getNick());
        myIntent.putExtra("email", appUser.getEmail());
        myIntent.putExtra("phone", appUser.getPhone());
        myIntent.putExtra("desc", appUser.getDescription());
        view.startActivity(myIntent);
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        view.startActivity(new Intent(view.getApplicationContext(), Login.class));
        view.finish();
    }

    @Override
    public void onDataResultListener(String result) {
        view.showToast(result);
    }

    @Override
    public void onReceiveUserDataListener(AppUser appUser) {
        //Listener that is called when database return data
        if(appUser != null){
            this.appUser = appUser;
            loadUserData();
            loadUserPostList();
            //save to internal storage
           saveUserDataToInternalStorage();

        }
    }

//    @Override
//    public void onReceivePictureListener(Bitmap pic) {
//        saveUserProfilePictureToInternalStorage(pic);
//    }


    @Override
    public void onReceiveUserPostListListener(Task<QuerySnapshot> task) {
        if(task.getResult() != null){
            for(QueryDocumentSnapshot doc : task.getResult()){
                //Log.d(Constants.TAG, doc.getId() + " => " + doc.getData());
                postList.add(new ItemDetails(doc.getString("title"),
                        "author",
                        doc.getLong("commentsCount").intValue(),
                        doc.getLong("likes").intValue(),
                        Constants.getDefaultDateFormat(doc.getDate("postDate")),
                        doc.getId()));
            }

            showPostList();
        }
        else{
            Log.d(TAG, "Couldn't load user posts list");
        }

    }

    private void saveUserProfilePictureToInternalStorage(Bitmap pic) {
        FileOutputStream userFile = null;

        //get bitmap of user profile picture
        //Log.d("MyTAG", view.profileImage.get);
        //BitmapDrawable profileDrawable = (BitmapDrawable) view.profileImage.getBackground();

        try{
            File f = new File(view.getFilesDir(), "profile.png");
            userFile = new FileOutputStream(f);
            pic.compress(Bitmap.CompressFormat.PNG, 100, userFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }

    private void saveUserDataToInternalStorage(){
        //saving user data to internal storage. To not ask database too often for this same data
        FileOutputStream userFile = null;
        try{
            userFile = view.openFileOutput(USER_DATA_FILE, MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(userFile);
            objectOut.writeObject(appUser);
            objectOut.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void openSettingsActivity() {
        view.startActivity(new Intent(view.getApplicationContext(), WriteNewPostView.class));
    }
}
