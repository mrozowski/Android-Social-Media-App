package com.example.test_store.Register

import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.test_store.R

class Register : AppCompatActivity(), RegisterContract.View {
    private var presenter: RegisterPresenter? = null
    var mNick: EditText? = null
    var mEmail: EditText? = null
    var mPassword: EditText? = null
    var mPhone: EditText? = null
    var mRegister: Button? = null
    var mLoginButton: TextView? = null
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponents()
        presenter = RegisterPresenter(this)
        setEvents()
    }

    override fun onLoginClick(v: View?) {
        presenter!!.openLoginActivity()
    }

    override fun onRegisterClick(v: View?) {
        presenter!!.onRegisterClicked()
    }

    private fun setEvents() {
        mNick!!.onFocusChangeListener = nickValidateOnFocusChange
        mEmail!!.onFocusChangeListener = emailValidateOnFocusChange
        mPassword!!.onFocusChangeListener = passwordValidateOnFocusChange
        mPhone!!.onFocusChangeListener = phoneValidateOnFocusChange
    }

    private val nickValidateOnFocusChange = OnFocusChangeListener { v, hasFocus -> if (!hasFocus) presenter!!.validateNick(mNick!!.text.toString().trim { it <= ' ' }) }
    private val emailValidateOnFocusChange = OnFocusChangeListener { v, hasFocus -> if (!hasFocus) presenter!!.validateEmail(mEmail!!.text.toString().trim { it <= ' ' }) }
    private val passwordValidateOnFocusChange = OnFocusChangeListener { v, hasFocus -> if (!hasFocus) presenter!!.validatePassword(mPassword!!.text.toString().trim { it <= ' ' }) }
    private val phoneValidateOnFocusChange = OnFocusChangeListener { v, hasFocus -> if (!hasFocus) presenter!!.validatePhone(mPhone!!.text.toString().trim { it <= ' ' }) }

    private fun initComponents() {
        mNick = findViewById(R.id.name_field)
        mEmail = findViewById(R.id.email_field)
        mPassword = findViewById(R.id.pass_field)
        mPhone = findViewById(R.id.phone_field)
        mRegister = findViewById(R.id.register_button)
        mLoginButton = findViewById(R.id.have_account_button)
        progressBar = findViewById(R.id.progressBar2)
    }

    override fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}