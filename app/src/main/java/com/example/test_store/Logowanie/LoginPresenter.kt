package com.example.test_store.Logowanie

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.test_store.BottomNavigation
import com.example.test_store.Constants
import com.example.test_store.Database.Database
import com.example.test_store.Database.ResultDataListenerAdapter
import com.example.test_store.R
import com.example.test_store.Register.Register

class LoginPresenter : ResultDataListenerAdapter, LoginContract.Presenter {
    private var view: Login
    private var database: Database

    constructor(view: Login) {
        this.view = view
        database = Database()
        database.setListener(this)
        checkIfLoggedIn()
    }

    constructor(view: Login, db: Database) {
        this.view = view
        database = db
        database.setListener(this)
        checkIfLoggedIn()
    }

    override fun checkIfLoggedIn() {
        if (database.isLoggedIn) {
            view.startActivity(Intent(view.applicationContext, BottomNavigation::class.java))
            view.finish()
        }
    }

    override fun validateEmail(email: String?): Boolean {
        if (email != null) {
            return email.matches(Constants.EMAIL_REGEX.toRegex())
        }
        return false
    }

    override fun validatePassword(password: String?): Boolean {
        if (TextUtils.isEmpty(password)) {
            return false
        }
        //Minimum eight characters, at least one letter and one number:
        if (password != null) {
            return password.matches(Constants.PASSWORD_REGEX.toRegex())
        }
        return false
    }

    override fun onLoginClicked() {
        val email: String = view.mEmail.text.toString().trim { it <= ' ' }
        val password: String = view.mPassword.text.toString().trim { it <= ' ' }
        if (!validateEmail(email)) {
            view.mEmail.error = view.getString(R.string.incorrect_email)
            return
        }
        if (!validatePassword(password)) {
            view.mPassword.error = view.getString(R.string.incorrect_password)
            return
        }
        view.progressBar.visibility = View.VISIBLE
        database.login(email, password)
    }

    override fun onLoginListener(isSuccess: Boolean) {
        if (isSuccess) {
            Toast.makeText(view, view.getString(R.string.succesful_login), Toast.LENGTH_SHORT).show()
            view.startActivity(Intent(view.applicationContext, BottomNavigation::class.java))
        } else {
            view.progressBar.visibility = View.GONE
        }
    }

    override fun onDataResultListener(result: String) {
        view.showToast(result)
    }

    override fun openRegisterActivity() {
        view.startActivity(Intent(view.applicationContext, Register::class.java))
    }
}


