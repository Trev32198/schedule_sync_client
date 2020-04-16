package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReplyList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ReplyDataAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    TextView reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        final ArrayList<ThreadReply> threadReplies = new ArrayList<>();
        //ClientCommunicator.getReplies();
        //threadReplies = ServerResponseParser.parseReplies();
        reply = findViewById(R.id.replyBody);

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ReplyDataAdapter(threadReplies);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void sendReply(View view){
        Intent intent = getIntent();
        DiscussionThread forumEvent = intent.getParcelableExtra("Forum");
        assert forumEvent != null;
        ClientCommunicator.postReply(forumEvent, reply.toString());

    }
}
