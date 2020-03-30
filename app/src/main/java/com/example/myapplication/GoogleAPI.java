package com.example.myapplication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleAPI {
    private static final String CAL_APP_NAME = "Simple GCal API For Java";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String PATH_TO_TOKENS = "tokens";
    // get the scope of the application to obtain the required access permission
    // if this scope is changed at some point, the above tokens folder should be deleted to refresh tokens
    private static final List<String> CAL_APP_SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    // the path where generated oauth credentials will be stored
    private static final String PATH_TO_CREDENTIALS = "/credentials.json";

    private static Events events;
    private static Calendar calendarService;
    private static boolean eventExists = false;

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleAPI.class.getResourceAsStream(PATH_TO_CREDENTIALS);
        if (in == null) {
            throw new FileNotFoundException("Credential File '" + PATH_TO_CREDENTIALS + "' was not found");
        }

        // load the oauth2 credentials
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, CAL_APP_SCOPES)
                // below code line enables to store the access token locally on the machine
                // to enable user access the resource with just one time permission access
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(PATH_TO_TOKENS)))
                // if every time permission should be checked or new login to be done, delete the above line of code
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Obtains the upcoming count of events from user's calendar
     *
     * @throws IOException
     */
    private static boolean eventExists(String summary, String dateString, String timeString) throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        // get the events from the calendar service
        events = calendarService.events().list("primary")
                .setMaxResults(1024)    // these many only if there are more
                .setTimeMin(now)                // starting now
                .setOrderBy("startTime")        // order by their start time
                .setSingleEvents(true)            // each event individually
                .execute();                        // run the query
        List<Event> eventsList = events.getItems();

        // list down all the events found
        if (eventsList.isEmpty()) {
            System.out.println("\n****   No upcoming events found.   ****");
        } else {
            for (Event event : eventsList) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                // check if the event we are about to add next, is already existing in the list or not
                if (event.getSummary().equals(summary) && start.equals(new DateTime(dateString + "T" + timeString + ":00-04:00"))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add the new event with already defined static details to the user's calendar
     *
     * @throws IOException
     */
    // dateString is date as YYYY-MM-DD
    // timeString is time as HH:MM
    public static void createNewEvent(String summary, String dateString, String timeString) throws IOException {
        // Check if event exists first
        if (eventExists(summary, dateString, timeString)) {
            return;
        }
        // create a new event instance
        Event event = new Event()
                .setSummary(summary);    // set the title

        DateTime startDateTime = new DateTime(dateString + "T" + timeString + ":00-04:00");
        EventDateTime start = new EventDateTime()    // instantiate the new eventtime
                .setDateTime(startDateTime);    // set the start time
        event.setStart(start);                // set it rolling

        // reminder to be given
        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        // add the new event to the user's primary calendar
        String calendarId = "primary";
        event = calendarService.events().insert(calendarId, event).setSendNotifications(true).execute();
        System.out.printf("\nEvent created: %s\n", event.getHtmlLink());
    }
/*
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(CAL_APP_NAME)
                .build();

        // List the next 10 events from the primary calendar.
        getNextEvents(10);

        System.out.printf("\nNew event '%s' starting '%s' is about to be created\n", NEW_EVENT_SUMMARY, NEW_EVENT_START_TIME);
        if (eventExists) {
            System.out.println("\nThe event already exists...Not Required to add again.");
            return;
        }

        // Event does not exist, create the new event
        createNewEvent();
    }*/
}
