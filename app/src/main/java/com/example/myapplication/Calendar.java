package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;

public class Calendar {
    // Syncs Moodle and Google calendars
    // Returns boolean indicating success or failure
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean syncCalendars() {
        try {
            // First pull data from Moodle API
            // Credentials must have been set first
            if (!MoodleAPI.fetchClassList()) {
                return false;
            }
            ArrayList<MoodleAssignment> assignments = new ArrayList<MoodleAssignment>();
            // Loop over classes
            for (MoodleCourse course : MoodleAPI.getCourseList()) {
                // Fetch assignments for the course
                if (!MoodleAPI.fetchAssignment(course.getID())) {
                    return false;
                }
                // Add them to the list to sync
                assignments.addAll(MoodleAPI.getAssignmentList());
            }
            // Push the assignments to Google calendar
            for (MoodleAssignment assignment : assignments) {
                GoogleAPI.createNewEvent(assignment.getName(), assignment.getDateString(), assignment.getHour() + ":" + assignment.getMinute());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
