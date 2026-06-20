package com.airtribe.ridewise.model;

import com.airtribe.ridewise.util.IdGenerator;

/**
 * @author Priyanka Pitla
 */
public class Rider {
    private String id;
    private String name;
    private Location location;

    public Rider(Location location, String name) {
        this.id = IdGenerator.generateRiderId();
        this.location = location;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void ride(Ride ride) {
        System.out.println(this.name + " is now travelling on ride: " + ride.getId());
    }

    @Override
    public String toString() {
        return """
           Rider {
               id='%s',
               name='%s',
               location='%s'
           }""".formatted(id, name, location);
    }
}
