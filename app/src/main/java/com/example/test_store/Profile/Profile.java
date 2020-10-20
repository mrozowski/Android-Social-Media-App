package com.example.test_store.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_store.Login;
import com.example.test_store.R;
import com.example.test_store.list.ItemDetails;
import com.example.test_store.list.MyRecyclerViewAdapter;
import com.example.test_store.list.VerticalSpaceItemDecoration;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Profile extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    TextView nick, likes, followers, posts, description;
    ImageButton settings;
    ImageView profileImage;
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private ProfilePresenter presenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nick = findViewById(R.id.user_name);
        likes = findViewById(R.id.likes);
        posts = findViewById(R.id.posts_count);
        followers = findViewById(R.id.followers);
        description = findViewById(R.id.user_description);
        settings = findViewById(R.id.settings_button);
        profileImage = findViewById(R.id.user_avatar);

        recyclerView = findViewById(R.id.recyclerView);

        presenter = new ProfilePresenter(this);

        //temporary data - finally data will be taken from the database
        //ArrayList<ItemDetails> content = getSomeData();

        //presenter.loadProfileImage();
        presenter.connectUser();

        //presenter.loadUserPostList();

        //settings

        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit_profile:
                                presenter.openEditProfileActivity();
                                return true;
                            case R.id.menu_settings:
                                presenter.openSettingsActivity();
                                return true;
                            case R.id.menu_logout:
                                presenter.logout();
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.settings_menu);
                popupMenu.show();
            }
        });

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }


    public ArrayList<ItemDetails> getSomeData(){
        ArrayList<ItemDetails> animalNames = new ArrayList<>();
        animalNames.add(new ItemDetails("Lorem ipsum caxani", "author", 4, 2, "08.10.2020", "WIOEJJqgFLkCuU9mK0lq"));
        animalNames.add(new ItemDetails("Aliqua id fugiat nostrud irure ex", "author", 5, 21, "05.10.2020", "2"));
        animalNames.add(new ItemDetails("Fugiat nostrud irure ex ipsum", "author", 1, 2, "01.10.2020", "3"));
        animalNames.add(new ItemDetails("Lorem nostrud ipsum", "author", 5, 11, "28.09.2020", "4"));
        animalNames.add(new ItemDetails("Nistrup ipsum", "author", 0, 13, "08.09.2020", "5"));
        return animalNames;

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this,"Clicked " + adapter.getItemId(position), Toast.LENGTH_SHORT).show();
        String postID = adapter.getItem(position);
        presenter.openPostActivity(postID);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


