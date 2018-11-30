package com.csc301.team22.api;

import java.util.List;

public class Request {
    private int request_id, start_time, duration;
    private User customer;
    private User assigned_to;
    private double latitude, longitude;
    private boolean finished;
    private String description;
    private List<Response> responses;

    private Request(Builder builder) {
        this.request_id = builder.request_id;
        this.start_time = builder.start_time;
        this.duration = builder.duration;
        this.customer = builder.customer;
        this.assigned_to = builder.assigned_to;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.finished = builder.finished;
        this.description = builder.description;
        this.responses = builder.responses;
    }

    public Request() {}

    public int getRequest_id() {
        return request_id;
    }

    public int getStart_time() {
        return start_time;
    }

    public int getDuration() {
        return duration;
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

    public static class Builder {
        private Integer request_id = null, start_time = null, duration = null;
        private User customer = null;
        private User assigned_to = null;
        private Double latitude = null, longitude = null;
        private boolean finished = false;
        private String description = null;
        private List<Response> responses = null;

        public Builder request_id(int request_id) {
            this.request_id = request_id;
            return this;
        }

        public Builder start_time(int start_time) {
            this.start_time = start_time;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder customer(User customer) {
            this.customer = customer;
            return this;
        }

        public Builder assigned_to(User assigned_to) {
            this.assigned_to = assigned_to;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder finished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Request build() {
            if (request_id == null || start_time == null || duration == null || customer == null
                    || latitude == null || longitude == null || description == null) {
                throw new IllegalArgumentException("Fill all necessary Request fields");
            }

            return new Request(this);
        }
    }
}
