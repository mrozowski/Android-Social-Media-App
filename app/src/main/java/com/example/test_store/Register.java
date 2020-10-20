package com.example.test_store;

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

import com.example.test_store.Profile.Profile;
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
    EditText mNick, mEmail, mPassword, mPhone;
    Button mRegister;
    TextView mLoginButton;
    String userID;
    ProgressBar progressBar;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNick = findViewById(R.id.name_field);
        mEmail = findViewById(R.id.email_field);
        mPassword = findViewById(R.id.pass_field);
        mPhone = findViewById(R.id.phone_field);
        mRegister = findViewById(R.id.register_button);
        mLoginButton = findViewById(R.id.have_account_button);
        progressBar = findViewById(R.id.progressBar2);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();



        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nick = mNick.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String phone = mPhone.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                //Minimum eight characters, at least one letter and one number:
                if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")){
                    mPassword.setError("Password must have minimum 8 characters,\ncontains at least one letter and one number");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // If everything is good then register new user
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();
                                    userID = mFirebaseAuth.getCurrentUser().getUid(); //take current user ID

                                    DocumentReference documentReference = mFireStore.collection("users").document(userID);  //add new user to the firestore database
                                    Map<String,Object> user = new HashMap<>();  //user attributes
                                    user.put("Nick", nick);
                                    user.put("Email", email);
                                    user.put("Phone", phone);
                                    user.put("Posts", 0);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("MyTAG","User "+userID+" was successfully added to the database");
                                        }
                                    });

                                    startActivity(new Intent(getApplicationContext(), Profile.class));
                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                    String error = Objects.requireNonNull(task.getException()).getMessage();
                                    Toast.makeText(Register.this, "Something went wrong " + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });
    }
}