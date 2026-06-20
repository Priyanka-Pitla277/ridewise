package com.airtribe.ridewise;

import com.airtribe.ridewise.constants.Constants;
import com.airtribe.ridewise.enums.VehicleType;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Location;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.repository.DriverRepositoryImpl;
import com.airtribe.ridewise.repository.LocationRegistry;
import com.airtribe.ridewise.repository.RiderRepositoryImpl;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.strategy.DefaultFareStrategy;
import com.airtribe.ridewise.strategy.LeastActiveDriverStrategy;
import com.airtribe.ridewise.strategy.PeakHourFareStrategy;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Priyanka Pitla
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;
        RiderService riderService = new RiderService(new RiderRepositoryImpl());
        DriverService driverService = new DriverService(new DriverRepositoryImpl());
        RideService rideService = new RideService(new DefaultFareStrategy(), new LeastActiveDriverStrategy(), new DriverRepositoryImpl());

        while (keepRunning) {
            System.out.println("Please select Menu option on which you want to perform action: ");
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    //add Rider
                    addRider(scanner, riderService);
                    break;
                case 2:
                    //add Driver
                    addDriver(scanner, driverService);
                    break;
                case 3:
                    //get available drivers
                    getAvailableDrivers(driverService);
                    break;
                case 4:
                    //request a Ride
                    requestRide(scanner, riderService, rideService);
                    break;
                case 5:
                    //complete a ride
                    completeRide(scanner, rideService);
                    break;
                case 6:
                    //view rides
                    getRides(rideService);
                    break;
                case 7:
                    keepRunning = false;
                    System.out.println("Exiting the interactive menu window.");
                    break;
            }
        }
        scanner.close();
    }


    private static void printMenu() {
        System.out.println("Select the operation you want to perform on book: " + Constants.MENU);
    }


    private static void completeRide(Scanner scanner, RideService rideService) {
        System.out.print("Enter rideId: ");
        String rideId = scanner.nextLine().trim();
        rideService.completeRide(rideId);
    }

    private static void addRider(Scanner scanner, RiderService service) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter location: ");
        String location = scanner.nextLine().trim();
        Rider rider = new Rider(new Location(location), name);
        Rider riderDto = service.registerRider(rider);
    }

    private static void addDriver(Scanner scanner, DriverService service) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter vehicle type: CAR/AUTO/BIKE: ");
        String vehicleType = scanner.nextLine().trim();
        System.out.println("Enter current location: ");
        String currentLocationName = scanner.nextLine().trim();
        Location currentLocation = LocationRegistry.getLocation(currentLocationName);
        if (null == currentLocation) {
            System.out.println("location details not found please enter location coordinates");
            System.out.println("Enter latitude:");
            double latitude = scanner.nextDouble();
            System.out.println("Enter longitude:");
            double longitude = scanner.nextDouble();
            scanner.nextLine();
            currentLocation = new Location(latitude, longitude, "");
        }

        Driver driver = new Driver(currentLocation, name,VehicleType.valueOf(vehicleType));
        service.registerDriver(driver);

    }

    private static void getAvailableDrivers(DriverService service) {
        List<Driver> drivers = service.getAvailableDrivers();
        System.out.println("available drivers: " + drivers);
    }

    private static void requestRide(Scanner scanner, RiderService riderService, RideService service) {
        System.out.print("Enter riderId: ");
        String riderId = scanner.nextLine().trim();
        System.out.println("Enter vehicle type: CAR/AUTO/BIKE: ");
        String vehicleType = scanner.nextLine().trim();
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            System.out.println("Error: Rider profile not found.");
            return;
        }

        // 1. Get Pickup Location
        System.out.print("How are you going to provide pickup location details? Type YES' for manual text area name, or anything else for coordinates: ");
        String manualPickup = scanner.nextLine().trim();
        Location pickupLocation = null;

        if (manualPickup.equalsIgnoreCase("YES")) {
            System.out.print("Enter pickup location name: ");
            String pickupLocationName = scanner.nextLine().trim();
            pickupLocation = LocationRegistry.getLocation(pickupLocationName);
        } else {
            pickupLocation = readCoordinatesFromRider(scanner, "pickup");
        }

        // 2. Get Drop Location
        System.out.print("How are you going to provide drop location details? Type YES for manual text area name, or anything else for coordinates: ");
        String manualDrop = scanner.nextLine().trim();
        Location dropLocation = null;

        if (manualDrop.equalsIgnoreCase("YES")) {
            System.out.print("Enter drop location: ");
            String dropLocationName = scanner.nextLine().trim();
            dropLocation = LocationRegistry.getLocation(dropLocationName);
        } else {
            dropLocation = readCoordinatesFromRider(scanner, "drop");
        }

        // 3. Request and Dispatch Ride
        if (pickupLocation != null && dropLocation != null) {
            rider.setLocation(pickupLocation);
            Ride ride = service.requestRide(rider, pickupLocation, dropLocation, VehicleType.valueOf(vehicleType));
        } else {
            System.out.println("Booking cancelled due to invalid location details.");
        }
    }

    /**
     * Helper method to handle coordinate entry cleanly, safely clear the scanner buffer,
     * and catch input typos.
     */
    private static Location readCoordinatesFromRider(Scanner scanner, String label) {
        try {
            System.out.print("Enter latitude for " + label + " location: ");
            double latitude = scanner.nextDouble();
            System.out.print("Enter longitude for " + label + " location: ");
            double longitude = scanner.nextDouble();
            // CRITICAL: Consume the trailing newline left behind by nextDouble()
            scanner.nextLine();
            return new Location(latitude, longitude, "");
        } catch (InputMismatchException e) {
            System.out.println("Invalid numeric input format. Expected a double coordinate.");
            scanner.nextLine();
            return null;
        }
    }

    private static void getRides(RideService service) {
        List<Ride> rides = service.getRides();
        System.out.println(" rides: " + rides);
    }
}
