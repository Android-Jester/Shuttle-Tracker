package com.printf.shuttle_tracker.Model.Map.data;

public class Location {
    private boolean isActve;
    private double latitude;
    private double longitude;



    public Location() {}
    public Location( boolean isActve, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isActve = isActve;
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

    public boolean isActve() {
        return isActve;
    }

    public void setActive(boolean actve) {
        isActve = actve;
    }
}
