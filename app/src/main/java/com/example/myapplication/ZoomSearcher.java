package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ZoomSearcher {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static ArrayList<ZoomEvent> getMyZoomEvents() {
        ArrayList<ZoomEvent> results = new ArrayList<>();
        if (!ClientCommunicator.getZoomEvents()) {
            return results;
        }
        // Parse server response
        ArrayList<ZoomEvent> serverResponse = ServerResponseParser.parseZoomEvents();
        // Search
        for (ZoomEvent event : serverResponse) {
            if (event.getPoster().equals(ClientCommunicator.getUsername())) {
                results.add(event);
            }
        }
        return results;
    }
}
