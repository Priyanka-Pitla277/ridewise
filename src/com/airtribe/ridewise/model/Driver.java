package com.airtribe.ridewise.model;

import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.util.IdGenerator;

/**
 * @author Priyanka Pitla
 */
public class Driver {

    private String id;
    private String name;
    private Location currentLocation;
    private boolean isAvailable;
    private int tripsCompleted;
    private VehicleType vehicleType;

    public Driver(Location currentLocation, String name, VehicleType vehicleType) {
        this.id = IdGenerator.generateDriverId();
        this.isAvailable = true;
        this.currentLocation = currentLocation;
        this.name = name;
        this.vehicleType = vehicleType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void drive(Ride ride) {
        System.out.println(this.name + " is now on ride: " + ride.getId());
    }

    public int getTripsCompleted() {
        return tripsCompleted;
    }

    public void setTripsCompleted(int tripsCompleted) {
        this.tripsCompleted = tripsCompleted;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "currentLocation=" + currentLocation +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isAvailable=" + isAvailable +
                ", tripsCompleted=" + tripsCompleted +
                ", vehicleType=" + vehicleType +
                '}';
    }
}
