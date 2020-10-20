package com.example.test_store.Database;

import com.example.test_store.Post.PostModel;
import com.example.test_store.Profile.AppUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public abstract class ResultDataListenerAdapter implements ResultDataListener{
    @Override
    public void onDataResultListener(String result) {

    }

    @Override
    public void onReceiveUserDataListener(AppUser appUser) {

    }

    @Override
    public void onReceivePostDataListener(PostModel post) {

    }

//    @Override
//    public void onReceivePictureListener(Bitmap pic) {
//
//    }


    @Override
    public void onReceiveUserPostListListener(Task<QuerySnapshot> postList) {

    }
}
