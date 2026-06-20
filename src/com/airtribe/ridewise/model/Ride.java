package com.airtribe.ridewise.model;

import java.util.Date;
import java.util.UUID;

import com.airtribe.ridewise.enums.RideStatus;

/**
 * @author Priyanka Pitla
 */
public class Ride {
    private String id;
    private Rider rider;
    private Driver driver;
    private double distance;
    private RideStatus status;
    private FareReceipt receipt;
    private Location dropLocation;

    public Ride(double distance, Driver driver, Rider rider, RideStatus status) {
        this.distance = distance;
        this.driver = driver;
        this.id = UUID.randomUUID().toString();
        this.rider = rider;
        this.status = status;
        this.receipt = new FareReceipt();
        this.receipt.setRideId(this.id);

    }

    public Ride(Rider rider, RideStatus status) {
        this.id = UUID.randomUUID().toString();
        this.rider = rider;
        this.status = status;
        this.receipt = new FareReceipt();
        this.receipt.setRideId(this.id);
    }

    public Ride(double distance) {
        this.id = UUID.randomUUID().toString();
        this.receipt = new FareReceipt();
        this.receipt.setRideId(this.id);
        this.distance = distance;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public FareReceipt getReceipt() {
        return receipt;
    }

    public void setReceipt(FareReceipt receipt) {
        this.receipt = receipt;
    }


    public Location getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(Location dropLocation) {
        this.dropLocation = dropLocation;
    }


    public void generateFareReceipt()
    {
        this.receipt.setGeneratedAt(new Date());
    }

    @Override
    public String toString() {
        return "Ride{" +
                "distance=" + distance +
                ", id='" + id + '\'' +
                ", rider=" + rider +
                ", driver=" + driver +
                ", status=" + status +
                ", receipt=" + receipt +
                ", dropLocation=" + dropLocation +
                '}';
    }
}
