package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void openSettings(View view){
        startActivity(new Intent(HomeScreen.this, Settings.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void syncCalendars(View view){
        Calendar.syncCalendars();

    }

    public void createZoomScreen(View view){
    }
}
