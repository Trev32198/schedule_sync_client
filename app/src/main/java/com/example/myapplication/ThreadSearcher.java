package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ThreadSearcher {
    private static String searchMode = "NAME";

    public static String getSearchMode() {
        return searchMode;
    }

    public static void setSearchMode(String mode) {
        searchMode = mode;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static ArrayList<DiscussionThread> search(String query) {
        switch (searchMode) {
            case "NAME":
                return searchThreadsByName(query);
            case "COURSE":
                return searchThreadsByCourse(query);
            case "CREATOR":
                return searchThreadsByCreator(query);
            default:
                throw new IllegalStateException("Unexpected value: " + searchMode);
        }
    }

    // Do the search client side so we don't have to change the server
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static ArrayList<DiscussionThread> searchThreadsByName(String query) {
        // First get threads
        // If unsuccessful, return an empty list
        ArrayList<DiscussionThread> results = new ArrayList<>();
        if (!ClientCommunicator.getThreads()) {
            return results;
        }
        // Parse server response
        ArrayList<DiscussionThread> serverResponse = ServerResponseParser.parseDiscussionThreads();
        // Search
        for (DiscussionThread thread : serverResponse) {
            if (thread.getThreadName().toLowerCase().contains(query.toLowerCase())) {
                results.add(thread);
            }
        }
        return results;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static ArrayList<DiscussionThread> searchThreadsByCourse(String query) {
        // First get threads
        // If unsuccessful, return an empty list
        ArrayList<DiscussionThread> results = new ArrayList<>();
        if (!ClientCommunicator.getThreads()) {
            return results;
        }
        // Parse server response
        ArrayList<DiscussionThread> serverResponse = ServerResponseParser.parseDiscussionThreads();
        // Search
        for (DiscussionThread thread : serverResponse) {
            if (thread.getAssociatedCourse().toLowerCase().contains(query.toLowerCase())) {
                results.add(thread);
            }
        }
        return results;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static ArrayList<DiscussionThread> searchThreadsByCreator(String query) {
        // First get threads
        // If unsuccessful, return an empty list
        ArrayList<DiscussionThread> results = new ArrayList<>();
        if (!ClientCommunicator.getThreads()) {
            return results;
        }
        // Parse server response
        ArrayList<DiscussionThread> serverResponse = ServerResponseParser.parseDiscussionThreads();
        // Search
        for (DiscussionThread thread : serverResponse) {
            if (thread.getCreatorUsername().toLowerCase().contains(query.toLowerCase())) {
                results.add(thread);
            }
        }
        return results;
    }
}
