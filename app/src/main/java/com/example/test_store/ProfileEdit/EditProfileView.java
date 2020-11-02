package com.example.test_store.ProfileEdit;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test_store.Database.Database;
import com.example.test_store.Dialog.VerificationDialogListener;
import com.example.test_store.R;



public class EditProfileView extends Fragment implements VerificationDialogListener, EditProfileContract.View {
    String userID;
    EditProfilePresenter presenter;
    EditText nick, email, phone, desc, password;
    TextView userName;
    Button saveButton, backButton;
    ImageView profileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        initComponents(view);
        presenter = new EditProfilePresenter(this, new Database());
        setActions();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userID = bundle.getString("userID");
            String user_nick = bundle.getString("nick");
            String user_email = bundle.getString("email");
            String user_phone = bundle.getString("phone");
            String user_desc = bundle.getString("desc");

            userName.setText(user_nick);
            nick.setText(user_nick);
            email.setText(user_email);
            phone.setText(user_phone);
            desc.setText(user_desc);
        }
    }

    private void setActions() {
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


    private void initComponents(View view){
        userName = view.findViewById(R.id.user_name_ed);
        nick = view.findViewById(R.id.edit_user_name);
        email = view.findViewById(R.id.edit_user_email);
        phone = view.findViewById(R.id.edit_user_phone);
        desc = view.findViewById(R.id.edit_user_description);
        password = view.findViewById(R.id.edit_user_password);
        saveButton = view.findViewById(R.id.edit_user_save_button);
        backButton = view.findViewById(R.id.edit_user_cancel_button);
        profileImage = view.findViewById(R.id.user_avatar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    protected void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOkButtonListener(String password) {
        presenter.changeSensitiveData(password);
    }
}
