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
    private static ArrayList<String> courseList = new ArrayList<String>();
    private static ArrayList<MoodleAssignment> assignmentList = new ArrayList<MoodleAssignment>();
    private static HashMap<String,String> asmnt = new HashMap<String,String>();

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
            Pattern pattern = Pattern.compile("[.\\n]*\"token\"\\s*:\\s*\"(.*)\"[.\\n]*");
            Matcher matcher = pattern.matcher(rawTok);
            if (matcher.find()) {
                token = matcher.group(1);
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public static boolean fetchClassList() throws IOException {
        URL url = new URL("https://studentsync.moodlecloud.com/login/server.php/core_course_get_courses");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("token", token);

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
        ArrayList<String> newCourseList = new ArrayList<String>();
        try {
            // Class names look like "shortname" : "CID"
            Pattern pattern = Pattern.compile("[.\\n]*\"shortname\"\\s*:\\s*\"(.*)\"[.\\n]*");
            Matcher matcher = pattern.matcher(rawClass);
            if (matcher.find(0)) {
                newCourseList.add(matcher.group(1));
                while (matcher.find(matcher.end())) {
                    newCourseList.add(matcher.group(1));
                }
            } else {
                return false;
            }
            courseList = newCourseList;
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    // Return the list of courses
    public static ArrayList<String> getCourseList() {
        return courseList;
    }

    // Still WIP
    public static boolean fetchAssignment(String course) throws IOException {

        URL url = new URL("https://studentsync.moodlecloud.com/webservice/rest/server.php?");
        Map<String,Object> params = new LinkedHashMap<>();

        params.put("wsfunction", "mod_assign_get_assignments");
        params.put("moodlewsrestformat", "json");
        params.put("wstoken", token);
        params.put("courseids[0]", course);

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
        ArrayList<String> assilist = new ArrayList<String>();
        Arraylist<String> datelist = new ArrayList<String>();
        try {
            // Class names look like "shortname" : "CID"
            Pattern pattern = Pattern.compile("[.\\n]*\"name\"\\s*:\\s*\"(.*)\"[.\\n]*");
            Matcher matcher = pattern.matcher(rawAs);
            if (matcher.find(0)) {
                asslist.add(matcher.group(1));
                while (matcher.find(matcher.end())) {
                    assilist.add(matcher.group(1));
                }
            } else {
                return false;
            }
            courseList = newCourseList;
        } catch (Exception exception) {
            return false;
        }
        try {
            // Class names look like "shortname" : "CID"
            Pattern pattern = Pattern.compile("[.\\n]*\"duedate\"\\s*:\\s*\"(.*)\"[.\\n]*");
            Matcher matcher = pattern.matcher(rawAs);
            if (matcher.find(0)) {
                datelist.add(matcher.group(1));
                while (matcher.find(matcher.end())) {
                    datelist.add(matcher.group(1));
                }
            } else {
                return false;
            }
            courseList = newCourseList;
        } catch (Exception exception) {
            return false;
        }
        for(int i = 0; i < assilist.size(); i++)
        {
            assmnt.put(assilist.get(i),datelist.get(i));
        }




        //TBD: parse xml from assighnments list and return a key value pair of assignment name: datetime

        // Return false for now in the name of allowing compilation for testing
        return false;
    }

    // Get the list of assignments
    public static ArrayList<MoodleAssignment> getAssignmentList() {
        return assignmentList;
    }
}