package com.example.myapplication;// Networking functionality

import android.os.StrictMode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

// ArrayList

public class ClientCommunicator
{
    // Declare necessary variables privately
    private static final String SUCCESS = "SUCCESS" + (char)(0x04);
    private static final String FAILURE = "FAILURE" + (char)(0x04);
    private static final int PORT = 24602;
    private static final String SERVER = "10.0.2.2";
    private static String username = "";
    private static String authType = "pw";
    private static String sq = "";
    private static String sqa = "";
    private static String authToken = "";
    private static String latestResult = "";
    // Sends command to server and gets its response
    private static String sendToServer(String command, String args[]) throws UnknownHostException, IOException
    {
        // To be able to do a little networking on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String toSend = command;
        for (String arg : args)
        {
            toSend += "\n" + arg;
        }
        // EOF marker
        toSend += (char)(0x04);
        System.out.println("About to connect");
        Socket connectionSocket = new Socket(SERVER, PORT);
        DataOutputStream dataOutStream = new DataOutputStream(connectionSocket.getOutputStream());
        DataInputStream dataInStream = new DataInputStream(connectionSocket.getInputStream());
        dataOutStream.write(toSend.getBytes("UTF-8"));
        ArrayList<Byte> byteString = new ArrayList<Byte>();
        while (true)
        {
            int b = dataInStream.read();
            if (b != -1)
            {
                byteString.add((byte)(b));
            }
            else
            {
                break;
            }
        }
        byte outputBytes[] = new byte[byteString.size()];
        for (int i = 0; i < byteString.size(); i++)
        {
            outputBytes[i] = byteString.get(i);
        }
        String output = new String(outputBytes, "UTF-8");
        dataInStream.close();
        dataOutStream.close();
        connectionSocket.close();
        return output;
    }
    // Allow the rest of the app to get the results returned from the server, minus
    // the SUCCESS, FAILURE, and EOF markers
    public static String getLatestResult()
    {
        System.out.println(latestResult);
        return latestResult.replace(SUCCESS, "").replace(FAILURE, "");
    }
    // A method for each of the commands the server supports
    // All commands / methods require authentication except createAccount
    // All commands / methods store their results in this->latestResult
    // All commands / methods return a boolean indicating success or failure
    public static boolean createAccount(String username2, String password2,
                              String sq2, String sqa2)
    {
        try
        {
            latestResult = sendToServer("CREATE ACCOUNT", new String[]{username, password2,
                                                  sq, sqa});
            System.out.println(latestResult);
            if (latestResult.contains(SUCCESS))
            {
                // If we make it here, the account was successfully created
                // Update the class's credential storage so that after the user
                // is made the account can be immediately used in authentication
                username = username2;
                sq = sq2;
                sqa = sqa2;
                authType = "pw";
                authToken = password2;
            }
            return latestResult.contains(SUCCESS);
        }
        catch (UnknownHostException e) {System.out.println(e); return false;}
        catch (IOException e) {System.out.println(e); return false;}
    }
    public static boolean changePW(String newPassword)
    {
        try
        {
            latestResult = sendToServer("CHANGE PASSWORD", new String[]{username, authToken, authType, newPassword});
            if (latestResult.contains(SUCCESS))
            {
                // If we make it here, we changed the password successfully,
                // so we should update the locally stored password
                authToken = newPassword;
            }
            return latestResult.contains(SUCCESS);
        }
        catch (UnknownHostException e) {return false;}
        catch (IOException e) {return false;}
    }
    public static boolean changeSQ(String newSQ, String newSQA)
    {
        try
        {
            latestResult = sendToServer("CHANGE SQ", new String[]{username, authToken, authType, newSQ, newSQA});
            if (latestResult.contains(SUCCESS))
            {
                // If we make it here, we changed the sq and sqa successfully
                // Change it client side too
                sq = newSQ;
                sqa = newSQA;
            }
            return latestResult.contains(SUCCESS);
        }
        catch (UnknownHostException e) {return false;}
        catch (IOException e) {return false;}
    }
    public static boolean deleteAccount()
    {
        try
        {
            latestResult = sendToServer("DELETE ACCOUNT", new String[]{username, authToken, authType});
            // If we make it here, the account has been deleted
            // We could reset the credentials in this instance, but trying to log in with empty
            // credentials will work no better than credentials that are no longer valid
            return latestResult.contains(SUCCESS);
        }
        catch (UnknownHostException e) {return false;}
        catch (IOException e) {return false;}
    }
    public static boolean searchUser(String query)
    {
        try
        {
            latestResult = sendToServer("SEARCH USER", new String[]{username, authToken, authType, query});
            return latestResult.contains(SUCCESS);
        }
        catch (UnknownHostException e) {return false;}
        catch (IOException e) {return false;}
    }

