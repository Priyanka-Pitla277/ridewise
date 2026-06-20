package com.airtribe.ridewise.service;

import com.airtribe.ridewise.enums.RideStatus;
import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.model.*;
import com.airtribe.ridewise.repository.DriverRepository;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;

import java.util.ArrayList;
import java.util.List;

public class RideServiceManualTest {

    public static void main(String[] args) {
        System.out.println("========== STARTING RIDE SERVICE MANUAL TESTS ==========\n");

        // 1. Setup Mock Dependencies
        FareStrategy testFareStrategy = new StubFareStrategy();
        RideMatchingStrategy testMatchingStrategy = new StubMatchingStrategy();
        DriverRepository testRepo = new InMemoryDriverRepository();
        
        RideService rideService = new RideService(testFareStrategy, testMatchingStrategy, testRepo);

        // 2. Setup Shared Domain Objects
        Rider rider = new Rider(new Location("R1"), "Alice");
        Location pickup = new Location(10.0, 20.0, "Downtown");
        Location drop = new Location(15.0, 25.0, "Airport");

        // 3. Run Tests
        testSuccessfulRideBookingAndCompletion(rideService, testRepo, rider, pickup, drop);
        testNoDriverAvailableEdgeCase(rideService, testRepo, rider, pickup, drop);

        System.out.println("\n========== ALL MANUAL TESTS EXECUTED ==========");
    }

    private static void testSuccessfulRideBookingAndCompletion(RideService service, DriverRepository repo, Rider rider, Location pickup, Location drop) {
        System.out.println("--- Test Case 1: Successful Ride Request & Completion ---");
        
        // Seed an available sedan driver into our repository
        InMemoryDriverRepository memRepo = (InMemoryDriverRepository) repo;
        Driver driver = new Driver(new Location("D1"), "Bob",  VehicleType.AUTO);
        driver.setAvailable(true);
        memRepo.addDriverToPool(driver);

        // Act - Request Ride
        Ride activeRide = service.requestRide(rider, pickup, drop, VehicleType.CAR);

        // Assert Request Phase
        if (activeRide != null && activeRide.getStatus() == RideStatus.ASSIGNED) {
            System.out.println("Result: SUCCESS - Ride booked and driver matched.");
            
            // Act - Complete Ride
            System.out.println("\nCompleting the ongoing ride...");
            service.completeRide(activeRide.getId());
            
            // Assert Completion Phase
            if (activeRide.getStatus() == RideStatus.COMPLETED) {
                System.out.println("Result: SUCCESS - Ride status marked COMPLETED.");
            } else {
                System.out.println("Result: FAILED - Ride completion lifecycle bug.");
            }
        } else {
            System.out.println("Result: FAILED - Ride was not initialized or assigned properly.");
        }
        System.out.println();
    }

    private static void testNoDriverAvailableEdgeCase(RideService service, DriverRepository repo, Rider rider, Location pickup, Location drop) {
        System.out.println("--- Test Case 2: No Drivers Available Catch Block ---");
        
        // Empty the active driver list
        InMemoryDriverRepository memRepo = (InMemoryDriverRepository) repo;
        memRepo.clearPool();

        // Act
        Ride failedRide = service.requestRide(rider, pickup, drop, VehicleType.CAR);

        // Assert
        if (failedRide == null) {
            System.out.println("Result: SUCCESS - Method caught exception gracefully and returned null.");
        } else {
            System.out.println("Result: FAILED - Exception wasn't handled or incorrect object returned.");
        }
        System.out.println();
    }

    // =========================================================================
    // LIGHTWEIGHT STUB DESIGNS FOR SEAMLESS RUNNING
    // =========================================================================

    private static class StubFareStrategy implements FareStrategy {
        @Override
        public double calculateFare(Ride ride) {
            return 25.50; // Simple fixed dummy pricing
        }
    }

    private static class StubMatchingStrategy implements RideMatchingStrategy {
        @Override
        public Driver findDriver(Rider rider, List<Driver> availableDrivers) {
            if (availableDrivers.isEmpty()) return null;
            return availableDrivers.get(0); // Return first available candidate
        }
    }

    private static class InMemoryDriverRepository implements DriverRepository {
        private final List<Driver> pool = new ArrayList<>();

        public void addDriverToPool(Driver d) { pool.add(d); }
        public void clearPool() { pool.clear(); }

        @Override
        public Driver registerDriver(Driver driver) {
            return null;
        }

        @Override
        public List<Driver> getAvailableDrivers() {
            return List.of();
        }

        @Override
        public List<Driver> getAvailableDriversByVehichleType(VehicleType type) {
            // Simply returns our mock pool regardless of structural filter string matching
            return pool.stream().filter(Driver::isAvailable).toList();
        }

        @Override
        public boolean updateDriverDetails(String driverId, boolean isAvailable, Location currentLocation) {
            return false;
        }


    }
}