package com.airtribe.ridewise.repository;

import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Priyanka Pitla
 */
public class DriverRepositoryImpl implements DriverRepository{
    private static final Map<String, Driver> driverMap = new HashMap<>();

    @Override
    public Driver registerDriver(Driver driver) {
        driverMap.put(driver.getId(), driver);
        System.out.println("driver registered successfully");
        System.out.println(driver);
        return driver;
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        return driverMap.values()
                .stream().filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public List<Driver> getAvailableDriversByVehichleType(VehicleType vehicleType) {
        return driverMap.values()
                .stream().filter(driver -> driver.getVehicleType()==vehicleType && driver.isAvailable())
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateDriverDetails(String driverId, boolean isAvailable, Location currentLocation) {
       if(driverMap.containsKey(driverId))
       {
           Driver driver = driverMap.get(driverId);
           driver.setAvailable(isAvailable);
           driver.setCurrentLocation(currentLocation);
           driver.setTripsCompleted(driver.getTripsCompleted()+1);
           driverMap.put(driverId, driver);
           return true;
       }
       else{
           throw new NoDriverAvailableException("No driver details found for the ride to complete");
       }
    }
}
