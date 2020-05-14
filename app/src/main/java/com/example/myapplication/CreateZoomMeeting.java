package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class CreateZoomMeeting extends AppCompatActivity {
    EditText day;
    Spinner month;
    Spinner year;
    Spinner hour;
    Spinner minute;
    Spinner classChoice;
    EditText eventName;
    EditText zoomCode;
    ZoomEvent zoomEvent;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_zoom_meeting);
        addItemsOnSpinner2();
    }

    private static int convertMonthToInt(String monthName) {
        ArrayList<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months.indexOf(monthName) + 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addItemsOnSpinner2() {
        Spinner classSpinner = findViewById(R.id.classChoose);
        // Get course list from Moodle API

        if (!MoodleAPI.haveFetchedCourseList()) {
            MoodleAPI.setCredentials();
            MoodleAPI.checkCredentials();
            try {
                MoodleAPI.fetchClassList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> courseNames = new ArrayList<>();
        courseNames.add("Select Course");
        for (MoodleCourse course : MoodleAPI.getCourseList()) {
            courseNames.add(course.getShortName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                courseNames);  //is this going to work?  i have no idea
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(dataAdapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createZoomEvent(View view){
        eventName = findViewById(R.id.eventName);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        classChoice = findViewById(R.id.classChoose);
        zoomCode = findViewById(R.id.enterZoom);

        System.out.println("Day is set to: " + day.getText().toString());

        boolean proceed = true;

        if (month.getSelectedItem().toString().equals("Select Month") ||
                year.getSelectedItem().toString().equals("Select Year") ||
                hour.getSelectedItem().toString().equals("Select Hour") ||
                minute.getSelectedItem().toString().equals("Select Minute") ||
                classChoice.getSelectedItem().toString().equals("Select Course")) {
            proceed = false;
        } else if (eventName.getText().toString().equals("")) {
            eventName.setError("A name for the event is required");
            proceed = false;
        } else if (day.getText().toString().equals("")) {
            day.setError("A day is required");
            proceed = false;
        } else if (zoomCode.getText().toString().equals("")) {
            zoomCode.setError("A Zoom code is required");
            proceed = false;
        }

        if (proceed) {
            zoomEvent = new ZoomEvent(eventName.getText().toString(), Integer.parseInt(year.getSelectedItem().toString()),
                    convertMonthToInt(month.getSelectedItem().toString()), Integer.parseInt(day.getText().toString()),
                    Integer.parseInt(hour.getSelectedItem().toString()), Integer.parseInt(minute.getSelectedItem().toString()),
                    zoomCode.getText().toString(), classChoice.getSelectedItem().toString(), ClientCommunicator.getUsername());

            if (ClientCommunicator.postZoomEvent(zoomEvent)) {
                CreateZoomMeeting.this.finish();
            } else {
                startActivity(new Intent(CreateZoomMeeting.this, CreateZoomMeeting.class));
                CreateZoomMeeting.this.finish();
            }
        }
    }
}
