package com.example.test_store.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test_store.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeView extends Fragment {



    public HomeView() {
        // Required empty public constructor
    }


    public static HomeView newInstance() {
        HomeView fragment = new HomeView();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
}