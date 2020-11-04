package com.example.test_store.Logowanie;

import android.view.View;

public interface LoginContract {
    interface View{
        void onLoginClick(android.view.View v);
        void onRegisterClick(android.view.View v);
        void showToast(String message);
    }

    interface Presenter{
        void checkIfLoggedIn();
        boolean validateEmail(String email);
        boolean validatePassword(String password);
        void onLoginClicked();
        void openRegisterActivity();
    }
}
