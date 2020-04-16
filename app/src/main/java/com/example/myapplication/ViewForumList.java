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

        final ArrayList<DiscussionThread> forumEvents = new ArrayList<>();
        //ClientCommunicator.getThreads();
        //forumEvents = ServerResponseParser.parseDiscussionThreads();
        forumEvents.add(new DiscussionThread( "string", "csc645"));
        forumEvents.add(new DiscussionThread( "string", "csc5"));
        forumEvents.add(new DiscussionThread( "string", "csc7745"));

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ForumDataAdapter(forumEvents);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ForumDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ViewForumList.this, ExpandForum.class);
                intent.putExtra("Forum", forumEvents.get(position));
                startActivity(intent);
            }
        });
    }
}
