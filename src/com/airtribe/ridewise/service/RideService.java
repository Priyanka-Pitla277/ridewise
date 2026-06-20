package com.airtribe.ridewise.service;

import com.airtribe.ridewise.enums.RideStatus;
import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.*;
import com.airtribe.ridewise.repository.DriverRepository;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Priyanka Pitla
 */
public class RideService {

    private final RideMatchingStrategy rideMatchingStrategy;
    private final FareStrategy fareStrategy;
    private final DriverRepository driverRepository;
    private static final Map<String, Ride> ridesMap = new ConcurrentHashMap<>();

    public RideService(FareStrategy fareStrategy, RideMatchingStrategy rideMatchingStrategy, DriverRepository driverRepository) {
        this.fareStrategy = fareStrategy;
        this.rideMatchingStrategy = rideMatchingStrategy;
        this.driverRepository = driverRepository;
    }

    public Ride requestRide(Rider rider, Location pickUp, Location drop, VehicleType vehicleType) {
        double distanceTodrop = drop.distanceTo(pickUp);
        Ride newRide = new Ride(distanceTodrop);
        try {
            calculateFareAndAssignDriver(rider, drop, newRide, vehicleType);
        }
        catch (NoDriverAvailableException e){
            System.out.println(e.getMessage());
            return null;
        }
        return newRide;
    }

    private void calculateFareAndAssignDriver(Rider rider, Location drop, Ride newRide, VehicleType vehicleType) {
        double fare = calculateFare(newRide);
        System.out.println(rider.getName() + " is requested for a ride...");
        FareReceipt receipt = newRide.getReceipt();
        receipt.setAmount(fare);
        newRide.setRider(rider);
        newRide.setStatus(RideStatus.REQUESTED);
        newRide.setDropLocation(drop);
        Driver driver = assignDriver(rider,vehicleType);
        newRide.setDriver(driver);
        newRide.setStatus(RideStatus.ASSIGNED);
        ridesMap.put(newRide.getId(), newRide);
        System.out.println("\nRide successfully booked!");
        System.out.println("Ride Details: " + newRide);
    }

    public Driver assignDriver(Rider rider, VehicleType vehicleType) {
        System.out.println("searching driver for "+rider.getName());
        Driver driver = rideMatchingStrategy.findDriver(rider, driverRepository.getAvailableDriversByVehichleType(vehicleType));
        if (driver == null) {
            throw new NoDriverAvailableException("Booking Failed: No drivers available.");
        }
        driver.setAvailable(false);
        return driver;
    }

    public double calculateFare(Ride newRide) {
        double fare = fareStrategy.calculateFare(newRide);
        System.out.printf("Total Fare is : $%.2f%n" , fare);
        return fare;
    }

    public void completeRide(String rideId) {
        if (ridesMap.containsKey(rideId)) {
            Ride activeRide = ridesMap.get(rideId);
            checkAndUpdateRideDetails(rideId, activeRide);
        } else {
            System.out.println("no active ride details found for the details entered");
        }
    }

    private void checkAndUpdateRideDetails(String rideId, Ride activeRide) {
        if (activeRide.getStatus() == RideStatus.ASSIGNED) {
            Driver driver = activeRide.getDriver();
            try {
                driverRepository.updateDriverDetails(driver.getId(), true, activeRide.getDropLocation());
                activeRide.setStatus(RideStatus.COMPLETED);
                activeRide.generateFareReceipt();
                System.out.println("ride completed, please pay charges in cash");
                ridesMap.put(rideId, activeRide);
            } catch (NoDriverAvailableException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("The entered Ride is not ongoing/invalid state");
        }
    }

    public List<Ride> getRides() {
        return ridesMap.values().stream().toList();
    }
}
