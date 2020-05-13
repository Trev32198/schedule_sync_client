package com.example.myapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;

public class DiscussionThread implements Parcelable {
    private String threadName;
    private String associatedCourse;
    private String creatorUsername;
    // Not set automatically, only used / set currently for sorting by time of last post
    // and in the expiration filter
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean setLastPostTime() {
        if (!ClientCommunicator.getReplies(this)) {
            return false;
        }
        ArrayList<ThreadReply> replies = ServerResponseParser.parseReplies();
        // Threads with no posts will be considered born today
        Date date = new Date();
        CustomDateTime lastPostTime = new CustomDateTime(0, 1, 1, 0, 0);
        for (ThreadReply reply : replies) {
            if (reply.getDatetime().comesAfter(lastPostTime)) {
                lastPostTime = reply.getDatetime();
            }
        }
        if (lastPostTime.getYear() == 0) {
            lastPostTime = new CustomDateTime(date.getYear() + 1900, date.getMonth() + 1,
                    date.getDate(), date.getHours(), date.getMinutes());
        }
        this.lastPostTime = lastPostTime;
        System.out.println("Set last post time: " + this.toString());
        return true;
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

    // For debug purposes
    @Override
    public String toString() {
        return this.threadName + " in course " + this.associatedCourse + " at time " + this.lastPostTime.toString();
    }
}