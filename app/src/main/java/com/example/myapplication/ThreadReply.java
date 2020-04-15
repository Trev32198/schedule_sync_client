package com.example.myapplication;


// For usage only when retrieving replies from server
// We should not construct these when creating a new reply
public class ThreadReply {
    private String body;
    private String username;
    private CustomDateTime datetime;

    public ThreadReply(String txt, String usr, CustomDateTime datetime) {
        body = txt;
        username = usr;
        this.datetime = datetime;
    }

    public CustomDateTime getDatetime() {
        return datetime;
    }

    public String getUsername() {
        return username;
    }

    public String getBody() {
        return body;
    }

    public void editBody(String newTxt) {
        body = newTxt;
    }
}
