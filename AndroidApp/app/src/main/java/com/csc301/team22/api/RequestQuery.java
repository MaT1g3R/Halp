package com.csc301.team22.api;

public class RequestQuery {
    private boolean finished, assigned;
    private int starts_after, radius;
    private double latitude, longitude;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public int getStarts_after() {
        return starts_after;
    }

    public void setStarts_after(int starts_after) {
        this.starts_after = starts_after;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public RequestQuery (){}
}
