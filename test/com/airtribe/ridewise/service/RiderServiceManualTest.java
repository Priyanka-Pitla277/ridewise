package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Location;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.repository.RiderRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Manual Test class for RiderService verification.
 * Run this directly within your IDE.
 */
public class RiderServiceManualTest {

    public static void main(String[] args) {
        System.out.println("========== STARTING RIDER SERVICE MANUAL TESTS ==========\n");

        // 1. Setup Dependencies using our in-memory test double repository
        RiderRepository testRepository = new InMemoryRiderRepository();
        RiderService riderService = new RiderService(testRepository);

        // 2. Setup Test Data
        Rider sampleRider = new Rider(new Location("R-101"), "Priyanka");

        // 3. Execute Test Cases
        testRegisterRider(riderService, sampleRider);
        testGetRiderById_Found(riderService, "R-101");
        testGetRiderById_NotFound(riderService, "INVALID-ID");

        System.out.println("\n========== ALL MANUAL TESTS EXECUTED ==========");
    }

    private static void testRegisterRider(RiderService service, Rider rider) {
        System.out.println("--- Test Case 1: Register Rider ---");

        // Act
        Rider registered = service.registerRider(rider);

        // Assert
        if (registered != null && "Priyanka".equals(registered.getName())) {
            System.out.println("Result: SUCCESS - Rider registered successfully!");
            System.out.println("Saved Details: " + registered);
        } else {
            System.out.println("Result: FAILED - Rider object returned null or data corrupted.");
        }
        System.out.println();
    }

    private static void testGetRiderById_Found(RiderService service, String id) {
        System.out.println("--- Test Case 2: Get Rider By ID (Existing) ---");

        // Act
        Rider foundRider = service.getRiderById(id);

        // Assert
        if (foundRider != null && id.equals(foundRider.getId())) {
            System.out.println("Result: SUCCESS - Correct Rider matched and fetched.");
            System.out.println("Fetched Details: " + foundRider);
        } else {
            System.out.println("Result: FAILED - Could not locate existing Rider profile.");
        }
        System.out.println();
    }

    private static void testGetRiderById_NotFound(RiderService service, String id) {
        System.out.println("--- Test Case 3: Get Rider By ID (Non-Existent Entry) ---");

        // Act
        Rider missingRider = service.getRiderById(id);

        // Assert
        if (missingRider == null) {
            System.out.println("Result: SUCCESS - Returned null cleanly for an invalid ID request.");
        } else {
            System.out.println("Result: FAILED - Expected null response, but an unexpected object was returned.");
        }
        System.out.println();
    }

    // =========================================================================
    // LIGHTWEIGHT IN-MEMORY REPOSITORY BACKEND STUB FOR STANDALONE RUNNING
    // =========================================================================
    private static class InMemoryRiderRepository implements RiderRepository {
        private final Map<String, Rider> dbMock = new HashMap<>();

        @Override
        public Rider registerRider(Rider rider) {
            if (rider != null && rider.getId() != null) {
                dbMock.put(rider.getId(), rider);
                return rider;
            }
            return null;
        }

        @Override
        public Rider getRiderById(String id) {
            return dbMock.get(id); // Returns the object if exists, or null if it doesn't
        }
    }
}