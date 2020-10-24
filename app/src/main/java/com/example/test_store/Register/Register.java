package com.example.test_store.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_store.BottomNavigation;
import com.example.test_store.Logowanie.Login;
import com.example.test_store.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
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
        setButtons();
    }

    private void setButtons() {
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterClicked();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });
    }

    private void initComponents() {
        mNick = findViewById(R.id.name_field);
        mEmail = findViewById(R.id.email_field);
        mPassword = findViewById(R.id.pass_field);
        mPhone = findViewById(R.id.phone_field);
        mRegister = findViewById(R.id.register_button);
        mLoginButton = findViewById(R.id.have_account_button);
        progressBar = findViewById(R.id.progressBar2);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}