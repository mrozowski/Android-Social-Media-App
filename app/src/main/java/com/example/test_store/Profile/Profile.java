package com.example.test_store.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_store.R;

import com.example.test_store.list.MyRecyclerViewAdapter;



public class Profile extends Fragment implements MyRecyclerViewAdapter.ItemClickListener, ProfileContract.View {

    TextView nick, likes, followers, posts, description, empty;
    ImageButton settings;
    ImageView profileImage;
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private ProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponents(view);
        presenter = new ProfilePresenter(this);

        settings.setOnClickListener(settingsListener);
        return view;
    }

    private View.OnClickListener settingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onSettingsClick(v);
        }
    };

    private void initComponents(View view){
        nick = view.findViewById(R.id.user_name);
        likes = view.findViewById(R.id.likes);
        posts = view.findViewById(R.id.posts_count);
        followers = view.findViewById(R.id.followers);
        description = view.findViewById(R.id.user_description);
        empty = view.findViewById(R.id.empty_post_list);
        settings = view.findViewById(R.id.settings_button);
        profileImage = view.findViewById(R.id.user_avatar);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onItemClick(View view, int position) {
        String postID = adapter.getItem(position);
        presenter.openPostFragment(postID);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}


