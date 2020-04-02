package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class CreateZoomMeeting extends AppCompatActivity {
    EditText day;
    Spinner month;
    Spinner year;
    Spinner hour;
    Spinner minute;
    Spinner second;
    Spinner classChoice;
    EditText eventName;
    EditText zoomCode;
    ZoomEvent zoomEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_zoom_meeting);
        addItemsOnSpinner2();
    }
    public void addItemsOnSpinner2() {
        Spinner classSpinner = findViewById(R.id.classChoose);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Collections.singletonList(Arrays.toString(MoodleAPI.getCourseList().toArray())));  //is this going to work?  i have no idea
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(dataAdapter);
    }
    public void createZoomEvent(View view){
        eventName = findViewById(R.id.eventName);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        second = findViewById(R.id.second);
        classChoice = findViewById(R.id.classChoose);
        zoomCode = findViewById(R.id.enterZoom);

        zoomEvent = new ZoomEvent(eventName.getText().toString(), Integer.parseInt(year.toString()),
                Integer.parseInt(month.toString()), Integer.parseInt(day.toString()),
                Integer.parseInt(hour.toString()), Integer.parseInt(minute.toString()),
                zoomCode.toString(), classChoice.toString());
        if (ClientCommunicator.postZoomEvent(zoomEvent)) {
            CreateZoomMeeting.this.finish();
        }
        else{
            startActivity(new Intent(CreateZoomMeeting.this, CreateZoomMeeting.class));
            CreateZoomMeeting.this.finish();
        }


    }





}
