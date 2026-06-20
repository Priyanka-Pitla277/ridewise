package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.constants.Constants;
import com.airtribe.ridewise.model.Ride;

/**
 * @author Priyanka Pitla
 */
public class DefaultFareStrategy implements FareStrategy {

    @Override
    public double calculateFare(Ride ride) {
        return Constants.BASE_FARE + (ride.getDistance() * Constants.PER_UNIT_RATE);
    }
}
