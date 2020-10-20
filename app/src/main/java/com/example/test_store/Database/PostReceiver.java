package com.example.test_store.Database;

import com.example.test_store.Post.PostModel;
import com.example.test_store.Profile.AppUser;

class PostReceiver extends ResultDataListenerAdapter {
    private PostModel post;
    private ResultDataListener listener;
    private Database database;

    public PostReceiver() {
        database = new Database();
    }

    public void setListener(ResultDataListener listener){
        this.listener = listener;
        database.setListener(this);
    }

    public void getPostData(String postID){
        database.getPostData(postID);
    }

    private void getPostAuthor(String authorID){
        database.getUserDataByID(authorID);
    }

    @Override
    public void onReceiveUserDataListener(AppUser appUser) {
        //pack all data to post object and call listener so class that asked for post data
        //will receive post with all needed data
        post.setAuthorData(appUser.getNick(), appUser.getPosts());
        listener.onReceivePostDataListener(post);
    }

    @Override
    public void onReceivePostDataListener(PostModel post) {
        this.post = post;
        getPostAuthor(post.getAuthorID());
    }
}
