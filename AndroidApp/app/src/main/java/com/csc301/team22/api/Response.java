package com.csc301.team22.api;

public class Response {
    private int response_id;
    private User worker;
    private String comment;

    public int getResponse_id() {
        return response_id;
    }

    public User getWorker() {
        return worker;
    }

    public String getComment() {
        return comment;
    }
}
