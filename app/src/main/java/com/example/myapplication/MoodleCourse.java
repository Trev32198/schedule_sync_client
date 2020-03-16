package com.example.myapplication;

public class MoodleCourse
{
    private String shortName;
    private String cid;
    public MoodleCourse(String name, String id)
    {
        shortName = name;
        cid = id;
    }
    public String getShortName()
    {
        return shortName;
    }
    public String getCid()
    {
        return cid;
    }
}
