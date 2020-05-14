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
    private String poster;
    //added room code for the new zoom rooms
    // Need title and date / time to make an event
    ZoomEvent(String event_title, int year, int month, int day, int hour, int minute, String code, String courseID, String poster)
    {
        this.poster = poster;
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
    String getTitle()
    {
        return title;
    }

    int getDay() {
        return day;
    }

    int getMonth() {
        return month;
    }

    int getYear() {
        return year;
    }

    int getHour() {
        return hour;
    }

    int getMinute() {
        return minute;
    }
    String getCourse() {
        return course;
    }
    String getRoomCode() {
        return roomCode;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
