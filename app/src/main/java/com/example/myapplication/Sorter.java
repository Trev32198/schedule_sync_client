package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Sorter {
    static ArrayList<DiscussionThread> sortThreadsByName(ArrayList<DiscussionThread> threads, boolean reverse) {
        ArrayList<DiscussionThread> output = new ArrayList<>();
        if (threads.size() == 0) {
            return output;
        }
        for (int i = 0; i < threads.size(); i++) {
            String currentName = threads.get(i).getThreadName();
            int indexToInsert = 0;
            for (int k = 0; k < output.size(); k++) {
                if (currentName.compareTo(output.get(k).getThreadName()) <= 0) {
                    indexToInsert = k;
                    break;
                }
            }
            output.add(indexToInsert, threads.get(i));
        }
        if (reverse) {
            ArrayList<DiscussionThread> temp = new ArrayList<>();
            for (int i = output.size() - 1; i >= 0; i--) {
                temp.add(output.get(i));
            }
            output = temp;
        }
        return output;
    }

    static ArrayList<DiscussionThread> sortThreadsByCourse(ArrayList<DiscussionThread> threads, boolean reverse) {
        ArrayList<DiscussionThread> output = new ArrayList<>();
        if (threads.size() == 0) {
            return output;
        }
        for (int i = 0; i < threads.size(); i++) {
            String currentCourse = threads.get(i).getAssociatedCourse();
            int indexToInsert = 0;
            for (int k = 0; k < output.size(); k++) {
                if (currentCourse.compareTo(output.get(k).getAssociatedCourse()) <= 0) {
                    indexToInsert = k;
                    break;
                }
            }
            output.add(indexToInsert, threads.get(i));
        }
        if (reverse) {
            ArrayList<DiscussionThread> temp = new ArrayList<>();
            for (int i = output.size() - 1; i >= 0; i--) {
                temp.add(output.get(i));
            }
            output = temp;
        }
        return output;
    }

    // Be careful, this uses ClientCommunicator and therefore will change what getLastestResult returns
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static ArrayList<DiscussionThread> sortThreadByPostTime(ArrayList<DiscussionThread> threads, boolean reverse) {
        ArrayList<DiscussionThread> output = new ArrayList<>();
        // Get time of last post for every thread
        // Insert each in correct location in output
        for (DiscussionThread thread : threads) {
            thread.setLastPostTime();
            int lastAcceptableIndex = 0;
            for (int i = 0; i < output.size(); i++) {
                if (thread.getLastPostTime().comesAfter(output.get(i).getLastPostTime())) {
                    lastAcceptableIndex = i;
                }
            }
            output.add(lastAcceptableIndex, thread);
        }
        // Reverse if needed
        if (reverse) {
            ArrayList<DiscussionThread> temp = new ArrayList<>();
            for (int k = output.size() - 1; k >= 0; k--) {
                temp.add(output.get(k));
            }
            output = temp;
        }
        return output;
    }
}
