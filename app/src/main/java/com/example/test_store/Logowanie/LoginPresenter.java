package com.example.test_store.Logowanie;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.test_store.BottomNavigation;
import com.example.test_store.Database.Database;
import com.example.test_store.Database.ResultDataListenerAdapter;
import com.example.test_store.Register.Register;

public class LoginPresenter extends ResultDataListenerAdapter {
    private Login view;
    private Database database;

    public LoginPresenter(Login view) {
        this.view = view;
        database = new Database();
        database.setListener(this);
        checkIfLoggedIn();
    }

    public void checkIfLoggedIn(){
        if(database.isLoggedIn()){
            view.startActivity(new Intent(view.getApplicationContext(), BottomNavigation.class));
            view.finish();
        }
    }

    private boolean validateEmail(String email){
        if(TextUtils.isEmpty(email)){
            view.mEmail.setError("Email is required");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password){
        if(TextUtils.isEmpty(password)){
            view.mPassword.setError("Password is required");
            return false;
        }
        //Minimum eight characters, at least one letter and one number:
        if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")){
            view.mPassword.setError("Wrong password");
            return false;
        }
        return true;
    }

    public void onLoginClicked() {
        String email = view.mEmail.getText().toString().trim();
        String password = view.mPassword.getText().toString().trim();

       if(!validateEmail(email)) return;
       if(!validatePassword(password)) return;

        view.progressBar.setVisibility(View.VISIBLE);
        database.login(email, password);
    }


    @Override
    public void onLoginListener(boolean isSuccess) {
        if(isSuccess){
            Toast.makeText(view, "Login successful", Toast.LENGTH_SHORT).show();
            view.startActivity(new Intent(view.getApplicationContext(), BottomNavigation.class));
        }
        else{
            //Toast.makeText(view., "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
            view.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataResultListener(String result) {
        view.showToast(result);
    }

    public void openRegisterActivity() {
        view.startActivity(new Intent(view.getApplicationContext(), Register.class));
    }
}
