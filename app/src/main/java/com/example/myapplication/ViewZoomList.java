package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewZoomList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ZoomDataAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        final ArrayList<ZoomEvent> zoomEvents = new ArrayList<>();
        //ClientCommunicator.getZoomEvents();
        //zoomEvents = ServerResponseParser.parseZoomEvents();
        zoomEvents.add(new ZoomEvent("what", 1, 1, 1, 1, 1, "nono", "csc111"));
        zoomEvents.add(new ZoomEvent("who", 1, 1, 1, 1, 1, "yesyes", "csc111"));
        zoomEvents.add(new ZoomEvent("where", 1, 1, 1, 1, 1, "haha", "csc111"));

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ZoomDataAdapter(zoomEvents);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ZoomDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ViewZoomList.this, ExpandZoomEvent.class);
                intent.putExtra("Zoom Event", zoomEvents.get(position));
                startActivity(intent);
            }
        });{

        }


    }
}
