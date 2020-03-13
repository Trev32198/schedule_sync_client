package com.example.myapplication;

import java.util.Date;

public class Event
{
    // Each event has a title and a date / time
    private String title;
    private Date datetime;
    // Need title and date / time to make an event
    public Event(String event_title, int year, int month, int day, int hour, int minute)
    {
        title = event_title;
        datetime = new Date(year, month, day, hour, minute);
    }
    // Accessor methods
    public String getTitle()
    {
        return title;
    }
    public Date getDateTime()
    {
        return datetime;
    }
}
