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
        zoomTitle.setText(zoomEvent.getTitle());
        zoomCourse.setText(zoomEvent.getCourse());
        zoomMonth.setText(zoomEvent.getMonth());
        zoomDay.setText(zoomEvent.getDay());
        zoomYear.setText(zoomEvent.getYear());
        zoomHour.setText(zoomEvent.getHour());
        zoomMinute.setText(zoomEvent.getMinute());
        zoomCode.setText(zoomEvent.getRoomCode());

    }
}
