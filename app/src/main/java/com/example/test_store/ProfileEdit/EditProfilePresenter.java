package com.example.test_store.ProfileEdit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Dialog.VerificationDialog;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.example.test_store.Constants.IMG_INTEND_REQUEST_CODE;
import static com.example.test_store.Constants.USER_PROFILE_PICTURE_FILE;

public class EditProfilePresenter extends ResultDataListenerAdapter implements EditProfileContract.Presenter {

    private EditProfileView view; //reference for the view
    private EditProfileModel model;
    private StorageReference storageReference;
    private FirebaseFirestore fStorage;
    private DocumentReference fileRef;
    private Uri imageUrl;
    private String auth;
    private boolean isImageChanged = false;
    private boolean isDescChanged = false;
    private boolean isNickChanged = false;
    private boolean isPhoneChanged = false;
    private boolean isEmailChanged = false;
    private boolean isPasswordChanged = false;
    private Database database;

    public EditProfilePresenter(EditProfileView view) {
        this.view = view;
        database = new Database();
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
            if(validateDescription()) {
                String new_desc = view.desc.getText().toString().trim();
                database.changeDescription(new_desc);
            }
        }
        if(isNickChanged){
            if(validateNick()){
                String new_nick = view.nick.getText().toString().trim();
                database.changeNick(new_nick);
            }
        }
        if(isPhoneChanged){
            if(validatePhone()){
                String new_phone = view.phone.getText().toString().trim();
                database.changePhoneNumber(new_phone);
            }
        }
        if(isEmailChanged || isPasswordChanged){
            //Those are sensitives dates and firebase may require user to reLogin for verification
            //Instead of reLogin - app asks user to write password
            showDialogBox();
        }

        setDefaultFlags();
    }

    private void setDefaultFlags() {
        //isEmailChanged = false;
        //isPasswordChanged = false;
        isPhoneChanged = false;
        isNickChanged = false;
        isDescChanged = false;
        isImageChanged = false;
    }

    private boolean validateEmail() {
        //Note... add regex and then add it to Constant class
        String new_email = view.email.getText().toString().trim();
        if(new_email.length() < 5)
            return false;
        return true;

    }

    private boolean validatePhone() {
        String new_phone = view.phone.getText().toString().trim();
        //add regex later
        if(new_phone.length() == 9) return true;
        return false;
    }

    private boolean validateNick() {
        String new_nick = view.nick.getText().toString().trim();
        //update regex later
        if(new_nick.length() < 3) return false;
        if(!new_nick.matches("^[^0-9][^@# ]+$"))
            return false;
        return true;

    }

    private boolean validateDescription() {
        String new_desc = view.desc.getText().toString().trim();
        return new_desc.length() <= 255;
    }

    private boolean validatePassword() {
        String new_pass = view.password.getText().toString().trim();
        //Also add regex later and maybe toast or setError() to notify user what is wrong
        if(new_pass.length() < 8) return false;
        return true;
    }

    protected void changeSensitiveData(String auth){
        if(isEmailChanged){
            if(validateEmail()){
                String new_email = view.email.getText().toString().trim();
                database.changeEmail(new_email, auth);
                isEmailChanged = false;
            }
        }
        if(isPasswordChanged){
            if(validatePassword()){
                String new_pass = view.password.getText().toString().trim();
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
        //back to previous activity
        view.getActivity().finish();
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
