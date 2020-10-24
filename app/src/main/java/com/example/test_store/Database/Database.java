package com.example.test_store.Database;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test_store.BottomNavigation;
import com.example.test_store.Constants;
import com.example.test_store.Logowanie.Login;
import com.example.test_store.Post.PostModel;
import com.example.test_store.NewPost.NewPostModel;
import com.example.test_store.Profile.AppUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.test_store.Constants.PROFILE_IMG_NAME;
import static com.example.test_store.Constants.getProfileDirectory;


public class Database implements DTO{
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseStore;
    private DocumentReference userFileRef;  //Refers to Firebase Cloud FireStore - users collection
    private DocumentReference postFileRef;  //Refers to Firebase Cloud FireStore - posts collection
    private StorageReference storageRef;    //Refers to Firebase Storage
    private FirebaseUser user;
    private ResultDataListener listener;

    public Database() {
        firebaseStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void connectToUserFireStoreDatabase(String userID){
        if(userFileRef == null || !userFileRef.getId().equals(userID))
            userFileRef = firebaseStore.collection("users").document(userID);
    }

    private void connectToPostFireStoreDatabase(String postID){
        //maybe later make one method for connection to FireStore with param collection and id
        // check it later and wrap in if statement - postFileRef.getId()
        postFileRef = firebaseStore.collection("posts").document(postID);
    }


    private void connectToFirebaseStorage(String userID){
        if(storageRef == null){
            storageRef = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child(getProfileDirectory(userID) + PROFILE_IMG_NAME);
        }
    }

    @Override
    public void giveLike(String postID, String userID) {
        //updateUserDataByID("Likes",)
        //connectToUserFireStoreDatabase(userID);  add user later
        connectToPostFireStoreDatabase(postID);
        connectToUserFireStoreDatabase(userID);
        incrementData(postFileRef, "likes"); // add like to post
        incrementData(userFileRef, "Likes"); // add like to user
    }

    @Override
    public void followUser(String userID) {
        //add later
    }

    @Override
    public void changeDescription(String newDescription) {
        updateData("Description", newDescription);
    }

    @Override
    public void changeNick(String newNick) {
        updateData("Nick", newNick);
    }

    @Override
    public void changePhoneNumber(String newNumber) {
        updateData("Phone", newNumber);
    }

    @Override
    public void changeEmail(final String newEmail, String auth) {
        if(auth == null) return;
        connectToUserFireStoreDatabase(user.getUid());
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), auth);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("MyTAG", "User got authentication");
            }
        });

        user.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                userFileRef.update("Email", newEmail);
                listener.onDataResultListener("Email changed");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e.getClass() == FirebaseAuthRecentLoginRequiredException.class){
                    listener.onDataResultListener("Please login again");
                }
                listener.onDataResultListener("Something went wrong :/");
                Log.d("MyTAG", "Error: "+e.getMessage());
            }
        });
    }

    @Override
    public void changePassword(String newPassword, String auth) {

        AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(user.getEmail()), auth);
        user.reauthenticate(credential);

        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onDataResultListener("Password changed");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onDataResultListener("Something went wrong :/");
                Log.d("MyTAG", "Error: "+e.getMessage());
            }
        });
    }

    @Override
    public void updateProfilePictureOnFirebase(Uri imgUri) {
        connectToFirebaseStorage(user.getUid());

        storageRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                listener.onDataResultListener("Image uploaded");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onDataResultListener("Something went wrong :/");
                Log.d("MyTAG", "Error: "+e.getMessage());
            }
        });
    }

    @Override
    public void getCurrentUserProfilePicture(ImageView profile) {
        connectToFirebaseStorage(user.getUid());
        loadPicture(profile);
    }

    @Override
    public void getUserProfilePictureByID(ImageView profile, String userID) {
        connectToFirebaseStorage(userID);
        loadPicture(profile);
    }

    @Override
    public void getCurrentUserData() {
        connectToUserFireStoreDatabase(user.getUid());
        getUserData();
}

    @Override
    public void getUserDataByID(String userID) {
        connectToUserFireStoreDatabase(userID);
        getUserData();
    }

    @Override
    public void getPostDataByID(String postID) {
        PostReceiver receiver = new PostReceiver();
        receiver.setListener(listener);
        receiver.getPostData(postID);
    }

    public void getUserPostList(String userID) {
        CollectionReference posts = firebaseStore.collection("posts");

        posts.whereEqualTo("authorID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            listener.onReceiveUserPostListListener(task);
                        }
                        else{
                            Log.d(Constants.TAG, "Failed");
                        }
                    }
                });

    }

    @Override
    public void getAllPost() {
        CollectionReference posts = firebaseStore.collection("posts");
        posts.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    listener.onReceiveUserPostListListener(task);
                }
                else{
                    Log.d(Constants.TAG, "Failed");
                }
            }
        });
    }

    @Override
    public void uploadNewPost(NewPostModel post) {
        connectToUserFireStoreDatabase(user.getUid());
        firebaseStore.collection("posts")
                .add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.onDataResultListener("Post uploaded");
                    }
                });
    }

    @Override
    public boolean isLoggedIn() {
        return user != null;
    }

    @Override
    public void login(String email, String password) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            listener.onLoginListener(true);
                        }else{
                            listener.onLoginListener(false);
                            listener.onDataResultListener(task.getException().getMessage());
                        }
                    }
                });
        //if login failed check what was the readon (wrong pass, email etc)
    }

    protected void getPostData(final String postID){
        connectToPostFireStoreDatabase(postID);

        postFileRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document != null){
                    PostModel post = document.toObject(PostModel.class);
                    post.setPostID(postID);
                    listener.onReceivePostDataListener(post);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //listener.onReceivePostDataListener(null);
                Log.d("MyTAG", "Post Error: " + e.getMessage());

            }
        });
    }

    private void incrementData(final DocumentReference docRef, final String field){
        firebaseStore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docRef);
                long newLikes = snapshot.getLong(field) + 1;
                transaction.update(docRef, field, newLikes);

                //Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               // Log.d("MyTAG", "Giving a '"+field+"' operation succeed");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyTAG", field + " error: " + e.getMessage());
                //maybe show toast - operation didn't work
            }
        });
    }

    private void loadPicture(final ImageView profile) {
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Picasso.get().load(uri).into(profile);
                Picasso.get().load(uri).into(profile);
                //listener.onReceivePictureListener();
//                Bitmap bitmap = null;
//
//                Log.d("MyTAG", "url: "+ bitmap.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyTAG", "Error: "+e.getMessage());
            }
        });

    }

    private void getUserData(){
        userFileRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document != null){
                    int like_count = 0, follower_count = 0, post_count = 0;
                    String user_nick  = document.getString("Nick");
                    String user_email = document.getString("Email");
                    String user_phone = document.getString("Phone");
                    String user_desc  = document.getString("Description");

                    try {
                        like_count = (document.getLong("Likes")).intValue();
                        follower_count = (document.getLong("Followers")).intValue();
                        post_count = (document.getLong("Posts")).intValue();

                    }catch (NullPointerException e){
                        Log.d("MyTAG", "Error: " + e.getMessage());
                    }

                    AppUser appUser = new AppUser(user.getUid(), user_nick, user_email, user_phone, user_desc, follower_count, like_count, post_count);
                    listener.onReceiveUserDataListener(appUser);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onDataResultListener("Couldn't load data");
            }
        });
    }

    private void updateData(String field, String newData){
        //update current user data
        connectToUserFireStoreDatabase(user.getUid());

        userFileRef.update(field, newData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onDataResultListener("Profile updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onDataResultListener("Something went wrong :/");
                Log.d("MyTAG", "Error: "+e.getMessage());
            }
        });
    }


    public String getUserID(){
        return user.getUid();
    }

    public void setListener(ResultDataListener listener){
        this.listener = listener;
    }


}

