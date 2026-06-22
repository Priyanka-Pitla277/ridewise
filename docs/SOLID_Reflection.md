# Architectural Review: SOLID Principles Reflection

This document reflects on how the **RideWise** ride-sharing system adheres to the **SOLID** design principles to ensure a decoupled, maintainable, and highly extensible codebase.

---

## 1. Single Responsibility Principle (SRP)
> *A class should have one, and only one, reason to change.*

The RideWise system avoids the "God Object" anti-pattern by segregating responsibilities into distinct layers:

* **Separation of Concerns:** `Main` is strictly an input/output coordinator (routing user choices). It does not contain core system logic.
* **Data Layer vs. Business Layer:** Data persistence is isolated within repositories (e.g., `DriverRepositoryImpl`), whereas business logic flows entirely through services (`DriverService`).
* **Utility Isolation:** Unique identity generation is abstracted entirely into the `IdGenerator` class, ensuring that changes to the ID format do not bleed into domain models like `Rider` or `Driver`.

---

## 2. Open/Closed Principle (OCP)
> *Software entities should be open for extension, but closed for modification.*

The architecture relies heavily on polymorphism to allow behavior expansion without code modification, specifically within the matching and pricing domains:

* **Pluggable Strategies:** The `RideService` relies on the `FareStrategy` and `RideMatchingStrategy` interfaces.
* **Seamless Extension:** If we need to introduce a new business rule—such as a `SurgePricingStrategy` or a `VipDriverMatchingStrategy`—we can simply write a new class that implements the respective interface. The core execution engine (`RideService`) remains completely untouched.

---

## 3. Liskov Substitution Principle (LSP)
> *Subtypes must be substitutable for their base types without altering the correctness of the program.*

Every concrete subclass and implementation in RideWise can be substituted directly into its parent reference seamlessly:

* **Strategy Substitutability:** `DefaultFareStrategy` and `PeakHourFareStrategy` can be swapped interchangeably inside `RideService` at runtime. Both honor the contract established by `FareStrategy` without throwing unexpected runtime exceptions or requiring internal type-casting hacks.
* **Repository Contracts:** `RiderRepositoryImpl` implements all methods defined by `RiderRepository` predictably, ensuring consumer services face zero side effects when retrieving or persisting data.

---

## 4. Interface Segregation Principle (ISP)
> *Clients should not be forced to depend on methods they do not use.*

Instead of creating massive, multi-purpose interfaces, RideWise establishes highly specialized, streamlined contracts:

* **Targeted Repositories:** Rather than a monolithic `SystemRepository`, the codebase isolates data access into `DriverRepository` and `RiderRepository`. A service tracking riders is never forced to know anything about driver operations.
* **Single-Purpose Functional Interfaces:** Interfaces like `FareStrategy` export exactly one responsibility (`calculateFare`). This keeps implementations lightweight, clean, and highly compatible with Java functional programming constructs.

---

## 5. Dependency Inversion Principle (DIP)
> *Depend on abstractions, not on concretions.*

High-level modules do not depend on low-level implementation details; both depend on abstract contracts.

* **Loose Coupling via Dependency Injection:** `RideService` does not instantiate concrete objects like `NearestDriverStrategy` or `DriverRepositoryImpl` internally. Instead, it accepts interface definitions via its constructor:
  ```java
  public RideService(FareStrategy fareStrategy, 
                     RideMatchingStrategy rideMatchingStrategy, 
                     DriverRepository driverRepository) { ... }