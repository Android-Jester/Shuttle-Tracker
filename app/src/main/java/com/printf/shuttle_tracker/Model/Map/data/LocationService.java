package com.printf.shuttle_tracker.Model.Map.data;

public class LocationService {
    public double latitude;
    public double longitude;

    public double getLatitude() {
        return latitude;
    }

    public LocationService() {
    }

    public LocationService(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
}
