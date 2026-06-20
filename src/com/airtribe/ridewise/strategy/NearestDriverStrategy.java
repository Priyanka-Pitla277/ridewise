package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

/**
 * @author Priyanka Pitla
 */
public class NearestDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        Driver nearestDriver = null;
        double shortestDriverDistance = Double.MAX_VALUE;

        // 1. Find the NEAREST available driver to the pickup location
        for (Driver driver : drivers) {
                double distanceToPassenger = driver.getCurrentLocation().distanceTo(rider.getLocation());
                if (distanceToPassenger < shortestDriverDistance) {
                    shortestDriverDistance = distanceToPassenger;
                    nearestDriver = driver;
                }
        }
        return nearestDriver;
    }
}
