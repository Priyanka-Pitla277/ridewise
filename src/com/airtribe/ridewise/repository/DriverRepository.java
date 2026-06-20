package com.airtribe.ridewise.repository;

import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Location;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

/**
 * @author Priyanka Pitla
 */
public interface DriverRepository {

    Driver registerDriver(Driver driver);

    List<Driver> getAvailableDrivers();

    List<Driver> getAvailableDriversByVehichleType(VehicleType vehicleType);

    boolean updateDriverDetails(String driverId, boolean isAvailable, Location currentLocation);
}
