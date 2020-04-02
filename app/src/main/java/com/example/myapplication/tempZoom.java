package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class tempZoom extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_zoom);
        TextView tempTxt = findViewById(R.id.enterZoomDataHere);
        try {
            ClientCommunicator.getZoomEvents();
            ClientCommunicator.getLatestResult();
        }
        catch (Exception exception){
            System.out.println("what");
        }
        finally {
            tempTxt.setText(ClientCommunicator.getLatestResult());

        }
        }
}
