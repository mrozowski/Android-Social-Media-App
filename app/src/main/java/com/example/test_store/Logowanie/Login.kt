package com.example.test_store.Logowanie

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.test_store.R
import java.text.BreakIterator

class Login : AppCompatActivity(), LoginContract.View {

    lateinit var progressBar: ProgressBar
    lateinit var mPassword: EditText
    lateinit var mEmail: EditText
    private var presenter: LoginPresenter? = null
    var mLogin: Button? = null
    var mRegisterButton: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponents()
        presenter = LoginPresenter(this)
    }

    override fun onLoginClick(v: View?) {
        presenter!!.onLoginClicked()
    }

    override fun onRegisterClick(v: View?) {
        presenter!!.openRegisterActivity()
    }

    private fun initComponents() {
        mEmail = findViewById(R.id.email_field)
        mPassword = findViewById(R.id.pass_field)
        mLogin = findViewById(R.id.login_button)
        mRegisterButton = findViewById(R.id.have_account_button)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}