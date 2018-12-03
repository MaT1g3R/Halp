package com.csc301.team22.api;

import java.util.List;

public class JobRequest {
    private int request_id, duration;
    private long start_time;
    private User customer;
    private User assigned_to;
    private double latitude, longitude;
    private boolean finished;
    private String description;
    private List<Response> responses;
    private String title;

    public int getRequest_id() {
        return request_id;
    }

    public int getDuration() {
        return duration;
    }

    public long getStart_time() {
        return start_time;
    }

    public User getCustomer() {
        return customer;
    }

    public User getAssigned_to() {
        return assigned_to;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getDescription() {
        return description;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public String getTitle() {
        return title;
    }

}
