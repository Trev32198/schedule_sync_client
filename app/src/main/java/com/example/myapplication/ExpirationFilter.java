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
        for (DiscussionThread thread : threads) {
            thread.setLastPostTime();
        }
        System.out.println("Filtering expired threads out of: " + threads);
        Date currentDate = new Date();
        System.out.println("Current date and time: " + (currentDate.getYear() + 1900) + "/" + (currentDate.getMonth() + 1) + "/" + currentDate.getDate()
                + " " + currentDate.getHours() + ":" + currentDate.getMinutes());
        int todayDays = 365 * (currentDate.getYear() + 1900) + daysUpTo(currentDate.getMonth() + 1) + currentDate.getDate();
        System.out.println("Current day estimation: " + todayDays);
        ArrayList<DiscussionThread> output = new ArrayList<>();
        for (DiscussionThread thread : threads) {
            thread.setLastPostTime();
            CustomDateTime threadDateTime = thread.getLastPostTime();
            // Make a rough estimate of the number of days between the last post in this thread
            // and today
            int threadDays = 365 * threadDateTime.getYear() + daysUpTo(threadDateTime.getMonth()) + threadDateTime.getDay();
            int dayDifference = todayDays - threadDays;
            System.out.println("Thread day estimate:" + threadDays + ", diff: " + dayDifference);
            if (dayDifference <= 14) {
                System.out.println("(Not Rejected)");
                output.add(thread);
            }
        }
        return output;
    }
}
