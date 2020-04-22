package com.example.myapplication;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MoodleAssignment
{
    private String name;
    private LocalDateTime ldt;

    @RequiresApi(api = Build.VERSION_CODES.O)
    MoodleAssignment(String name, long epochTime)
    {
        this.name = name;
        // Convert epoch time, UTC, to LocalDateTime
        this.ldt = LocalDateTime.ofEpochSecond(epochTime, 0, ZoneOffset.of("-04:00"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String getDateString() {
        return getYear() + "-" + getMonth() + "-" + getDay();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getYear() {
        return ldt.getYear();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getMonth() {
        return ldt.getMonthValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getDay() {
        return ldt.getDayOfMonth();
    }

    public String getName()
    {
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    int getHour() {
        return ldt.getHour();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    int getMinute() {
        return ldt.getMinute();
    }
}
