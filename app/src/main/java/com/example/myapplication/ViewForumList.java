package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewForumList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ForumDataAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);


        ArrayList<DiscussionThread> forumEvents = new ArrayList<>();
        ClientCommunicator.getThreads();
        forumEvents = ServerResponseParser.parseDiscussionThreads();
        
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ForumDataAdapter(forumEvents);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final ArrayList<DiscussionThread> finalForumEvents = forumEvents;
        mAdapter.setOnItemClickListener(new ForumDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent2 = new Intent(ViewForumList.this, ExpandForum.class);
                intent2.putExtra("ForumThread", finalForumEvents.get(position));
                startActivity(intent2);
            }
        });
    }
}
