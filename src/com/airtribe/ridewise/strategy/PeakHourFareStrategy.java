package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

import java.time.LocalTime;

import com.airtribe.ridewise.constants.Constants;

/**
 * @author Priyanka Pitla
 */
public class PeakHourFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(Ride ride) {
        double standardFare = Constants.BASE_FARE + (ride.getDistance() * Constants.PER_UNIT_RATE);
        if (isPeakHour()) {
            System.out.printf("[SURGE ACTIVE] Peak hour multiplier applied: %.1fx%n", Constants.PEAK_MULTIPLIER);
            return standardFare * Constants.PEAK_MULTIPLIER;
        }
        return standardFare;
    }

    private boolean isPeakHour() {
        LocalTime now = LocalTime.now();
        boolean isMorningPeak = !now.isBefore(Constants.MORNING_PEAK_START) && !now.isAfter(Constants.MORNING_PEAK_END);
        boolean isEveningPeak = !now.isBefore(Constants.EVENING_PEAK_START) && !now.isAfter(Constants.EVENING_PEAK_END);
        return isMorningPeak || isEveningPeak;
    }
}
