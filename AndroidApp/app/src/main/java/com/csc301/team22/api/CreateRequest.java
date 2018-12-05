package com.csc301.team22.api;


import com.google.gson.Gson;

public class CreateRequest {
    private Long start_time = null;
    private Integer duration = null;
    private Double latitude = null;
    private Double longitude = null;
    private String description = null;
    private String title = null;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static class Builder {
        private CreateRequest createRequest = new CreateRequest();

        public Builder title(String title) {
            createRequest.title = title;
            return this;
        }

        public Builder start_time(Long start_time) {
            createRequest.start_time = start_time;
            return this;
        }

        public Builder duration(int duration) {
            createRequest.duration = duration;
            return this;
        }

        public Builder latitude(double latitude) {
            createRequest.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            createRequest.longitude = longitude;
            return this;
        }

        public Builder description(String description) {
            createRequest.description = description;
            return this;
        }

        public CreateRequest build() {
            if (createRequest.title == null || createRequest.duration == null
                    || createRequest.latitude == null || createRequest.longitude == null
                    || createRequest.description == null) {
                throw new IllegalArgumentException("Only start_time in CreateRequest can be null");
            }

            return createRequest;
        }
    }

}
