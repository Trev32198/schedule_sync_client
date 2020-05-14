package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExpandZoomEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_zoom_event);

        Intent intent = getIntent();
        ZoomEvent zoomEvent = intent.getParcelableExtra("Zoom Event");

        TextView zoomTitle, zoomCourse, zoomTime, zoomCode;

        zoomTitle = findViewById(R.id.zoomTitle);
        zoomCourse = findViewById(R.id.zoomCourse);
        zoomTime = findViewById(R.id.zoomTime);
        zoomCode = findViewById(R.id.zoomCode);

        assert zoomEvent != null;
        zoomTitle.setText(String.format("Title: %s", zoomEvent.getTitle()));
        zoomCourse.setText(String.format("Course: %s", zoomEvent.getCourse()));
        zoomTime.setText(String.format("When: %s at %s",
                zoomEvent.getYear() + "-" + zoomEvent.getMonth() + "-" + zoomEvent.getDay(),
                zoomEvent.getHour() + ":" + (zoomEvent.getMinute() < 10 ? "0" + zoomEvent.getMinute() : zoomEvent.getMinute())));

        zoomCode.setText(String.format("Zoom Code: %s", zoomEvent.getRoomCode()));
    }
}
