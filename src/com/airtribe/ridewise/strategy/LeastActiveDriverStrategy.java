package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

/**
 * @author Priyanka Pitla
 */
public class LeastActiveDriverStrategy implements RideMatchingStrategy {



    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        Driver leastActiveDriver = null;
        int lowestTripCount = Integer.MAX_VALUE;
        for (Driver driver : drivers) {
            if (driver.getTripsCompleted() < lowestTripCount) {
                lowestTripCount = driver.getTripsCompleted();
                leastActiveDriver = driver;
            }
        }
        return leastActiveDriver;
    }
}