package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ExpandZoomEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_zoom_event);

        Intent intent = getIntent();
        ZoomEvent zoomEvent = intent.getParcelableExtra("Zoom Event");

        TextView zoomTitle, zoomCourse, zoomMonth, zoomDay, zoomYear, zoomHour, zoomMinute, zoomCode;

        zoomTitle = findViewById(R.id.zoomTitle);
        zoomCourse = findViewById(R.id.zoomCourse);
        zoomMonth = findViewById(R.id.zoomMonth);
        zoomDay = findViewById(R.id.zoomDay);
        zoomYear = findViewById(R.id.zoomYear);
        zoomHour = findViewById(R.id.zoomHour);
        zoomMinute = findViewById(R.id.zoomMinute);
        zoomCode = findViewById(R.id.zoomCode);

        assert zoomEvent != null;
        zoomTitle.setText(String.format("Title: %s", zoomEvent.getTitle()));
        zoomCourse.setText(String.format("Course: %s", zoomEvent.getCourse()));
        zoomMonth.setText(String.format("Month: %s", String.valueOf(zoomEvent.getMonth())));
        zoomDay.setText(String.format("Day: %s", String.valueOf(zoomEvent.getDay())));
        zoomYear.setText(String.format("Year: %s", String.valueOf(zoomEvent.getYear())));
        zoomHour.setText(String.format("Hour: %s", String.valueOf(zoomEvent.getHour())));
        zoomMinute.setText(String.format("Minute: %s", String.valueOf(zoomEvent.getMinute())));
        zoomCode.setText(String.format("Zoom Code: %s", zoomEvent.getRoomCode()));
    }
}
