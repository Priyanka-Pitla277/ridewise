package com.airtribe.ridewise.model;

import com.airtribe.ridewise.repository.LocationRegistry;

/**
 * @author Priyanka Pitla
 */
public class Location {
    private String locationName;
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude, String locationName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    public Location(String locationName) {
        this.locationName = locationName;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Calculates Euclidean distance between this location and another
    public double distanceTo(Location other) {
        return Math.sqrt(Math.pow(this.latitude - other.getLatitude(), 2) +
                Math.pow(this.longitude - other.getLongitude(), 2));
    }

    public double distanceTo(String locationName){
        if(null != locationName && !locationName.isBlank()) {
            Location location = LocationRegistry.getLocation(locationName);
            return distanceTo(location);
        }
        else{
            //exception
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Location{" +
                "  latitude=" + latitude +
                ", locationName='" + locationName + '\'' +
                ", longitude=" + longitude +
                '}';
    }
}
