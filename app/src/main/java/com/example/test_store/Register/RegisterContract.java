package com.example.test_store.Register;


public interface RegisterContract {
    interface View{
        void onLoginClick(android.view.View v);
        void onRegisterClick(android.view.View v);
        void showToast(String message);
    }

    interface Presenter{
        void onRegisterClicked();
        void openLoginActivity();
    }
}
