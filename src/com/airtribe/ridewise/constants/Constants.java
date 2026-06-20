package com.airtribe.ridewise.constants;

import java.time.LocalTime;

/**
 * @author Priyanka Pitla
 */
public class Constants {
    public static final String MENU = "(1) register Rider (2) register driver (3) view all available drivers (4) request a ride (5) complete a ride (6) view all rides (7) Exit";

    public static final double BASE_FARE = 2.50;
    public static final double PER_UNIT_RATE = 1.50;

    public static final LocalTime MORNING_PEAK_START = LocalTime.of(8, 0);
    public static final LocalTime MORNING_PEAK_END = LocalTime.of(10, 0);
    public static final LocalTime EVENING_PEAK_START = LocalTime.of(17, 0);
    public static final LocalTime EVENING_PEAK_END = LocalTime.of(19, 0);
    public static final double PEAK_MULTIPLIER = 2.0;
}
