package com.example.test_store.Register

import android.view.View

interface RegisterContract {
    interface View {
        fun onLoginClick(v: android.view.View?)
        fun onRegisterClick(v: android.view.View?)
        fun showToast(message: String?)
    }

    interface Presenter {
        fun onRegisterClicked()
        fun openLoginActivity()
    }
}