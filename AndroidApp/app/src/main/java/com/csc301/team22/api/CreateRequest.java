package com.csc301.team22.api;


public class CreateRequest {
    private Integer start_time = null;
    private User customer;
    private int duration = 0;
    private double latitude = 0, longitude = 0;
    private String description = null;
    private String title = null;

    public CreateRequest(Builder builder) {
        this.start_time = builder.start_time;
        this.customer = builder.customer;
        this.duration = builder.duration;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.description = builder.description;
        this.title = builder.title;
    }

    public CreateRequest() {
    }

    public Integer getStart_time() {
        return start_time;
    }

    public User getUser() {
        return customer;
    }

    public int getDuration() {
        return duration;
    }

    public User getCustomer() {
        return customer;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private Integer start_time = null, duration = null;
        private User customer = null;
        private Double latitude = null, longitude = null;
        private String description = null;
        private String title = null;

        public Builder() {
        }

        public Builder title(String title) {
            this.title = title;
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

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public CreateRequest build() {

            if (start_time == null || customer == null
                    || duration == null || latitude == null ||
                    longitude == null || description == null) {
                throw new IllegalArgumentException("Only start_time in CreateRequest can be null");
            }

            return new CreateRequest(this);
        }
    }

}
