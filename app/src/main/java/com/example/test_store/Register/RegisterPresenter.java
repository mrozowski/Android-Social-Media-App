package com.example.test_store.Register;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.test_store.BottomNavigation;
import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Logowanie.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterPresenter extends ResultDataListenerAdapter implements RegisterContract.Presenter{
    private Register view;
    private Database database;

    public RegisterPresenter(Register view) {
        this.view = view;
        database = new Database();
        database.setListener(this);
    }

    @Override
    public void onRegisterClicked() {
        String nick = view.mNick.getText().toString().trim();
        String email = view.mEmail.getText().toString().trim();
        String password = view.mPassword.getText().toString().trim();
        String phone = view.mPhone.getText().toString().trim();

        //validation to the model
        if(!validateNick(nick)) return;
        if(!validateEmail(email)) return;
        if(!validatePassword(password)) return;
        if(!validatePhone(phone)) return;

        // If everything is good then create new user
        RegisterModel newUser = new RegisterModel(nick, email, phone);
        view.progressBar.setVisibility(View.VISIBLE);

        //and register new user
        database.register(newUser, password);
    }

    @Override
    public void onDataResultListener(String result) {
        view.showToast(result);
    }

    @Override
    public void onRegisterListener(boolean isSuccess) {
        if (isSuccess) {
            view.startActivity(new Intent(view.getApplicationContext(), BottomNavigation.class));
        }
    }

    public boolean isEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    private boolean validateNick(String nick) {
        if(isEmpty(nick)){
            view.mNick.setError("Nick is required");
            return false;
        }
        else if(nick.length() < 3){
            view.mNick.setError("Nick is too short");
            return false;
        }
        else if(nick.length() > 15){
            view.mNick.setError("Nick is too long");
            return false;
        }
        else if(!nick.matches("^[a-zA-Z]\\w*$")){
            view.mNick.setError("Nick is incorrect");
            return false;
        }
        view.mNick.setError(null);
        return true;
    }

    private boolean validateEmail(String email){
        if(isEmpty(email)){
            view.mEmail.setError("Email is required");
            return false;
        }
        else if(!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            view.mEmail.setError("Email is incorrect");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password){
        if(isEmpty(password)){
            view.mPassword.setError("Password is required");
            return false;
        }
        else if(password.length() < 8){
            view.mPassword.setError("Password is too short");
            return false;
        }
        //Minimum eight characters, at least one letter and one number:
        if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")){
            view.mPassword.setError("Wrong password");
            return false;
        }
        return true;
    }

    private boolean validatePhone(String phone) {
        if(isEmpty(phone)){
            view.mPhone.setError("Phone is required");
            return false;
        }
        else if(!phone.matches("^[+0-9]{8,12}$")){
            view.mPhone.setError("Phone is incorrect");
            return false;
        }
        return true;
    }

    @Override
    public void openLoginActivity() {
        view.startActivity(new Intent(view.getApplicationContext(), Login.class));
    }
}
