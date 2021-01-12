package com.example.test_store.OtherUserProfile;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Helper;
import com.example.test_store.Post.PostView;
import com.example.test_store.Profile.AppUser;
import com.example.test_store.list.ItemDetails;
import com.example.test_store.list.MyRecyclerViewAdapter;
import com.example.test_store.list.VerticalSpaceItemDecoration;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.test_store.Constants.TAG;

public class OtherUserProfilePresenter extends ResultDataListenerAdapter implements OtherUserProfileContract.Presenter {
    private OtherUserProfileView view;
    private AppUser appUser; // add a new model
    private Database database;
    ArrayList<ItemDetails> postList;


    public OtherUserProfilePresenter(OtherUserProfileView view) {
        this.view = view;
        postList = new ArrayList<>();
        database = new Database();
        database.setListener(this);
    }

    @Override
    public void getUser(String userID) {
        connectUser(userID);
        loadProfileImage(userID);
    }

    private void loadProfileImage(String userID) {
        database.getUserProfilePictureByID(view.profileImage, userID);
    }

    private void connectUser(String userID) {
        database.getUserDataByID(userID);
    }

    private void loadUserPostList(){
        database.getUserPostList(appUser.getUserID());
    }

    private void showPostList(){
        view.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        view.adapter = new MyRecyclerViewAdapter(view.getContext(), Helper.sortListByDate(postList));
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

    @Override
    public void openPostFragment(String postID){
        PostView postFragment = new PostView();
        Bundle bundle = new Bundle();
        bundle.putString("postID", postID);
        postFragment.setArguments(bundle);
        view.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)view.getView().getParent()).getId(), postFragment, "postFragment")
                .addToBackStack(view.getClass().getName())
                .commit();
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

        }
    }

    @Override
    public void onReceiveUserPostListListener(Task<QuerySnapshot> task) {
        if(task.getResult() != null){
            for(QueryDocumentSnapshot doc : task.getResult()){
                //Log.d(Constants.TAG, doc.getId() + " => " + doc.getData());
                postList.add(new ItemDetails(doc.getString("title"),
                        "author",
                        doc.getString("category"),
                        doc.getLong("commentsCount").intValue(),
                        doc.getLong("likes").intValue(),
                        doc.getDate("postDate"),
                        doc.getId()));
            }
            showPostList();
        }
        else{
            Log.d(TAG, "Couldn't load user posts list");
        }

    }

}
