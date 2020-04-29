package com.example.myapplication;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoodleAPI {
    // Declare some attributes
    private static String token = "";
    private static String username = "";
    private static String password = "";
    private static ArrayList<MoodleCourse> courseList = new ArrayList<>();
    private static ArrayList<MoodleAssignment> assignmentList = new ArrayList<>();

    // To set the user's credentials before trying to pull data
    static void setCredentials() {
        username = SettingsManager.getMoodleUsername();
        password = SettingsManager.getMoodlePassword();
    }

    // To be able to go from course ID to shortname
    // Empty if no translation
    public static String translateIDToName(String courseID) {
        String result = "";
        for (MoodleCourse course : courseList) {
            if (course.getID().equals(courseID)) {
                result = course.getShortName();
                break;
            }
        }
        return result;
    }

    // And vice versa
    public static String translateNameToID(String name) {
        String result = "";
        for (MoodleCourse course : courseList) {
            if (course.getShortName().equals(name)) {
                result = course.getID();
                break;
            }
        }
        return result;
    }

    // Check credentials
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static boolean checkCredentials() {
        try {
            return getToken();
        } catch (IOException e) {
            return false;
        }
    }

    // To get a token from Moodle for future logins in this session
    // private because whether this needs to be called or not is
    // determined in this class's other methods (TODO)
    // Credentials must be set first
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean getToken() throws IOException {
        // To be able to do a little networking on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // The URL to connect to
        URL url = new URL("https://studentsync.moodlecloud.com/login/token.php?");
        // For the storage of POST arguments
        Map<String,Object> params = new LinkedHashMap<>();
        // Pretend to be the Moodle mobile app
        params.put("service", "moodle_mobile_app");
        // Specify our username
        params.put("username", username);
        // Specify our password
        params.put("password", password);

        // Put together an HTTP POST request
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            // Separate the key value pairs
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            // In-between the key and value
            postData.append('=');
            // The value
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        // Open the connection and send the POST request
        HttpURLConnection connect = (HttpURLConnection)url.openConnection();
        connect.setRequestMethod("POST");
        connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connect.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connect.setDoOutput(true);
        connect.getOutputStream().write(postDataBytes);

        // Get response
        Reader in = new BufferedReader(new InputStreamReader(connect.getInputStream(), StandardCharsets.UTF_8));
        // String to store POST response
        StringBuilder rawTok = new StringBuilder();
        for (int c = in.read(); c >= 0; c = in.read())
            rawTok.append((char) c);
        try {
            Pattern pattern = Pattern.compile(".*?\"token\"\\s*?:\\s*?\"(.*?)\".*?");
            Matcher matcher = pattern.matcher(rawTok.toString());
            if (matcher.find()) {
                token = matcher.group(1);
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
        // For debug purposes
        System.out.println("Token set to: " + token);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static boolean fetchClassList() throws IOException {
        // To be able to do a little networking on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL("https://studentsync.moodlecloud.com/webservice/rest/server.php");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("wstoken", token);
        params.put("moodlewsrestformat", "json");
        params.put("wsfunction", "core_course_get_courses_by_field");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        HttpURLConnection conner = (HttpURLConnection)url.openConnection();
        conner.setRequestMethod("POST");
        conner.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conner.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conner.setDoOutput(true);
        conner.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conner.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder rawClass = new StringBuilder();
        for (int c = in.read(); c >= 0; c = in.read())
            rawClass.append((char) c);
        // For debug purposes
        // System.out.println(rawClass);
        // To house new list of courses until parsing is done
        // When parsing is done and successful, the current list of courses
        // will be updated to be this list
        ArrayList<MoodleCourse> newCourseList = new ArrayList<>();
        try {
            // Extract course IDs and shortnames
            Pattern pattern = Pattern.compile(".*?\"id\"\\s*?:\\s*?([0-9]+).*?\"shortname\"\\s*?:\\s*?\"(.*?)\".*?");
            Matcher matcher = pattern.matcher(rawClass.toString());
            if (matcher.find(0)) {
                newCourseList.add(new MoodleCourse(matcher.group(1), matcher.group(2)));
                while (matcher.find(matcher.end())) {
                    newCourseList.add(new MoodleCourse(matcher.group(1), matcher.group(2)));
                }
            } else {
                System.out.println("Nothing found in data");
                return false;
            }
            courseList = newCourseList;
        } catch (Exception exception) {
            return false;
        }
        // For debug purposes
        System.out.println("Courses: ");
        for (MoodleCourse course : courseList)
            System.out.println("Course shortname, ID: " + course.getShortName() + ", " + course.getID());
        System.out.println();
        return true;
    }

    // Return the list of courses
    static ArrayList<MoodleCourse> getCourseList() {
        return courseList;
    }

    // Next two methods are now deprecated for out of class usage
    // Pull list of MoodleAssignments for specified course ID
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static boolean fetchAssignment(String courseID) throws IOException {
        // To be able to do a little networking on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL("https://studentsync.moodlecloud.com/webservice/rest/server.php?");
        Map<String,Object> params = new LinkedHashMap<>();

        params.put("wsfunction", "mod_assign_get_assignments");
        params.put("moodlewsrestformat", "json");
        params.put("wstoken", token);
        params.put("courseids[0]", courseID);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder rawAs = new StringBuilder();
        for (int c = in.read(); c >= 0; c = in.read())
            rawAs.append((char) c);
        // For debug purposes
        System.out.println(rawAs);

        ArrayList<MoodleAssignment> newAssignmentList = new ArrayList<>();
        try {
            // Extract assignment names, due dates / times
            // Ensure only assignments for current course are grabbed, just in case
            Pattern pattern = Pattern.compile("\"course\"\\s*?:\\s*?" + courseID + "\\s*?,.*?\"name\"\\s*?:\\s*?\"(.*?)\".*?\"duedate\"\\s*?:\\s*?([0-9]*)\\s*?,");
            Matcher matcher = pattern.matcher(rawAs.toString());
            if (matcher.find(0)) {
                // Each assignment needs a name, and the epoch time as given by Moodle
                newAssignmentList.add(new MoodleAssignment(matcher.group(1), Long.parseLong(Objects.requireNonNull(matcher.group(2)))));
                while (matcher.find(matcher.end())) {
                    newAssignmentList.add(new MoodleAssignment(matcher.group(1), Long.parseLong(Objects.requireNonNull(matcher.group(2)))));
                }
            } else {
                System.out.println("Nothing found in data");
                return false;
            }
            assignmentList = newAssignmentList;
        } catch (Exception exception) {
            return false;
        }
        // For debug purposes
        System.out.println("Assignments: ");
        for (MoodleAssignment assignment : assignmentList)
            System.out.println("Assignment name, due date: " + assignment.getName() + ", " + assignment.getDateString() + " at " + assignment.getHour() + ":" + assignment.getMinute());
        System.out.println();
        return true;
    }

    // Get the list of assignments
    private static ArrayList<MoodleAssignment> getAssignmentList() {
        return assignmentList;
    }


    // Get assignments associated with all classes
    @RequiresApi(api = Build.VERSION_CODES.O)
    static ArrayList<MoodleAssignment> getAllAssignments() throws IOException {
        ArrayList<MoodleAssignment> assignments = new ArrayList<>();
        // First set credentials
        MoodleAPI.setCredentials();
        if (!MoodleAPI.checkCredentials()) {
            return assignments;
        }
        // Next pull data from Moodle API
        // Credentials must have been set first
        if (!MoodleAPI.fetchClassList()) {
            return assignments;
        }
        // Loop over classes
        for (MoodleCourse course : MoodleAPI.getCourseList()) {
            // Fetch assignments for the course
            if (MoodleAPI.fetchAssignment(course.getID())) {
                // Add them to the list to sync
                // Will not get here if unsuccessful or if no assignments
                assignments.addAll(MoodleAPI.getAssignmentList());
            }
        }
        return assignments;
    }
}
