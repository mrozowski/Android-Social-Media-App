package com.example.test_store.Logowanie

import android.view.View

interface LoginContract {
    interface View {
        fun onLoginClick(v: android.view.View?)
        fun onRegisterClick(v: android.view.View?)
        fun showToast(message: String?)
    }

    interface Presenter {
        fun checkIfLoggedIn()
        fun validateEmail(email: String?): Boolean
        fun validatePassword(password: String?): Boolean
        fun onLoginClicked()
        fun openRegisterActivity()
    }
}
