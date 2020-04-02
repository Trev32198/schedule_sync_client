package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.ui.viewzoomevents.ViewZoomEventsFragment;

public class ViewZoomEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_zoom_events_activity);

        try {
            ClientCommunicator.getZoomEvents();
        }
        catch(Exception exception){
            System.out.println("what");
        }
        changeFragmentTextView(ClientCommunicator.getLatestResult());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ViewZoomEventsFragment.newInstance())
                    .commitNow();
        }
    }

    public void changeFragmentTextView(String s) {
        Fragment frag = getFragmentManager().findFragmentById(R.layout.view_zoom_events_fragment);
        ((TextView) frag.getView().findViewById(R.id.viewZoom)).setText(s);
    }

}
