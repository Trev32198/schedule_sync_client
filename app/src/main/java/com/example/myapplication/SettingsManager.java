package com.example.myapplication;

// Imports

import android.content.SharedPreferences;

public class SettingsManager
{
    // All methods now static, because only "one" SettingsManager
    // is enough for every part of the app
    // We just take the context / shareprefs of the LogIn activity
    // to do all of the work here
    // Store a specified username and password (the ones for server login)
    public static void storeCredentials(String username, String password)
    {
        SharedPreferences.Editor prefsEditor = LogIn.prefs.edit();
        prefsEditor.putString("username", username);
        prefsEditor.putString("password", password);
        prefsEditor.apply();
    }
    // Get Schedule.Sync username
    public static String getStoredUsername()
    {
        return LogIn.prefs.getString("username", "");
    }
    // Get Schedule.Sync password
    public static String getStoredPassword()
    {
        return LogIn.prefs.getString("password", "");
    }
    // Store specified Moodle username and password
    static void storeMoodleCredentials(String username, String password)
    {
        SharedPreferences.Editor prefsEditor = LogIn.prefs.edit();
        prefsEditor.putString("moodle_username", username);
        prefsEditor.putString("moodle_password", password);
        prefsEditor.apply();
    }
    // Get the stored Moodle username
    static String getMoodleUsername()
    {
        return LogIn.prefs.getString("moodle_username", "");
    }
    // Get the stored Moodle password
    static String getMoodlePassword()
    {
        return LogIn.prefs.getString("moodle_password", "");
    }
    // Store a specified Google username and password
    static void storeGoogleCredentials(String username, String password)
    {
        SharedPreferences.Editor prefsEditor = LogIn.prefs.edit();
        prefsEditor.putString("google_username", username);
        prefsEditor.putString("google_password", password);
        prefsEditor.apply();
    }
    // Get the stored Google username
    public static String getGoogleUsername()
    {
        return LogIn.prefs.getString("google_username", "");
    }
    // Get the stored Google password
    public static String getGooglePassword()
    {
        return LogIn.prefs.getString("google_password", "");
    }
}