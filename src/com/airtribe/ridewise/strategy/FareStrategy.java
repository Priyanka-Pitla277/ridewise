package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

/**
 * @author Priyanka Pitla
 */
public interface FareStrategy {
    double calculateFare(Ride ride);
}
