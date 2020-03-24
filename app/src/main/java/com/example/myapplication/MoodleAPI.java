package com.example.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//cid refers to the integer value moodle gives each course in the system. Returned by api calls. EX. example 00 has a CID of 2

public class MoodleAPI {
    // Declare some attributes
    private static String token = "";
    private static String username = "";
    private static String password = "";
    private static ArrayList<MoodleCourse> courseList = new ArrayList<MoodleCourse>();
    private static ArrayList<MoodleAssignment> assignmentList = new ArrayList<MoodleAssignment>();

    // To set the user's credentials before trying to pull data
    public static void setCredentials() {
        SettingsManager mgr = new SettingsManager();
        username = mgr.getMoodleUsername();
        password = mgr.getMoodlePassword();
    }

    // Check credentials
    public static boolean checkCredentials() {
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
    private static boolean getToken() throws IOException {
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
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        // Open the connection and send the POST request
        HttpURLConnection connect = (HttpURLConnection)url.openConnection();
        connect.setRequestMethod("POST");
        connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connect.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connect.setDoOutput(true);
        connect.getOutputStream().write(postDataBytes);

        // Get response
        Reader in = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
        // String to store POST response
        String rawTok = "";
        for (int c = in.read(); c >= 0; c = in.read())
            rawTok = rawTok + (char)c;
        try {
            Pattern pattern = Pattern.compile(".*?\"token\"\\s*?:\\s*?\"(.*?)\".*?");
            Matcher matcher = pattern.matcher(rawTok);
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

    public static boolean fetchClassList() throws IOException {
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
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conner = (HttpURLConnection)url.openConnection();
        conner.setRequestMethod("POST");
        conner.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conner.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conner.setDoOutput(true);
        conner.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conner.getInputStream(), "UTF-8"));
        String rawClass = "";
        for (int c = in.read(); c >= 0; c = in.read())
            rawClass = rawClass + (char)c;
        // To house new list of courses until parsing is done
        // When parsing is done and successful, the current list of courses
        // will be updated to be this list
        ArrayList<MoodleCourse> newCourseList = new ArrayList<MoodleCourse>();
        try {
            // Extract course IDs and shortnames
            Pattern pattern = Pattern.compile(".*?\"id\"\\s*?:\\s*?([0-9]+).*?\"shortname\"\\s*?:\\s*?\"(.*?)\".*?");
            Matcher matcher = pattern.matcher(rawClass);
            if (matcher.find(0)) {
                newCourseList.add(new MoodleCourse(matcher.group(1), matcher.group(2)));
                while (matcher.find(matcher.end())) {
                    newCourseList.add(new MoodleCourse(matcher.group(1), matcher.group(2)));
                }
            } else {
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
    public static ArrayList<MoodleCourse> getCourseList() {
        return courseList;
    }

    // Still WIP
    public static boolean fetchAssignment(String courseID) throws IOException {

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
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String rawAs = "";
        for (int c = in.read(); c >= 0; c = in.read())
            rawAs = rawAs + (char)c;
        // For debug purposes
        System.out.println(rawAs);

        //TBD: parse xml from assighnments list and return a key value pair of assignment name: datetime

        // Return false for now in the name of allowing compilation for testing
        return false;
    }

    // Get the list of assignments
    public static ArrayList<MoodleAssignment> getAssignmentList() {
        return assignmentList;
    }
}