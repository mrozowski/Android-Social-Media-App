package com.example.test_store.Database;


import android.net.Uri;
import android.widget.ImageView;

import com.example.test_store.NewPost.NewPostModel;

public interface DTO {
   void changeDescription(String newDescription);
   void changeNick(String newNick);
   void changePhoneNumber(String newNumber);
   void changeEmail(String email, String auth);
   void changePassword(String newPassword, String auth);
   void updateProfilePictureOnFirebase(Uri imgUri);
   void getCurrentUserProfilePicture(ImageView profile);
   void getUserProfilePictureByID(ImageView profile, String userID);

   void getCurrentUserData();
   void getUserDataByID(String userID);

   void getPostDataByID(String postID);
   void getUserPostList(String userID);
   void getAllPost();

   void uploadNewPost(NewPostModel post);

   void giveLike(String postID, String userID);
   void followUser(String userID);


   boolean isLoggedIn();
   void login(String email, String password);
}
