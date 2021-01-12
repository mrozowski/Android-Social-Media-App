package com.example.test_store.Register

import android.content.Intent
import android.provider.Settings.Global.getString

import android.text.TextUtils
import android.view.View
import com.example.test_store.BottomNavigation
import com.example.test_store.Constants.*
import com.example.test_store.Database.Database
import com.example.test_store.Database.ResultDataListenerAdapter
import com.example.test_store.Logowanie.Login
import com.example.test_store.R


class RegisterPresenter(private val view: Register) : ResultDataListenerAdapter(), RegisterContract.Presenter {
    private val database: Database = Database()

    init {
        database.setListener(this)
    }
    override fun onRegisterClicked() {
        val nick = view.mNick!!.text.toString().trim { it <= ' ' }
        val email = view.mEmail!!.text.toString().trim { it <= ' ' }
        val password = view.mPassword!!.text.toString().trim { it <= ' ' }
        val phone = view.mPhone!!.text.toString().trim { it <= ' ' }

        //validation to the model
        if (!validateNick(nick)) return
        if (!validateEmail(email)) return
        if (!validatePassword(password)) return
        if (!validatePhone(phone)) return

        // If everything is good then create new user
        val newUser = RegisterModel(nick, email, phone)
        view.progressBar!!.visibility = View.VISIBLE

        //and register new user
        database.register(newUser, password)
    }

    override fun onDataResultListener(result: String) {
        view.showToast(result)
    }

    override fun onRegisterListener(isSuccess: Boolean) {
        if (isSuccess) {
            view.startActivity(Intent(view.applicationContext, BottomNavigation::class.java))
        }
    }

    fun isEmpty(string: String?): Boolean {
        return TextUtils.isEmpty(string)
    }

    fun validateNick(nick: String): Boolean {
        if (isEmpty(nick)) {
            view.mNick!!.error = view.getString(R.string.nick_is_empty)
            return false
        } else if (nick.length < 3) {
            view.mNick!!.error = view.getString(R.string.short_nick)
            return false
        } else if (nick.length > 15) {
            view.mNick!!.error = view.getString(R.string.long_nick)
            return false
        } else if (!nick.matches(NICK_REGEX.toRegex())) {
            view.mNick!!.error = view.getString(R.string.incorrect_nick)
            return false
        }
        view.mNick!!.error = null
        return true
    }

    fun validateEmail(email: String): Boolean {
        if (isEmpty(email)) {
            view.mEmail!!.error = view.getString(R.string.empty_email)
            return false
        } else if (!email.matches(EMAIL_REGEX.toRegex())) {
            view.mEmail!!.error = view.getString(R.string.incorrect_email)
            return false
        }
        return true
    }

    fun validatePassword(password: String): Boolean {
        if (isEmpty(password)) {
            view.mPassword!!.error = view.getString(R.string.empty_password)
            return false
        } else if (password.length < 8) {
            view.mPassword!!.error = view.getString(R.string.short_password)
            return false
        }
        //Minimum eight characters, at least one letter and one number:
        if (!password.matches(PASSWORD_REGEX.toRegex())) {
            view.mPassword!!.error = view.getString(R.string.incorrect_password)
            return false
        }
        return true
    }

    fun validatePhone(phone: String): Boolean {
        if (isEmpty(phone)) {
            view.mPhone!!.error = view.getString(R.string.empty_phone)
            return false
        } else if (!phone.matches(PHONE_REGEX.toRegex())) {
            view.mPhone!!.error = view.getString(R.string.incorrect_phone)
            return false
        }
        return true
    }

    override fun openLoginActivity() {
        view.startActivity(Intent(view.applicationContext, Login::class.java))
    }


}