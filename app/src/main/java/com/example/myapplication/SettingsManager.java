package com.example.myapplication;

// Imports
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

// Must extend Activity to get a context later
public class SettingsManager extends Activity
{
    // For easy interaction with the App's private storage
    private SharedPreferences prefs;
    // Initialize by creating a SharedPreferences object referring to the app's data
    public SettingsManager()
    {
        prefs = this.getSharedPreferences("storage", Context.MODE_PRIVATE);
    }
    // Store a specified username and password (the ones for server login)
    public void storeCredentials(String username, String password)
    {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("username", username);
        prefsEditor.putString("password", password);
        prefsEditor.apply();
    }
    // Get Schedule.Sync username
    public String getStoredUsername()
    {
        return prefs.getString("username", "");
    }
    // Get Schedule.Sync password
    public String getStoredPassword()
    {
        return prefs.getString("password", "");
    }
    // Store specified Moodle username and password
    public void storeMoodleCredentials(String username, String password)
    {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("moodle_username", username);
        prefsEditor.putString("moodle_password", password);
        prefsEditor.apply();
    }
    // Get the stored Moodle username
    public String getMoodleUsername()
    {
        return prefs.getString("moodle_username", "");
    }
    // Get the stored Moodle password
    public String getMoodlePassword()
    {
        return prefs.getString("moodle_password", "");
    }
}