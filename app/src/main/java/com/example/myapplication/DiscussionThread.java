package com.example.myapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

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
        // Threads with no posts will be considered very old
        CustomDateTime lastPostTime = new CustomDateTime(0, 1, 1, 0, 0);
        for (ThreadReply reply : replies) {
            if (reply.getDatetime().comesAfter(lastPostTime)) {
                lastPostTime = reply.getDatetime();
            }
        }
        this.lastPostTime = lastPostTime;
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
}