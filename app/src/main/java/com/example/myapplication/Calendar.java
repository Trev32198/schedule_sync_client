package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

class Calendar {
    // Syncs Moodle and Google calendars
    // Returns boolean indicating success or failure
    @RequiresApi(api = Build.VERSION_CODES.O)
    static boolean syncCalendars() {
        try {
            // First set credentials
            MoodleAPI.setCredentials();
            if (!MoodleAPI.checkCredentials()) {
                return false;
            }
            // Next pull data from Moodle API
            // Credentials must have been set first
            if (!MoodleAPI.fetchClassList()) {
                return false;
            }
            ArrayList<MoodleAssignment> assignments = new ArrayList<>();
            // Loop over classes
            for (MoodleCourse course : MoodleAPI.getCourseList()) {
                // Fetch assignments for the course
                if (MoodleAPI.fetchAssignment(course.getID())) {
                    // Add them to the list to sync
                    // Will not get here if unsuccessful or if no assignments
                    assignments.addAll(MoodleAPI.getAssignmentList());
                }
            }
            // Prepare GoogleAPI for usage
            if (!GoogleAPI.prepareAPI()) {
                System.out.println("Could not prepare GoogleAPI for usage.");
                return false;
            }
            // Push the assignments to Google calendar
            for (MoodleAssignment assignment : assignments) {
                GoogleAPI.createNewEvent(assignment.getName(), assignment.getDateString(), assignment.getHour() + ":" + assignment.getMinute());
            }
        } catch (IOException | GeneralSecurityException e) {
            return false;
        }
        return true;
    }
}
