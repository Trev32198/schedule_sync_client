package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;

public class ExpirationFilter {
    private static final int[] monthSizes = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static int daysUpTo(int month) {
        int sum = 0;
        for (int i = 0; i < month - 1; i++) {
            sum += monthSizes[i];
        }
        return sum;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static ArrayList<DiscussionThread> removeExpired(ArrayList<DiscussionThread> threads) {
        ArrayList<DiscussionThread> output = new ArrayList<>();
        for (DiscussionThread thread : threads) {
            thread.setLastPostTime();
            CustomDateTime threadDateTime = thread.getLastPostTime();
            // Make a rough estimate of the number of days between the last post in this thread
            // and today
            Date currentDate = new Date();
            int threadDays = 365 * threadDateTime.getYear() + daysUpTo(threadDateTime.getMonth()) + threadDateTime.getDay();
            int todayDays = 365 * currentDate.getYear() + daysUpTo(currentDate.getMonth()) + currentDate.getDay();
            int dayDifference = todayDays - threadDays;
            if (dayDifference <= 14) {
                output.add(thread);
            }
        }
        return output;
    }
}
