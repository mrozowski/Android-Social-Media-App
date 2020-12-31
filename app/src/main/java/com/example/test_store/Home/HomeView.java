package com.example.test_store.Home;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.test_store.R;


public class HomeView extends Fragment {

    private HomePresenter presenter;
    CardView cardView;
    RecyclerView recyclerView;
    EditText searchBar;
    Button searchButton;
    private View.OnClickListener searchButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchList();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        searchButton.setOnClickListener(searchButtonListener);
        presenter = new HomePresenter(this);
        return view;
    }

    private void initComponents(View view){
        cardView = view.findViewById(R.id.ad_container);
        recyclerView = view.findViewById(R.id.home_post_list_view);
        searchBar = view.findViewById(R.id.home_search_bar);
        searchButton = view.findViewById(R.id.button_search_list);
    }

    private void searchList(){
        String search = searchBar.getText().toString();
        if(!search.isEmpty()) {
            presenter.searchPosts(search);
        }
        else
            searchBar.setError("Field is empty");
    }
}