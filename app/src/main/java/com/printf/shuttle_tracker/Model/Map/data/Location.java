package com.printf.shuttle_tracker.Model.Map.data;

public class Location {
    private double latitude;
    private double longitude;
    private boolean isActive;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Location() {

    }

    Location(double latitude, double longitude, boolean isActive){
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActive = isActive;
    }
}
