package com.example.myapplication;

import java.util.Date;

public class Event
{
    // Each event has a title and a date / time
    private String title;
    private Date datetime;
    private String rmCode;
    //added room code for the new zoom rooms
    // Need title and date / time to make an event
    public Event(String event_title, int year, int month, int day, int hour, int minute,String code)
    {
        title = event_title;
        datetime = new Date(year, month, day, hour, minute);
        rmCode = code;

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
    public String getRoomCode(){ return roomcode;}

}
