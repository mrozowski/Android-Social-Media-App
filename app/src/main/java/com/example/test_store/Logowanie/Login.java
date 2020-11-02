package com.example.test_store.Logowanie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_store.Database.Database;
import com.example.test_store.R;

public class Login extends AppCompatActivity {
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
        setButtons();
        presenter = new LoginPresenter(this, new Database());
    }

    private void setButtons() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginClicked();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openRegisterActivity();
            }
        });
    }

    private void initComponents() {
        mEmail = findViewById(R.id.email_field);
        mPassword = findViewById(R.id.pass_field);
        mLogin = findViewById(R.id.login_button);
        mRegisterButton = findViewById(R.id.have_account_button);
        progressBar = findViewById(R.id.progressBar);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}