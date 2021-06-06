package com.example.feb8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feb8.model.Habits;
import com.example.feb8.model.RoomDB;
import com.example.feb8.viewModel.MainAdapter;

import java.util.ArrayList;
import java.util.List;


public class Dashboard extends Fragment {

    RecyclerView recyclerView;
    View rootView;

    List<Habits> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        //initialize database
        database = RoomDB.getInstance(getContext());
        dataList = database.mainDao().getAll();

        //initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(getContext());
        //set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //initialize adapter
        //adapter = new MainAdapter(rootView,dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}