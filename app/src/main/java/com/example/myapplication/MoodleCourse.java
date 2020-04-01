package com.example.myapplication;

public class MoodleCourse
{
    private String shortName;
    private String ID;

    public MoodleCourse(String ID, String shortName)
    {
        this.shortName = shortName;
        this.ID = ID;
    }
    public String getShortName()
    {
        return shortName;
    }

    public String getID() {
        return ID;
    }
}
