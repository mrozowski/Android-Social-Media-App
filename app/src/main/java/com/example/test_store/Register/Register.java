package com.example.test_store.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.test_store.R;


public class Register extends AppCompatActivity implements RegisterContract.View{
    private RegisterPresenter presenter;
    EditText mNick, mEmail, mPassword, mPhone;
    Button mRegister;
    TextView mLoginButton;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
        presenter = new RegisterPresenter(this);
        setEvents();
    }

    @Override
    public void onLoginClick(View v) {
        presenter.openLoginActivity();
    }

    @Override
    public void onRegisterClick(View v) {
        presenter.onRegisterClicked();
    }

    private void setEvents() {
        mNick.setOnFocusChangeListener(validateOnFocusChange);
    }

    private View.OnFocusChangeListener validateOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus)
                presenter.validateNick(mNick.getText().toString().trim());
        }
    };

    private void initComponents() {
        mNick = findViewById(R.id.name_field);
        mEmail = findViewById(R.id.email_field);
        mPassword = findViewById(R.id.pass_field);
        mPhone = findViewById(R.id.phone_field);
        mRegister = findViewById(R.id.register_button);
        mLoginButton = findViewById(R.id.have_account_button);
        progressBar = findViewById(R.id.progressBar2);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}