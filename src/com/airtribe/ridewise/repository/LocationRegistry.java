package com.airtribe.ridewise.repository;

import com.airtribe.ridewise.model.Location;

import java.util.HashMap;
import java.util.Map;

public class LocationRegistry {
    private static final Map<String, Location> areaMap = new HashMap<>();

    // Populate the area database
    static {
        areaMap.put("Downtown", new Location(1.0, 10.0, "Downtown"));
        areaMap.put("Airport", new Location(15.0, 20.0, "Airport"));
        areaMap.put("Suburbs", new Location(2.0, 1.0, "Suburbs"));
        areaMap.put("Central Park", new Location(5.0, 5.0, "Central Park"));
        areaMap.put("Mall", new Location(8.0, 12.0, "Mall"));
    }

    public static Location getLocation(String areaName) {
        return areaMap.get(areaName);
    }

    public static void populateAreas(String areaName, double latitude, double longitude) {
        areaMap.put(areaName, new Location(latitude, longitude, areaName));
    }
}