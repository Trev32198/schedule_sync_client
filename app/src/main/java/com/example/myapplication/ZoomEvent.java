package com.example.myapplication;

import java.util.Date;

public class ZoomEvent
{
    // Each event has a title and a date / time
    private String title;
    private Date datetime;
    private String roomCode;
    private String course;
    //added room code for the new zoom rooms
    // Need title and date / time to make an event
    public ZoomEvent(String event_title, int year, int month, int day, int hour, int minute, String code, String courseID)
    {
        title = event_title;
        datetime = new Date(year, month, day, hour, minute);
        roomCode = code;
        course = courseID;
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

    public String getCourse() {
        return course;
    }
    public String getRoomCode() {
        return roomCode;
    }
}