package com.csc301.team22.api;


public class CreateRequest {
    private Integer start_time = null;
    private int duration = 0;
    private double latitude = 0, longitude = 0;
    private String description = null;


    CreateRequest() {
    }

    public class Builder {
        private Integer start_time = null, duration = null;
        private Double latitude = null, longitude = null;
        private String description = null;

        public Builder() {
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public CreateRequest build() {

            if (duration == null || latitude == null ||
                    longitude == null || description == null) {
                throw new IllegalArgumentException("Only start_time in CreateRequest can be null");
            }

            CreateRequest request = new CreateRequest();

            request.start_time = start_time;
            request.duration = duration;
            request.latitude = latitude;
            request.longitude = longitude;
            request.description = description;

            return request;
        }
    }

}
