package com.example.myapplication;

import java.util.ArrayList;

public class ServerResponseParser {
    // Parse the server's response to us asking for a list of zoom events
    // Currently this is in CSV format
    public static ArrayList<ZoomEvent> parseZoomEvents() {
        // First get the server's latest response
        // Assumes the server has already been contacted
        String serverResponse = ClientCommunicator.getLatestResult();
        if (serverResponse.equals("")) {
            System.out.println("Server response empty, no events to parse.");
            return new ArrayList<>();
        }
        // Split into individual lines
        String[] eventLines = serverResponse.split("\n");
        // Make an empty list
        ArrayList<ZoomEvent> events = new ArrayList<>();
        for (String eventLine : eventLines) {
            String[] values = eventLine.split(",");
            String[] dateTimeData = values[2].split(" ");
            String[] dateData = dateTimeData[0].split("-");
            String[] timeData = dateTimeData[1].split(":");
            int year = Integer.parseInt(dateData[0]);
            int month = Integer.parseInt(dateData[1]);
            int day = Integer.parseInt(dateData[2]);
            int hour = Integer.parseInt(timeData[0]);
            int minute = Integer.parseInt(timeData[1]);
            String poster = values[0];
            events.add(new ZoomEvent(values[3], year, month, day, hour, minute, values[1], values[4], values[0]));
        }
        return events;
    }

    static ArrayList<DiscussionThread> parseDiscussionThreads() {
        String serverResponse = ClientCommunicator.getLatestResult();
        System.out.println("Response to parse:");
        System.out.println(serverResponse);
        if (serverResponse.equals("")) {
            System.out.println("Server response empty, no threads to parse.");
            return new ArrayList<>();
        }
        String[] threadLines = serverResponse.split("\n");
        System.out.println("Lines of the CSV response:");
        for (String threadLine : threadLines) {
            System.out.println(threadLine);
        }
        ArrayList<DiscussionThread> threads = new ArrayList<>();
        for (String threadLine : threadLines) {
            String[] values = threadLine.split(",");
            System.out.println("Length of split values list for thread parsing is " + values.length);
            threads.add(new DiscussionThread(values[0], values[1], values[2]));
        }
        return threads;
    }

    static ArrayList<ThreadReply> parseReplies() {
        String serverResponse = ClientCommunicator.getLatestResult();
        if (serverResponse.equals("")) {
            System.out.println("Server response empty, no replies to parse.");
            return new ArrayList<>();
        }
        String[] replyLines = serverResponse.split("\n");
        ArrayList<ThreadReply> replies = new ArrayList<>();
        for (String replyLine : replyLines) {
            String[] values = replyLine.split(",");
            String[] dateTimeData = values[2].split(" ");
            String[] dateData = dateTimeData[0].split("-");
            String[] timeData = dateTimeData[1].split(":");
            int year = Integer.parseInt(dateData[0]);
            int month = Integer.parseInt(dateData[1]);
            int day = Integer.parseInt(dateData[2]);
            int hour = Integer.parseInt(timeData[0]);
            int minute = Integer.parseInt(timeData[1]);
            CustomDateTime dt = new CustomDateTime(year, month, day, hour, minute);
            replies.add(new ThreadReply(values[1], values[0], dt));
        }
        return replies;
    }
}
