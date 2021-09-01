package com.printf.shuttle_tracker.Model.Map.data;

public class Location {
    private boolean isActive;
    private double latitude;
    private double longitude;



    public Location() {}
    public Location( boolean isActive, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActive = isActive;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
