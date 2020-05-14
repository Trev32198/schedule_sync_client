package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExpandForum extends AppCompatActivity {

    DiscussionThread forumEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_forum);

        Intent intent = getIntent();
        forumEvent = intent.getParcelableExtra("ForumThread");

        TextView forumTitle;
        TextView forumCreator;
        TextView forumClass;

        forumTitle = findViewById(R.id.forumTitle);
        forumCreator = findViewById(R.id.forumCreator);
        forumClass = findViewById(R.id.forumClass);

        assert forumEvent != null;
        forumTitle.setText("Title: " + forumEvent.getThreadName());
        forumCreator.setText("Creator: " + forumEvent.getCreatorUsername());
        forumClass.setText("Course: " + forumEvent.getAssociatedCourse());

    }

    public void viewComments(View view){
        Intent intent2 = new Intent(ExpandForum.this, ReplyList.class);
        intent2.putExtra("ForumThread", forumEvent);
        startActivity(intent2);
    }
}
