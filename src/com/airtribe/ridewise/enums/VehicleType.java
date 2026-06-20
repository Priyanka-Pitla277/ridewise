package com.airtribe.ridewise.enums;

/**
 * @author Priyanka Pitla
 */
public enum VehicleType {

    BIKE("bike"),
    AUTO("auto"),
    CAR("car");

    private final String description;

    private VehicleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}

