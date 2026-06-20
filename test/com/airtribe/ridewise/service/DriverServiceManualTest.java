package com.airtribe.ridewise.service;

import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Location;
import com.airtribe.ridewise.repository.DriverRepositoryImpl;

import java.util.List;

/**
 * Manual Test class for DriverService verification.
 * Run this class directly using your IDE's main method executor.
 */
public class DriverServiceManualTest {

    public static void main(String[] args) {
        System.out.println("========== STARTING DRIVER SERVICE MANUAL TESTS ==========\n");

        // 1. Setup Dependencies
        DriverRepositoryImpl mockRepository = new DriverRepositoryImpl();
        DriverService driverService = new DriverService(mockRepository);

        // 2. Execute Test Cases
        testRegisterDriver(driverService);
        testGetAvailableDrivers(driverService);
        testAvailableDriverCount(driverService);

        System.out.println("\n========== ALL MANUAL TESTS EXECUTED ==========");
    }

    private static void testRegisterDriver(DriverService service) {
        System.out.println("--- Test Case 1: Register Driver ---");

        // Setup mock location and driver instance
        Location sampleLocation = new Location(12.9716, 77.5946, "Downtown");
        Driver newDriver = new Driver(sampleLocation, "John Doe", VehicleType.AUTO);

        // Act
        Driver registeredDriver = service.registerDriver(newDriver);

        // Assert
        if (registeredDriver != null && "John Doe".equals(registeredDriver.getName())) {
            System.out.println("Result: SUCCESS - Driver registered perfectly!");
            System.out.println("Registered Details: " + registeredDriver);
        } else {
            System.out.println("Result: FAILED - Driver was not registered or data mismatch.");
        }
        System.out.println();
    }

    private static void testGetAvailableDrivers(DriverService service) {
        System.out.println("--- Test Case 2: Get Available Drivers List ---");

        // Act
        List<Driver> availableDrivers = service.getAvailableDrivers();

        // Assert
        if (availableDrivers != null) {
            System.out.println("Result: SUCCESS - List retrieved successfully.");
            System.out.println("Available Count: " + availableDrivers.size());
            for (Driver d : availableDrivers) {
                System.out.println(" -> " + d.getName() + " is ready.");
            }
        } else {
            System.out.println("Result: FAILED - Driver collection returned null.");
        }
        System.out.println();
    }


    private static void testAvailableDriverCount(DriverService service) {
        System.out.println("--- Test Case 3: Get Available Drivers List size ---");

        // Act
        List<Driver> availableDrivers = service.getAvailableDrivers();

        // Assert
        if (availableDrivers != null && availableDrivers.size() == 1) {
            System.out.println("Result: SUCCESS - List retrieved successfully.");
            System.out.println("Available Count: " + availableDrivers.size());
        } else {
            System.out.println("Result: FAILED - Driver collection returned in accurate size.");
        }
        System.out.println();
    }
}