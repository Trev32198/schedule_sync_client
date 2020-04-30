package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Sorter {
    private static String sortMode = "TIME";
    private static boolean reverse = true;

    static String getSortMode() {
        return sortMode;
    }

    static void setSortMode(String mode) {
        sortMode = mode;
    }

    static boolean getReverse() {
        return reverse;
    }

    static void setReverse(boolean sortInReverse) {
        reverse = sortInReverse;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static ArrayList<DiscussionThread> sort(ArrayList<DiscussionThread> threads) {
        switch (sortMode) {
            case "TIME":
                return sortThreadByPostTime(threads);
            case "NAME":
                return sortThreadsByName(threads);
            case "COURSE":
                return sortThreadsByCourse(threads);
            default:
                throw new IllegalStateException("Unexpected value: " + sortMode);
        }
    }

    private static ArrayList<DiscussionThread> sortThreadsByName(ArrayList<DiscussionThread> threads) {
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

    private static ArrayList<DiscussionThread> sortThreadsByCourse(ArrayList<DiscussionThread> threads) {
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
    private static ArrayList<DiscussionThread> sortThreadByPostTime(ArrayList<DiscussionThread> threads) {
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
