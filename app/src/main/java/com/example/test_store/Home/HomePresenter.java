package com.example.test_store.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test_store.Constants;
import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Post.PostView;
import com.example.test_store.list.ItemDetails;
import com.example.test_store.list.MyRecyclerViewAdapter;
import com.example.test_store.list.VerticalSpaceItemDecoration;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.test_store.Constants.TAG;

public class HomePresenter extends ResultDataListenerAdapter implements MyRecyclerViewAdapter.ItemClickListener{
    private HomeView view;
    private Database database;
    MyRecyclerViewAdapter adapter;

    private ArrayList<ItemDetails> postList;

    public HomePresenter(HomeView view) {
        database = new Database();
        database.setListener(this);
        database.getAllPost();
        this.view = view;
        postList = new ArrayList<>();
    }

    private void showPostList(){
        view.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        adapter = new MyRecyclerViewAdapter(view.getContext(), postList);
        adapter.setClickListener(this);
        view.recyclerView.setAdapter(adapter);
        view.recyclerView.setNestedScrollingEnabled(false);
    }


    public void openPostActivity(String postID){
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
    public void onItemClick(View view, int position) {
        String postID = adapter.getItem(position);
        openPostActivity(postID);
    }

    @Override
    public void onReceiveUserPostListListener(Task<QuerySnapshot> task) {
        if(task.getResult() != null){
            for(QueryDocumentSnapshot doc : task.getResult()){
                postList.add(new ItemDetails(doc.getString("title"),
                        "author",
                        doc.getString("category"),
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
}
