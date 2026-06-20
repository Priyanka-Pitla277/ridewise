package com.airtribe.ridewise.repository;

import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Location;

import java.util.List;

/**
 * Manual Test class for DriverRepositoryImpl verification.
 * Run directly inside your IDE environment.
 */
public class DriverRepositoryImplManualTest {

    public static void main(String[] args) {
        System.out.println("========== STARTING DRIVER REPOSITORY MANUAL TESTS ==========\n");

        // 1. Initialize System Under Test (SUT)
        DriverRepository repository = new DriverRepositoryImpl();

        // 2. Setup Test Stubs / Dummy Data
        Location locA = new Location(12.9, 77.5, "Location A");
        Location locB = new Location(13.0, 77.6, "Location B");

        Driver driver1 = new Driver(new Location("D-101"), "John", VehicleType.CAR);
        driver1.setAvailable(true);

        Driver driver2 = new Driver(new Location("D-102"), "Alex", VehicleType.CAR);
        driver2.setAvailable(false); // Starts unavailable

        // 3. Execute Test Pipeline
        testRegisterDriver(repository, driver1);
        testRegisterDriver(repository, driver2);

        testGetAvailableDrivers(repository);
        testGetAvailableDriversByVehicleType(repository, VehicleType.CAR);

        testUpdateDriverDetails_Success(repository, "D-101", locB);
        testUpdateDriverDetails_NotFoundException(repository, "INVALID-ID", locA);

        System.out.println("\n========== ALL MANUAL TESTS EXECUTED ==========");
    }

    private static void testRegisterDriver(DriverRepository repo, Driver driver) {
        System.out.println("--- Test: Register Driver (" + driver.getName() + ") ---");
        Driver saved = repo.registerDriver(driver);

        if (saved != null && saved.getId().equals(driver.getId())) {
            System.out.println("Assertion Passed: Driver saved securely.\n");
        } else {
            System.out.println("Assertion FAILED: Registration data error.\n");
        }
    }

    private static void testGetAvailableDrivers(DriverRepository repo) {
        System.out.println("--- Test: Get All Available Drivers ---");
        List<Driver> available = repo.getAvailableDrivers();

        // Out of John (avail: true) and Alex (avail: false), only John should appear
        if (available.size() == 1 && "John".equals(available.get(0).getName())) {
            System.out.println("Assertion Passed: Correctly filtered out unavailable profiles.\n");
        } else {
            System.out.println("Assertion FAILED: Availability filter mechanism broke down.\n");
        }
    }

    private static void testGetAvailableDriversByVehicleType(DriverRepository repo, VehicleType type) {
        System.out.println("--- Test: Get Available Drivers By Vehicle Type (" + type + ") ---");
        List<Driver> matchedList = repo.getAvailableDriversByVehichleType(type);

        if (!matchedList.isEmpty() && matchedList.get(0).getVehicleType() == type) {
            System.out.println("Assertion Passed: Successfully separated drivers matching vehicle structure type.\n");
        } else {
            System.out.println("Assertion FAILED: Stream structural type breakdown error.\n");
        }
    }

    private static void testUpdateDriverDetails_Success(DriverRepository repo, String id, Location targetLocation) {
        System.out.println("--- Test: Update Driver Details & Complete Trip ---");

        // Act
        boolean patchStatus = repo.updateDriverDetails("DRV-20260620-00001", true, targetLocation);

        // Assert
        if (patchStatus) {
            System.out.println("Assertion Passed: Returned true indicator confirm updates completed.");
            System.out.println("Note: Verify console outputs or fields to confirm tripsCompleted incremented cleanly.");
        } else {
            System.out.println("Assertion FAILED: Update operation tracking rejected unexpectedly.");
        }
        System.out.println();
    }

    private static void testUpdateDriverDetails_NotFoundException(DriverRepository repo, String fakeId, Location dummyLoc) {
        System.out.println("--- Test: Update Non-existent Driver (Expect Exception) ---");

        try {
            repo.updateDriverDetails(fakeId, true, dummyLoc);
            System.out.println("Assertion FAILED: Method completed without venting expected exception routes.");
        } catch (NoDriverAvailableException ex) {
            System.out.println("Assertion Passed: Caught expected error cleanly -> " + ex.getMessage());
        }
        System.out.println();
    }
}