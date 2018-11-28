package com.csc301.team22.api;

import java.util.List;

public class Request {
    private int request_id, customer_id, start_time, duration, assigned_to;
    private double latitude, longitude;
    private boolean finished;
    private String description;
    private List<Response> responses;

    public Request(){}
}
