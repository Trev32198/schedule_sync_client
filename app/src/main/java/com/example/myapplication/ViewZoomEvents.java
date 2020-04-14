package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewZoomEvents extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        ArrayList<ZoomEvent> zoomEvents = new ArrayList<>();
        zoomEvents.add(new ZoomEvent("what", 1, 1, 1, 1, 1, "nono", "csc111"));
        zoomEvents.add(new ZoomEvent("who", 1, 1, 1, 1, 1, "yesyes", "csc111"));
        zoomEvents.add(new ZoomEvent("lol", 1, 1, 1, 1, 1, "haha", "csc111"));

        mRecyclerView = findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DataAdapter(zoomEvents);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
