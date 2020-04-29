package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class CreateForum extends AppCompatActivity {

    TextView forumTitle;
    Spinner classChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_forum);

        forumTitle = findViewById(R.id.enterForumTitle);
        classChoice = findViewById(R.id.forumClassSpinner);

        addItemsOnSpinner2();
    }


    public void addItemsOnSpinner2() {
        Spinner classSpinner = findViewById(R.id.forumClassSpinner);
        // Get course list from Moodle API
        MoodleAPI.setCredentials();
        MoodleAPI.checkCredentials();
        try {
            MoodleAPI.fetchClassList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> courseNames = new ArrayList<>();
        for (MoodleCourse course : MoodleAPI.getCourseList()) {
            courseNames.add(course.getShortName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                courseNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(dataAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createForum(View view){

        DiscussionThread discussionThread = new DiscussionThread(forumTitle.getText().toString(),
                classChoice.getSelectedItem().toString());

        if (ClientCommunicator.postNewThread(discussionThread)) {
            CreateForum.this.finish();
        }
        else{
            startActivity(new Intent(CreateForum.this, CreateForum.class));
            CreateForum.this.finish();
        }
    }
}