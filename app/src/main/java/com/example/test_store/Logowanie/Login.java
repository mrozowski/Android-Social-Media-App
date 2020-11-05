package com.example.test_store.Logowanie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_store.R;

public class Login extends AppCompatActivity implements LoginContract.View{
    private LoginPresenter presenter;
    EditText mEmail, mPassword;
    Button mLogin;
    TextView mRegisterButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        presenter = new LoginPresenter(this);
    }

    @Override
    public void onLoginClick(View v) {
        presenter.onLoginClicked();
    }

    @Override
    public void onRegisterClick(View v) {
        presenter.openRegisterActivity();
    }

    private void initComponents() {
        mEmail = findViewById(R.id.email_field);
        mPassword = findViewById(R.id.pass_field);
        mLogin = findViewById(R.id.login_button);
        mRegisterButton = findViewById(R.id.have_account_button);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}