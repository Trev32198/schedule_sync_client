package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class DiscussionThread implements Parcelable {
    private String threadName;
    private String associatedCourse;
    private String creatorUsername;
    // Not set automatically, only used / set currently for sorting by time of last post
    private CustomDateTime lastPostTime;

    DiscussionThread(String threadName, String associatedCourse, String creatorUsername) {
        this.threadName = threadName;
        this.associatedCourse = associatedCourse;
        this.creatorUsername = creatorUsername;
    }

    DiscussionThread(String threadName, String associatedCourse) {
        this.threadName = threadName;
        this.associatedCourse = associatedCourse;
        this.creatorUsername = ClientCommunicator.getUsername();
    }

    private DiscussionThread(Parcel in) {
        threadName = in.readString();
        associatedCourse = in.readString();
        creatorUsername = in.readString();
    }

    public static final Creator<DiscussionThread> CREATOR = new Creator<DiscussionThread>() {
        @Override
        public DiscussionThread createFromParcel(Parcel in) {
            return new DiscussionThread(in);
        }

        @Override
        public DiscussionThread[] newArray(int size) {
            return new DiscussionThread[size];
        }
    };

    public CustomDateTime getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(CustomDateTime dateTime) {
        this.lastPostTime = dateTime;
    }

    String getAssociatedCourse() {
        return associatedCourse;
    }

    String getThreadName() {
        return threadName;
    }

    String getCreatorUsername() {
        return creatorUsername;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(threadName);
        dest.writeString(associatedCourse);
        dest.writeString(creatorUsername);
    }
}