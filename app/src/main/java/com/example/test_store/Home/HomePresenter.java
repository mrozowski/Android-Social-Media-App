package com.example.test_store.Home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        Intent myIntent = new Intent(view.getContext(), PostView.class);
        myIntent.putExtra("postID", postID);
        view.startActivity(myIntent);
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
}
