package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class ZoomEvent implements Parcelable
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

    protected ZoomEvent(Parcel in) {
        title = in.readString();
        roomCode = in.readString();
        course = in.readString();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(roomCode);
        dest.writeString(course);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(hour);
        dest.writeInt(minute);
    }

    public static final Creator<ZoomEvent> CREATOR = new Creator<ZoomEvent>() {
        @Override
        public ZoomEvent createFromParcel(Parcel in) {
            return new ZoomEvent(in);
        }

        @Override
        public ZoomEvent[] newArray(int size) {
            return new ZoomEvent[size];
        }
    };

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
