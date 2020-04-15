package com.example.myapplication;

public class DiscussionThread {
    private String threadName;
    private String associatedCourse;
    private String creatorUsername;

    public DiscussionThread(String threadName, String associatedCourse, String creatorUsername) {
        this.threadName = threadName;
        this.associatedCourse = associatedCourse;
        this.creatorUsername = creatorUsername;
    }

    public DiscussionThread(String threadName, String associatedCourse) {
        this.threadName = threadName;
        this.associatedCourse = associatedCourse;
        this.creatorUsername = ClientCommunicator.getUsername();
    }

    public String getAssociatedCourse() {
        return associatedCourse;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }
}