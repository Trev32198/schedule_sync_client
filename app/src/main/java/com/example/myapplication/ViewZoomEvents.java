package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.ui.viewzoomevents.ViewZoomEventsFragment;

public class ViewZoomEvents extends AppCompatActivity {
    TextView txt = findViewById(R.id.message);

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_zoom_events_activity);
        ClientCommunicator.getZoomEvents();
        txt.setText(ClientCommunicator.getLatestResult());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ViewZoomEventsFragment.newInstance())
                    .commitNow();
        }
    }
}
