package com.example.myapplication;

public class MoodleAssignment
{
    private String name;
    // Unfortunately, java.lang.Date seems to require API 29
    // We are using an earlier API version for compatibility
    // and to workaround a couple other problems
    // So we implement our own rudimentary date format:
    private int year;
    private int month;
    private int day;
    public MoodleAssignment(String name, int year, int month, int day)
    {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public String getDateString()
    {
        return month + "/" + day + "/" + year;
    }
    public int getYear()
    {
        return year;
    }
    public int getMonth()
    {
        return month;
    }
    public int getDay()
    {
        return day;
    }
    public String getName()
    {
        return name;
    }
}
