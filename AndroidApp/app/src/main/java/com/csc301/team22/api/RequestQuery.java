package com.csc301.team22.api;

import java.util.HashMap;

public class RequestQuery {
    private Boolean finished = null, assigned = null;
    private Integer starts_after = null, radius = null;
    private Double latitude = null, longitude = null;

    public RequestQuery() {
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        if (finished != null) {
            result.put("finished", finished.toString());
        }
        if (assigned != null) {
            result.put("assigned", assigned.toString());
        }
        if (starts_after != null) {
            result.put("starts_after", starts_after.toString());
        }
        if (radius != null) {
            result.put("radius", radius.toString());
        }
        if (latitude != null) {
            result.put("lat", latitude.toString());
        }
        if (longitude != null)  {
            result.put("long", longitude.toString());
        }
        return result;
    }

    public Boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public Integer getStarts_after() {
        return starts_after;
    }

    public void setStarts_after(int starts_after) {
        this.starts_after = starts_after;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
