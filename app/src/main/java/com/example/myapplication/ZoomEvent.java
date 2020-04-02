package com.example.myapplication;

public class ZoomEvent
{
    // Each event has a title and a date / time
    private String title;
    private String roomCode;
    private String course;
    private int year, month, day;
    private int hour, minute;
    //added room code for the new zoom rooms
    // Need title and date / time to make an event
    public ZoomEvent(String event_title, int year, int month, int day, int hour, int minute, String code, String courseID)
    {
        title = event_title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        roomCode = code;
        course = courseID;
    }
    // Accessor methods
    public String getTitle()
    {
        return title;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
    public String getCourse() {
        return course;
    }
    public String getRoomCode() {
        return roomCode;
    }
}
