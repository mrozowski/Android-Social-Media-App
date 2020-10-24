package com.example.test_store.Database;



import com.example.test_store.Post.PostModel;
import com.example.test_store.Profile.AppUser;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.QuerySnapshot;



public interface ResultDataListener {
    void onDataResultListener(String result);
    //void onReceivePictureListener(Bitmap pic);
    void onReceiveUserPostListListener(Task<QuerySnapshot> task);
    void onReceiveUserDataListener(AppUser appUser);
    void onReceivePostDataListener(PostModel post);
    void onLoginListener(boolean isSuccess);

    void onRegisterListener(boolean isSuccess);
}
