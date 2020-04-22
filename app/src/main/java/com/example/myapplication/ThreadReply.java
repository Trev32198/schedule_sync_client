package com.example.myapplication;


import android.os.Parcel;
import android.os.Parcelable;

// For usage only when retrieving replies from server
// We should not construct these when creating a new reply
public class ThreadReply implements Parcelable {
    private String body;
    private String username;
    private CustomDateTime datetime;

    ThreadReply(String txt, String usr, CustomDateTime datetime) {
        body = txt;
        username = usr;
        this.datetime = datetime;
    }

    protected ThreadReply(Parcel in) {
        body = in.readString();
        username = in.readString();
    }

    public static final Creator<ThreadReply> CREATOR = new Creator<ThreadReply>() {
        @Override
        public ThreadReply createFromParcel(Parcel in) {
            return new ThreadReply(in);
        }

        @Override
        public ThreadReply[] newArray(int size) {
            return new ThreadReply[size];
        }
    };

    CustomDateTime getDatetime() {
        return datetime;
    }

    String getUsername() {
        return username;
    }

    String getBody() {
        return body;
    }

    public void editBody(String newTxt) {
        body = newTxt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(username);
    }
}
