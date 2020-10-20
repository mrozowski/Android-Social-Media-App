package com.example.test_store.ProfileEdit;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test_store.Dialog.VerificationDialogListener;
import com.example.test_store.R;



public class EditProfileView extends AppCompatActivity implements VerificationDialogListener {
    String userID;
    EditProfilePresenter presenter;
    EditText nick, email, phone, desc, password;
    TextView userName;
    Button saveButton, backButton;
    ImageView profileImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userID = getIntent().getStringExtra("userID");

        String user_nick = getIntent().getStringExtra("nick");
        String user_email = getIntent().getStringExtra("email");
        String user_phone = getIntent().getStringExtra("phone");
        String user_desc = getIntent().getStringExtra("desc");

        userName = findViewById(R.id.user_name_ed);
        nick = findViewById(R.id.edit_user_name);
        email = findViewById(R.id.edit_user_email);
        phone = findViewById(R.id.edit_user_phone);
        desc = findViewById(R.id.edit_user_description);
        password = findViewById(R.id.edit_user_password);

        saveButton = findViewById(R.id.edit_user_save_button);
        backButton = findViewById(R.id.edit_user_cancel_button);
        profileImage = findViewById(R.id.user_avatar);

        presenter = new EditProfilePresenter(this);

        userName.setText(user_nick);
        nick.setText(user_nick);
        email.setText(user_email);
        phone.setText(user_phone);
        desc.setText(user_desc);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveData();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeImage();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBack();
            }
        });
        desc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                presenter.descChanged();
                return true;
            }
        });
        phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                presenter.phoneChanged();
                return false;
            }
        });
        nick.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                presenter.nickChanged();
                return false;
            }
        });
        email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                presenter.emailChanged();
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                presenter.passwordChanged();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    protected void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOkButtonListener(String password) {
        presenter.changeSensitiveData(password);
    }
}
