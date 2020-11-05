package com.example.test_store.OtherUserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_store.R;
import com.example.test_store.list.MyRecyclerViewAdapter;

public class OtherUserProfileView extends Fragment implements MyRecyclerViewAdapter.ItemClickListener, OtherUserProfileContract.View {
    TextView nick, likes, followers, posts, description;
    ImageView profileImage;
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private OtherUserProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_user_profile, container, false);
        initComponents(view);
        presenter = new OtherUserProfilePresenter(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String userID = bundle.getString("userID");
            presenter.getUser(userID);
        }
    }

    private void initComponents(View view){
        nick = view.findViewById(R.id.user_name);
        likes = view.findViewById(R.id.likes);
        posts = view.findViewById(R.id.posts_count);
        followers = view.findViewById(R.id.followers);
        description = view.findViewById(R.id.user_description);
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
