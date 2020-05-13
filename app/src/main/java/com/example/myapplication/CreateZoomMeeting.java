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
    Spinner second;
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
        MoodleAPI.setCredentials();
        MoodleAPI.checkCredentials();
        try {
            MoodleAPI.fetchClassList();
        } catch (IOException e) {
            e.printStackTrace();
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
        second = findViewById(R.id.second);
        classChoice = findViewById(R.id.classChoose);
        zoomCode = findViewById(R.id.enterZoom);

        zoomEvent = new ZoomEvent(eventName.getText().toString(), Integer.parseInt(year.getSelectedItem().toString()),
                convertMonthToInt(month.getSelectedItem().toString()), Integer.parseInt(day.getText().toString()),
                Integer.parseInt(hour.getSelectedItem().toString()), Integer.parseInt(minute.getSelectedItem().toString()),
                zoomCode.getText().toString(), classChoice.getSelectedItem().toString());
        System.out.println("Day is set to: " + day.getText().toString());

        if (month.getSelectedItem().toString().equals("Select Month")){
            month.setSelection(1);
        }
        if (year.getSelectedItem().toString().equals("Select Year")){
            year.setSelection(1);
        }
        if (hour.getSelectedItem().toString().equals("Select Hour")){
            hour.setSelection(1);
        }
        if (minute.getSelectedItem().toString().equals("Select Minute")){
            minute.setSelection(1);
        }
        if (second.getSelectedItem().toString().equals("Select Second")){
            second.setSelection(1);
        }
        if (classChoice.getSelectedItem().toString().equals("Select Class")){
            classChoice.setSelection(1);
        }
        if (ClientCommunicator.postZoomEvent(zoomEvent)) {
            CreateZoomMeeting.this.finish();
        }
        else{
            startActivity(new Intent(CreateZoomMeeting.this, CreateZoomMeeting.class));
            CreateZoomMeeting.this.finish();
        }
    }
}
