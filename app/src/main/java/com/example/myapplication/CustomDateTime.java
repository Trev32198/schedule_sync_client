package com.example.myapplication;

// The built in LocalDateTime seems a bit complex for our purposes if we don't
// need to convert an epoch time to standard time
// The built in Date class has proved buggy in our work before
// Here's a custom date implementation to put an end to those troubles:

public class CustomDateTime {
    private int year, month, day, hour, minute;

    CustomDateTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String toString() {
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }
}