    public static boolean postZoomEvent(ZoomEvent event) {
        try {
            String dateString = event.getYear() + "-" + event.getMonth() + "-" + event.getDay();
            String timeString = event.getHour() + ":" + event.getMinute();
            latestResult = sendToServer("POST ZOOM EVENT", new String[]{username, authToken, authType, event.getTitle(), dateString, timeString, event.getRoomCode(), event.getCourse()});
            return latestResult.contains(SUCCESS);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean getZoomEvents() {
        try {
            // Log in
            MoodleAPI.setCredentials();
            MoodleAPI.checkCredentials();
            // First get a list of course IDs
            // So we can tell the server which course Zoom codes to return
            if (!MoodleAPI.fetchClassList()) {
                return false;
            }
            ArrayList<MoodleCourse> enrolledCourses = MoodleAPI.getCourseList();
            String enrolledCourseIDs = "";
            for (MoodleCourse course : enrolledCourses) {
                // Course IDs are numbers, send them all on
                // one line separated by commas
                // Last comma will be ignored by server
                enrolledCourseIDs += course.getShortName() + ",";
            }
            latestResult = sendToServer("GET ZOOM EVENTS", new String[]{username, authToken, authType, enrolledCourseIDs});
            return latestResult.contains(SUCCESS);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean deleteZoomEvent(String code) {
        try {
            latestResult = sendToServer("DELETE ZOOM EVENT", new String[]{username, authToken, authType, code});
            return latestResult.contains(SUCCESS);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean postNewThread(DiscussionThread thread) {
        try {
            latestResult = sendToServer("POST THREAD", new String[]{username, authToken, authType, thread.getThreadName(), thread.getAssociatedCourse()});
            return latestResult.contains(SUCCESS);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean getThreads() {
        try {
            // Log in
            MoodleAPI.setCredentials();
            MoodleAPI.checkCredentials();
            // First get a list of course IDs
            // So we can tell the server which course Zoom codes to return
            if (!MoodleAPI.fetchClassList()) {
                return false;
            }
            ArrayList<MoodleCourse> enrolledCourses = MoodleAPI.getCourseList();
            String enrolledCourseIDs = "";
            for (MoodleCourse course : enrolledCourses) {
                // Course IDs are numbers, send them all on
                // one line separated by commas
                // Last comma will be ignored by server
                enrolledCourseIDs += course.getShortName() + ",";
            }
            latestResult = sendToServer("GET THREADS", new String[]{username, authToken, authType, enrolledCourseIDs});
            return latestResult.contains(SUCCESS);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    // We do not specify a reply object here because it would be redundant
    // ClientCommunicator sends the username for authentication and the server
    // knows the right time to associate with the post
    // So, we only send the reply text
    public static boolean postReply(DiscussionThread thread, String replyText) {
        try {
            // Need to tell server info that identifies the thread in which we want to reply
            // We also need to specify reply data
            latestResult = sendToServer("POST REPLY", new String[]{username, authToken, authType, thread.getThreadName(), thread.getAssociatedCourse(),
                    replyText});
            return latestResult.contains(SUCCESS);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean getReplies(DiscussionThread thread) {
        try {
            latestResult = sendToServer("GET REPLIES", new String[]{username, authToken, authType, thread.getThreadName(), thread.getAssociatedCourse()});
            return latestResult.contains(SUCCESS);
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getUsername() {
        return username;
    }

    // Allow the user to set credentials and authentication type manually, just in case
    // security question authentication is needed
    public static void setCredentials(String username2, String authToken2,
                              String authType2)
    {
        username = username2;
        authToken = authToken2;
        authType = authType2;
    }
}
