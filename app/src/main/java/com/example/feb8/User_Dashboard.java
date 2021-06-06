package com.example.feb8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.feb8.model.Habits;
import com.example.feb8.model.RoomDB;
import com.example.feb8.viewModel.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class User_Dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    View rootView;

    List<Habits> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__dashboard);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //initialize database
        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();

        //initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //initialize adapter
        adapter = new MainAdapter(User_Dashboard.this,dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

    }
}