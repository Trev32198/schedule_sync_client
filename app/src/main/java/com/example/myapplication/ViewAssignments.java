package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class ViewAssignments extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AssignmentsDataAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_recycler);

        ArrayList<MoodleAssignment> moodleAssignments = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                moodleAssignments = MoodleAPI.getAllAssignments();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mRecyclerView = findViewById(R.id.assignmentRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AssignmentsDataAdapter(moodleAssignments);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AssignmentsDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                finish();
            }
        });
    }
}
