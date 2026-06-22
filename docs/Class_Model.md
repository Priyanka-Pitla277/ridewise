```mermaid
classDiagram
    class Constants {
        +static MENU: String
        +static BASE_FARE: double
        +static PER_UNIT_RATE: double
        +static MORNING_PEAK_START: LocalTime
        +static MORNING_PEAK_END: LocalTime
        +static EVENING_PEAK_START: LocalTime
        +static EVENING_PEAK_END: LocalTime
        +static PEAK_MULTIPLIER: double
    }

    class NoDriverAvailableException {
        -static serialVersionUID: long
        +NoDriverAvailableException()
        +NoDriverAvailableException(message: String)
        +NoDriverAvailableException(message: String, cause: Throwable)
    }

    class Main {
        +static main(args: String[]): void
        -static printMenu(): void
        -static completeRide(scanner: Scanner, rideService: RideService): void
        -static addRider(scanner: Scanner, service: RiderService): void
        -static addDriver(scanner: Scanner, service: DriverService): void
        -static getAvailableDrivers(service: DriverService): void
        -static requestRide(scanner: Scanner, riderService: RiderService, service: RideService): void
        -static readCoordinatesFromRider(scanner: Scanner, label: String): Location
        -static getRides(service: RideService): void
    }

    class Driver {
        -id: String
        -name: String
        -currentLocation: Location
        -isAvailable: boolean
        -tripsCompleted: int
        -vehicleType: VehicleType
        +Driver(currentLocation: Location, name: String, vehicleType: VehicleType)
        +isAvailable(): boolean
        +setAvailable(available: boolean): void
        +getCurrentLocation(): Location
        +setCurrentLocation(currentLocation: Location): void
        +getId(): String
        +setId(id: String): void
        +getName(): String
        +setName(name: String): void
        +drive(ride: Ride): void
        +getTripsCompleted(): int
        +setTripsCompleted(tripsCompleted: int): void
        +getVehicleType(): VehicleType
        +setVehicleType(vehicleType: VehicleType): void
        +toString(): String
    }

    class FareReceipt {
        -rideId: String
        -amount: double
        -generatedAt: Date
        +getAmount(): double
        +setAmount(amount: double): void
        +getGeneratedAt(): Date
        +setGeneratedAt(generatedAt: Date): void
        +getRideId(): String
        +setRideId(rideId: String): void
        +toString(): String
    }

    class Location {
        -locationName: String
        -latitude: double
        -longitude: double
        +Location(latitude: double, longitude: double, locationName: String)
        +Location(locationName: String)
        +getLatitude(): double
        +getLongitude(): double
        +setLatitude(latitude: double): void
        +getLocationName(): String
        +setLocationName(locationName: String): void
        +setLongitude(longitude: double): void
        +distanceTo(other: Location): double
        +distanceTo(locationName: String): double
        +toString(): String
    }

    class Ride {
        -id: String
        -rider: Rider
        -driver: Driver
        -distance: double
        -status: RideStatus
        -receipt: FareReceipt
        -dropLocation: Location
        +Ride(distance: double, driver: Driver, rider: Rider, status: RideStatus)
        +Ride(rider: Rider, status: RideStatus)
        +Ride(distance: double)
        +getDistance(): double
        +setDistance(distance: double): void
        +getDriver(): Driver
        +setDriver(driver: Driver): void
        +getId(): String
        +setId(id: String): void
        +getRider(): Rider
        +setRider(rider: Rider): void
        +getStatus(): RideStatus
        +setStatus(status: RideStatus): void
        +getReceipt(): FareReceipt
        +setReceipt(receipt: FareReceipt): void
        +getDropLocation(): Location
        +setDropLocation(dropLocation: Location): void
        +generateFareReceipt(): void
        +toString(): String
    }

    class Rider {
        -id: String
        -name: String
        -location: Location
        +Rider(location: Location, name: String)
        +getId(): String
        +setId(id: String): void
        +getLocation(): Location
        +setLocation(location: Location): void
        +getName(): String
        +setName(name: String): void
        +ride(ride: Ride): void
        +toString(): String
    }

    class DriverRepository {
        <<interface>>
        ~registerDriver(driver: Driver): Driver
        ~getAvailableDrivers(): List~Driver~
        ~getAvailableDriversByVehichleType(vehicleType: VehicleType): List~Driver~
        ~updateDriverDetails(driverId: String, isAvailable: boolean, currentLocation: Location): boolean
    }

    class DriverRepositoryImpl {
        -static driverMap: Map~String, Driver~
        +registerDriver(driver: Driver): Driver
        +getAvailableDrivers(): List~Driver~
        +getAvailableDriversByVehichleType(vehicleType: VehicleType): List~Driver~
        +updateDriverDetails(driverId: String, isAvailable: boolean, currentLocation: Location): boolean
    }

    class LocationRegistry {
        -static areaMap: Map~String, Location~
        +static getLocation(areaName: String): Location
        +static populateAreas(areaName: String, latitude: double, longitude: double): void
    }

    class RiderRepository {
        <<interface>>
        ~registerRider(rider: Rider): Rider
        ~getRiderById(id: String): Rider
    }

    class RiderRepositoryImpl {
        -static riderMap: Map~String, Rider~
        +registerRider(rider: Rider): Rider
        +getRiderById(id: String): Rider
    }

    class DriverService {
        ~repository: DriverRepository
        +DriverService(repository: DriverRepository)
        +registerDriver(driver: Driver): Driver
        +updateDriverAvailable(isAvailable: boolean): void
        +getAvailableDrivers(): List~Driver~
    }

    class RiderService {
        ~repository: RiderRepository
        +RiderService(repository: RiderRepository)
        +registerRider(rider: Rider): Rider
        +getRiderById(id: String): Rider
    }

    class RideService {
        -rideMatchingStrategy: RideMatchingStrategy
        -fareStrategy: FareStrategy
        -driverRepository: DriverRepository
        -static ridesMap: Map~String, Ride~
        +RideService(fareStrategy: FareStrategy, rideMatchingStrategy: RideMatchingStrategy, driverRepository: DriverRepository)
        +requestRide(rider: Rider, pickUp: Location, drop: Location, vehicleType: VehicleType): Ride
        -calculateFareAndAssignDriver(rider: Rider, drop: Location, newRide: Ride, vehicleType: VehicleType): void
        +assignDriver(rider: Rider, vehicleType: VehicleType): Driver
        +calculateFare(newRide: Ride): double
        +completeRide(rideId: String): void
        -checkAndUpdateRideDetails(rideId: String, activeRide: Ride): void
        +getRides(): List~Ride~
    }

    class DefaultFareStrategy {
        +calculateFare(ride: Ride): double
    }

    class FareStrategy {
        <<interface>>
        ~calculateFare(ride: Ride): double
    }

    class LeastActiveDriverStrategy {
        +findDriver(rider: Rider, drivers: List~Driver~): Driver
    }

    class NearestDriverStrategy {
        +findDriver(rider: Rider, drivers: List~Driver~): Driver
    }

    class PeakHourFareStrategy {
        +calculateFare(ride: Ride): double
        -isPeakHour(): boolean
    }

    class RideMatchingStrategy {
        <<interface>>
        ~findDriver(rider: Rider, drivers: List~Driver~): Driver
    }

    class IdGenerator {
        -static RIDER_PREFIX: String
        -static DRIVER_PREFIX: String
        -static sequence: AtomicInteger
        -static formatter: DateTimeFormatter
        -static counter: AtomicLong
        +static generateRiderId(): String
        +static generateDriverId(): String
    }

    Main ..> RideService
    Main ..> RiderService
    Main ..> DriverService
    Main ..> Location
    Driver *-- Location
    Driver ..> Ride
    Location ..> Location
    Ride *-- Rider
    Ride *-- Driver
    Ride *-- FareReceipt
    Ride *-- Location
    Rider *-- Location
    Rider ..> Ride
    DriverRepository ..> Driver
    DriverRepository ..> Location
    DriverRepositoryImpl ..|> DriverRepository
    DriverRepositoryImpl o-- Driver
    DriverRepositoryImpl ..> Location
    LocationRegistry o-- Location
    RiderRepository ..> Rider
    RiderRepositoryImpl ..|> RiderRepository
    RiderRepositoryImpl o-- Rider
    DriverService --> DriverRepository
    DriverService ..> Driver
    RiderService --> RiderRepository
    RiderService ..> Rider
    RideService *-- RideMatchingStrategy
    RideService *-- FareStrategy
    RideService *-- DriverRepository
    RideService o-- Ride
    RideService ..> Rider
    RideService ..> Location
    RideService ..> Driver
    DefaultFareStrategy ..|> FareStrategy
    DefaultFareStrategy ..> Ride
    FareStrategy ..> Ride
    LeastActiveDriverStrategy ..|> RideMatchingStrategy
    LeastActiveDriverStrategy ..> Rider
    LeastActiveDriverStrategy ..> Driver
    NearestDriverStrategy ..|> RideMatchingStrategy
    NearestDriverStrategy ..> Rider
    NearestDriverStrategy ..> Driver
    PeakHourFareStrategy ..|> FareStrategy
    PeakHourFareStrategy ..> Ride
    RideMatchingStrategy ..> Rider
    RideMatchingStrategy ..> Driver