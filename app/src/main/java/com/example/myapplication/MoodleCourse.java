package com.example.myapplication;

class MoodleCourse
{
    private String shortName;
    private String ID;

    MoodleCourse(String ID, String shortName)
    {
        this.shortName = shortName;
        this.ID = ID;
    }
    String getShortName()
    {
        return shortName;
    }

    String getID() {
        return ID;
    }
}
