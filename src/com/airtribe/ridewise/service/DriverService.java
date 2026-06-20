package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.repository.DriverRepository;
import com.airtribe.ridewise.repository.DriverRepositoryImpl;
import com.airtribe.ridewise.repository.RiderRepository;

import java.util.List;

/**
 * @author Priyanka Pitla
 *
 *
 */

public class DriverService {

    DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public Driver registerDriver(Driver driver) {
        return repository.registerDriver(driver);
    }

    public void updateDriverAvailable(boolean isAvailable) {
    }

    public List<Driver> getAvailableDrivers() {
        return repository.getAvailableDrivers();
    }
}
