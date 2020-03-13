import java.io.*;
import java.net.*;
import java.util.*;
import java.StringUtil.*;
//cid refers to the integer value moodle gives each course in the system. Returned by api calls. EX. example 00 has a CID of 2

public class moodleTalker {
    private string token;
    //user token for moodle, Will be needed to access moodle for other webservices besides the gettoken service
    private string usrn;
    //moodle username of user to be stored
    private string password;
    //moodle password of user to be stored

    public moodleTalker ( string pass, string usr) {
        //when creating a moodletalker object, which will be during registration, you need the username and password of user.
        usrn = usr;
        //assigning input username to usrn field
        password = pass;
        //assigning input password to password
    }
    public bool getTkn (){
        URL url = new URL("https://studentsync.moodlecloud.com/login/token.php?");
        //url of moodle webservices
        Map<String,Object> params = new LinkedHashMap<>();
        //hashmap of the key-value pairs
        params.put("service", "moodle_mobile_app");
        //webservice name for api
        params.put("username", usrn);
        //username key - value pair
        params.put("password", password);
        //password key value pair
        

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            //seperating the key value pairs
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            //in-between the key and values
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection connect = (HttpURLConnection)url.openConnection();
        connect.setRequestMethod("POST");
        connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connect.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connect.setDoOutput(true);
        connect.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
        //creating our buffer reader
        string rawTok = "";
        //string to start storing our output from this http post request
        for (int c; (c = in.read()) >= 0;)
            rawTok = rawTok + (char)c;
            //adding each new character to the string 
        try{
            tokn = StringUtils.substringBetween(rawTok, "{\"token\":/"",","\"privatetoken\":\"hUE1xvhAZBleNiwX8if3cPZ6Guzul10yHP9a5pa8GhI0gK0jo8Q2CtGqwVYAh0BK\"}");
            return true;
        }
        catch( Exception exception){
            return false;
        }
        //TBD: EXTRACTING THE USER TOKEN FROM THE RAW OUTPUT FROM THE HTTP REQUEST
            
    }
    
    public void getclsShrt(){
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
        string rawClass = "";
        for (int c; (c = in.read()) >= 0;)
            rawClass = rawClass + (char)c;
            
        
        //TBD:, EXTRACTING USER COURSES FROM RAW OUTPUT: key- value pair shortname : CID
    }
    public String[] returnClassList(){
        //TBD:GIVING US THE list of classes enrolled and CIDs

    }

    public String[] getAsmnt(string cid){
       
        URL url = new URL("https://studentsync.moodlecloud.com/webservice/rest/server.php?");
        Map<String,Object> params = new LinkedHashMap<>();
        
        params.put("wsfunction", "mod_assign_get_assignments");
        params.put("moodlewsrestformat", "json");
        params.put("wstoken", "a43238fbf237045ee8dcf55aecc024fa");
        params.put("courseids[0]", cid);
        
        

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
        for (int c; (c = in.read()) >= 0;)
            rawAs = rawAs + (char)c;
    
        //TBD: parse xml from assighnments list and return a key value pair of assignment name: datetime

    }
    public void returnAssignmentDat(){
        //TBD:this will run a check for each id using the cid and return the assignments from each
    }

    public void returnAsmnt(){

    }



}