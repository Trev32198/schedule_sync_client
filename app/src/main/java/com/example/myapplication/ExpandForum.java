package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExpandForum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_forum);

        Intent intent = getIntent();
        DiscussionThread forumEvent = intent.getParcelableExtra("ForumThread");

        TextView forumTitle;
        TextView forumCreator;
        TextView forumClass;

        forumTitle = findViewById(R.id.forumTitle);
        forumCreator = findViewById(R.id.forumCreator);
        forumClass = findViewById(R.id.forumClass);

        assert forumEvent != null;
        forumTitle.setText(forumEvent.getThreadName());
        forumCreator.setText(forumEvent.getCreatorUsername());
        forumClass.setText(forumEvent.getAssociatedCourse());

    }

    public void viewComments(View view){
        startActivity(new Intent(ExpandForum.this, ReplyList.class ));
    }
}
