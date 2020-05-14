package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyZoomEvents extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyZoomDataAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        ArrayList<ZoomEvent> zoomEvents = ZoomSearcher.getMyZoomEvents();
        final ArrayList<ZoomEvent> finalZoom = zoomEvents;

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyZoomDataAdapter(zoomEvents);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyZoomDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MyZoomEvents.this, ExpandZoomEvent.class);
                intent.putExtra("Zoom Event", finalZoom.get(position));
                startActivity(intent);

            }
            public void onDeleteClick(int position) {
                ClientCommunicator.deleteZoomEvent(finalZoom.get(position).getRoomCode());
                finish();
                startActivity(new Intent(MyZoomEvents.this, MyZoomEvents.class));
            }
        });
    }
}
