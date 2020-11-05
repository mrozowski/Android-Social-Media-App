package com.example.test_store.ProfileEdit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Dialog.VerificationDialog;

import com.example.test_store.Profile.Profile;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.example.test_store.Constants.EMAIL_REGEX;
import static com.example.test_store.Constants.IMG_INTEND_REQUEST_CODE;
import static com.example.test_store.Constants.NICK_REGEX;
import static com.example.test_store.Constants.PASSWORD_REGEX;
import static com.example.test_store.Constants.PHONE_REGEX;
import static com.example.test_store.Constants.USER_PROFILE_PICTURE_FILE;

public class EditProfilePresenter extends ResultDataListenerAdapter implements EditProfileContract.Presenter {

    private EditProfileView view; //reference for the view
    private EditProfileModel model;
    private Uri imageUrl;
    private boolean isImageChanged = false;
    private boolean isDescChanged = false;
    private boolean isNickChanged = false;
    private boolean isPhoneChanged = false;
    private boolean isEmailChanged = false;
    private boolean isPasswordChanged = false;
    private Database database;

    public EditProfilePresenter(EditProfileView view, Database db) {
        this.view = view;
        database = db;
        database.setListener(this);
        database.getCurrentUserProfilePicture(view.profileImage);
        //loadProfilePictureFromStorage();
    }

    private void loadProfilePictureFromStorage() {
        FileInputStream fileInputStream = null;
        Bitmap bitmap = null;
        try {
            File f = new File(view.getActivity().getFilesDir(), "profile.png");
            fileInputStream = view.getActivity().openFileInput(USER_PROFILE_PICTURE_FILE);
            //BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            view.profileImage.setImageBitmap(bitmap);
        } catch (IOException e)
        {
            e.printStackTrace();
            Log.d("MyTAG", "nie dziaa");
        }

    }

    @Override
    public void saveData() {
        if(isImageChanged && imageUrl !=null){
            database.updateProfilePictureOnFirebase(imageUrl);
        }
        if(isDescChanged){
            String new_desc = view.desc.getText().toString().trim();
            if(validateDescription(new_desc)) {
                database.changeDescription(new_desc);
            }
        }
        if(isNickChanged){
            String new_nick = view.nick.getText().toString().trim();
            if(validateNick(new_nick)){
                database.changeNick(new_nick);
            }
        }
        if(isPhoneChanged){
            String new_phone = view.phone.getText().toString().trim();
            if(validatePhone(new_phone))
                database.changePhoneNumber(new_phone);

        }
        if(isEmailChanged || isPasswordChanged){
            //Those are sensitives dates and firebase may require user to reLogin for verification
            //Instead of reLogin - app asks user to write password
            showDialogBox();
        }

        setDefaultFlags();
    }

    private void setDefaultFlags() {
        isPhoneChanged = false;
        isNickChanged = false;
        isDescChanged = false;
        isImageChanged = false;
    }

    public boolean validateEmail(String new_email) {
        return new_email.matches(EMAIL_REGEX);
    }

    public boolean validatePhone(String new_phone) {
        return !new_phone.matches(PHONE_REGEX);
    }

    public boolean validateNick(String new_nick) {
        if(new_nick.length() < 3) return false;
        return new_nick.matches(NICK_REGEX);
    }

    private boolean validateDescription(String new_desc) {
        return new_desc.length() <= 255;
    }

    private boolean validatePassword(String new_pass) {
        //Minimum eight characters, at least one letter and one number:
        return new_pass.matches(PASSWORD_REGEX);
    }

    protected void changeSensitiveData(String auth){
        if(isEmailChanged){
            String new_email = view.email.getText().toString().trim();
            if(validateEmail(new_email)){
                database.changeEmail(new_email, auth);
                isEmailChanged = false;
            }
        }
        if(isPasswordChanged){
            String new_pass = view.password.getText().toString().trim();
            if(validatePassword(new_pass)){
                database.changePassword(new_pass, auth);
                isPasswordChanged = false;
            }
        }
    }

    @Override
    public void changeImage() {
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        view.startActivityForResult(openGallery, IMG_INTEND_REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    private void showDialogBox() {
        VerificationDialog verificationDialog = new VerificationDialog();
        verificationDialog.show(view.getActivity().getSupportFragmentManager(), "Password Dialog");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMG_INTEND_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                imageUrl = data.getData();
                view.profileImage.setImageURI(imageUrl);
                isImageChanged = true;
            }
        }
    }

    public void onBack() {
        //back to profile activity
        view.getFragmentManager().beginTransaction()
                .replace(((ViewGroup)view.getView().getParent()).getId(), new Profile(), "profile")
                .addToBackStack(view.getClass().getName())
                .commit();
    }

    public void descChanged() {
        isDescChanged = true;
    }

    public void nickChanged() {
        isNickChanged = true;
    }

    public void phoneChanged() {
        isPhoneChanged = true;
    }

    public void emailChanged() {
        isEmailChanged = true;
    }

    public void passwordChanged() {
        isPasswordChanged = true;
    }

    @Override
    public void onDataResultListener(String result) {
        view.showToast(result);
    }

}
