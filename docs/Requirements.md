# System Requirements Specification (SRS) - RideWise Platform

This document outlines the functional requirements, business rules, and technical constraints for the **RideWise** ride-sharing console application.

---

## 1. Project Overview
RideWise is a backend, menu-driven console application designed to orchestrate passenger (Rider) registrations, driver onboarding, driver availability updates, and algorithmic ride-matching. It computes dynamically adjusted fares based on distance and temporal peak factors.

---

## 2. Functional Requirements

### 2.1 Rider Management
* **Rider Registration** The system must allow a new passenger to register by capturing their `Name` and initial `Location`.
* **Unique ID Assignment** Every registered rider must be assigned a unique system identifier prefixed with `RDR-` followed by an auto-incremented sequence number.
* **Profile Retrieval** The system must support fetching a rider profile seamlessly by their unique Rider ID.

### 2.2 Driver Management
* **Driver Registration** The system must allow a new driver to onboard by capturing their `Name`, preferred `Vehicle Type`, and starting `Location` coordinates.
* **Unique ID Assignment** Every registered driver must be assigned a unique system identifier prefixed with `DRV-` followed by an auto-incremented tracking sequence.
* **Availability Toggle** The system must track and allow updates to a driver's availability state (`true`/`false`).
* **Location Updates** The system must update a driver's current coordinates dynamically when a trip completes.
* **Trip Counter** The system must track the total count of completed trips (`tripsCompleted`) for every individual driver profile.

### 2.3 Ride Execution & Lifecycle
* **Ride Booking Request** A rider can request a ride by providing their destination area. The system will look up the geographic coordinates via a `LocationRegistry`.
* **Driver Matching** The system must evaluate all registered, online drivers and match the optimal driver using interchangeable matching algorithms.
* **No Driver Handling** If no active driver satisfies the matching algorithm's constraints or vehicle selection, the system must abort execution and throw a `NoDriverAvailableException`.
* **Trip Completion** Upon trip conclusion, the system must mark the ride status as completed, set the driver's availability back to active, update the driver's current location to the drop-off coordinates, and increment their lifetime trip counter.
* **Receipt Generation** Upon completion, the system must generate an immutable `FareReceipt` showing the `Ride ID`, finalized `Amount`, and a generation timestamp.

---

## 3. Business & Core Domain Rules

### 3.1 Driver Allocation Strategies
The system must support flexible runtime strategies to align drivers to incoming ride requests:
1. **Nearest Driver Strategy:** Allocates the ride to the closest available driver matching the requested vehicle type using straight-line coordinate distance calculations.
2. **Least Active Driver Strategy:** Allocates the ride to the eligible available driver who has completed the fewest lifetime trips, helping distribute workload.

### 3.2 Dynamic Pricing & Fare Matrix
Fares are derived mathematically based on distance metrics and system timeframes:

| Cost Element | Rule / Value |
| :--- | :--- |
| **Base Fare** | $2.50 fixed starting price |
| **Per Unit Distance Rate** | $1.50 per unit of calculated distance |
| **Peak Hour Multiplier** | 2.0x modifier applied to the subtotal |

#### Peak Hour Windows:
* **Morning Windows:** 08:00 AM to 10:00 AM
* **Evening Windows:** 05:00 PM to 07:00 PM

*Formula:* `Total Fare = (Base Fare + (Distance * Per Unit Rate)) * Multiplier (if applicable)`

---

## 4. Interface Requirements (Console Menu)

The main application loop must present a continuous, interactive command-line interface mapping exactly to the following numerical routing index:

```text
(1) Register Rider 
(2) Register Driver 
(3) View All Available Drivers 
(4) Request a Ride 
(5) Complete a Ride 
(6) View All Rides 
(7) Exit